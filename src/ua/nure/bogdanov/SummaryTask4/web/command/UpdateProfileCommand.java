package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.exception.InvalidDataException;
import ua.nure.bogdanov.SummaryTask4.validator.Validator;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Update profile command.
 * 
 * @author I.Bogdanov
 * 
 */
public class UpdateProfileCommand extends Command {

	private static final long serialVersionUID = -7402595445693355611L;
	private static final Logger LOG = Logger
			.getLogger(UpdateProfileCommand.class);

	private HttpSession session;

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, AppException {
		String forward = Path.PAGE_ERROR_PAGE;
		String xmlPath = request.getServletContext().getRealPath("") + XML_PATH;
		session = request.getSession();
		DBManager manager = DBManager.getInstance();

		LOG.debug("Command starts");
		// get info from request
		User user = new User();
		User tempUser = (User) session.getAttribute("user");
		user.setLogin(request.getParameter("login"));
		LOG.trace("Request parameter: loging --> " + user.getLogin());
		String oldPassword = request.getParameter("oldPassword");
		LOG.trace("Request parameter: oldPassword --> " + user.getPassword());
		String newPassword = request.getParameter("newPassword");
		LOG.trace("Request parameter: newPassword --> " + user.getPassword());
		String confPassword = request.getParameter("password_conf");
		LOG.trace("Request parameter: password_conf --> " + user.getPassword());
		user.setEmail(request.getParameter("email"));
		LOG.trace("Request parameter: email --> " + user.getEmail());
		user.setFirstName(request.getParameter("firstName"));
		LOG.trace("Request parameter: firstName --> " + user.getFirstName());
		user.setLastName(request.getParameter("lastName"));
		LOG.trace("Request parameter: lastName --> " + user.getLastName());

		try {
			// validating params
			Validator validator = new Validator(xmlPath);
			validateUser(user, oldPassword, newPassword, confPassword,
					validator);
		} catch (InvalidDataException e) {
			LOG.trace("Break whith error --> " + e.getMessage());
			session.setAttribute("message", e.getMessage());
			forward = Path.COMMAND_PROFILE;
			return forward;
		}
		//
		user.setRoleId(tempUser.getRoleId());
		user.setId(tempUser.getId());
		// save changes to db
		user = manager.updateUser(tempUser.getId(), user);
		LOG.debug("Update user --> " + user);
		session.setAttribute("user", user);
		LOG.debug("Set the session attribute: user --> " + user);

		LOG.debug("Set the request attribute: message --> "
				+ "Update successful!");
		session.setAttribute("message", "Update successful!");
		forward = Path.COMMAND_PROFILE;
		LOG.debug("Command finished");
		return forward;
	}

	/**
	 * Validating user and throws {@link InvalidDataException} if field is not
	 * valid
	 * 
	 * @param user
	 *            user to validate
	 * @param oldPassword
	 *            old user password
	 * @param newPassword
	 *            new user password
	 * @param confPassword
	 *            new user password
	 * @param val
	 *            Validator with which validate
	 * @throws InvalidDataException
	 * @throws AppException
	 */
	private void validateUser(User user, String oldPassword,
			String newPassword, String confPassword, Validator val)
			throws InvalidDataException, AppException {

		DBManager manager = DBManager.getInstance();
		User tempUser = (User) session.getAttribute("user");
		Validator validator = val;
		validator.isValid(user.getLogin(), "Login");
		validator.isValid(user.getLastName(), "LastName");
		validator.isValid(user.getFirstName(), "FirstName");
		validator.isValid(user.getEmail(), "Email");

		if ((user.getLogin().compareTo(tempUser.getLogin()) != 0)
				&& (manager.findUserByLogin(user.getLogin()) != null)) {
			throw new InvalidDataException("InCurrent login already in use");
		}
		if ((user.getEmail().compareTo(tempUser.getEmail()) != 0)
				&& (manager.findUserByEmail(user.getEmail()) != null)) {
			throw new InvalidDataException("Current email already in use");
		}
		if (oldPassword != null && !oldPassword.isEmpty()) {
			validator.isValid(oldPassword, "OldPassword");
			validator.isValid(oldPassword, "NewPassword");
			validator.isValid(confPassword, "ConfPassword");

			if (newPassword.compareTo(confPassword) != 0) {
				throw new InvalidDataException("Entered passwords do not match");
			}

			String oldPasswordHash = hash(oldPassword);
			if (tempUser.getPassword().compareTo(oldPasswordHash) != 0) {
				throw new InvalidDataException("Incorrect password");
			}
			user.setPassword(hash(newPassword));

		} else {
			user.setPassword(tempUser.getPassword());
		}
	}
}
