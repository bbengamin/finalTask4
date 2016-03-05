package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.Role;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.exception.DBException;
import ua.nure.bogdanov.SummaryTask4.exception.InvalidDataException;
import ua.nure.bogdanov.SummaryTask4.validator.Validator;

/**
 * Login command.
 * 
 * @author I.Bogdanov
 * 
 */
public class LoginCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;
		String xmlPath = request.getServletContext().getRealPath("") + XML_PATH;
		HttpSession session = request.getSession();

		User user = null;
		// get login and password form request
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);
		String hashPassword = request.getParameter("hash_password");
		LOG.trace("Request parameter: hash_password --> " + hashPassword);

		try {
			// validating parameters
			Validator validator = new Validator(xmlPath);
			user = validateUser(login, hashPassword, validator);
		} catch (InvalidDataException e) {
			LOG.error("Break whith error --> " + e.getMessage());
			session.setAttribute("errorMessage", e.getMessage());
			session.setAttribute("login", login);
			forward = Path.PAGE_LOGIN;
			return forward;
		}
		// if user is blocked
		if (user.isStatus()) {
			String errorMessasge = "You are blocked! \n Administator message: "
					+ user.getBlockMessage();
			session.setAttribute("errorMessage", errorMessasge);
			LOG.error("Set the session attribute: errorMessage --> "
					+ errorMessasge);
			forward = Path.PAGE_ERROR_PAGE;
			LOG.debug("Command finished");
			return forward;
		}
		// set user config to session
		Role userRole = Role.getRole(user);
		LOG.trace("userRole --> " + userRole);

		session.setAttribute("currentLocale", user.getLocale());
		LOG.trace("Set the session attribute: currentLocale --> "
				+ user.getLocale());

		Config.set(session, Config.FMT_LOCALE,
				new java.util.Locale(user.getLocale()));
		LOG.trace("Set the session attribute: locale --> " + user.getLocale());

		if (userRole == Role.ADMIN) {
			forward = Path.COMMAND_LIST_TESTS;
		}

		if (userRole == Role.STUDENT) {
			forward = Path.COMMAND_LIST_TESTS;
		}

		session.setAttribute("user", user);
		LOG.trace("Set the session attribute: user --> " + user);

		session.setAttribute("userRole", userRole);
		LOG.trace("Set the session attribute: userRole --> " + userRole);

		LOG.info("User " + user + " logged as "
				+ userRole.toString().toLowerCase());

		LOG.debug("Command finished");
		return forward;
	}

	/**
	 * Validating login and password and throws {@link InvalidDataException} if
	 * field is not valid
	 * 
	 * @param login
	 *            user login
	 * @param hashPassword
	 *            hash of user password
	 * @param val
	 *            Validator with which validate
	 * @return
	 * @throws InvalidDataException
	 * @throws DBException
	 */
	private User validateUser(String login, String hashPassword, Validator val)
			throws InvalidDataException, DBException {
		DBManager manager = DBManager.getInstance();

		Validator validator = val;
		validator.isValid(login, "Login");
		validator.isValid(hashPassword, "HashPassword");

		User user = manager.findUserByLogin(login);
		LOG.trace("Found in DB: user --> " + user);

		if (user == null || !hashPassword.equals(user.getPassword())) {
			throw new InvalidDataException(
					"Cannot find user with such login/password");
		}
		return user;
	}

}