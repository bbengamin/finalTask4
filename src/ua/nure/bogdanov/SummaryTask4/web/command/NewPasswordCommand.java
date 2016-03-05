package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.exception.InvalidDataException;
import ua.nure.bogdanov.SummaryTask4.exception.Messages;
import ua.nure.bogdanov.SummaryTask4.validator.Validator;

/**
 * Set new password command.
 * 
 * @author I.Bogdanov
 * 
 */
public class NewPasswordCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger
			.getLogger(NewPasswordCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;

		DBManager manager = DBManager.getInstance();

		// get parameters form request
		String key = request.getParameter("key");
		LOG.trace("Request parameter: key --> " + key);

		String idUser = request.getParameter("idUser");
		LOG.trace("Request parameter: idUser --> " + idUser);
		// find user in db
		if(idUser == null || idUser.isEmpty()){
			LOG.error("Break whith error --> " + Messages.MESSAGE_ACCESS_ERROR);
			request.setAttribute("errorMessage", Messages.MESSAGE_ACCESS_ERROR);
			forward = Path.PAGE_ERROR_PAGE;
			return forward;
		}
		User user = manager.findUserById(Long.parseLong(idUser));
		if (key != null && !key.isEmpty()) {
			if (user.getPassword().compareTo(key) != 0) {
				LOG.error("Break whith error --> " + "Error");
				request.setAttribute("errorMessage", "Error");
				forward = Path.PAGE_ERROR_PAGE;
				return forward;
			}

			request.setAttribute("idUser", idUser);
			forward = Path.PAGE_NEW_PASSWORD;
		} else {
			// get new passwords from request
			user.setPassword(request.getParameter("password"));
			LOG.trace("Request parameter: password --> " + user.getPassword());

			String passwordConf = request.getParameter("password_conf");
			LOG.trace("Request parameter: password_conf --> " + passwordConf);

			try {
				// validating passwords
				Validator validator = new Validator(request.getServletContext()
						.getRealPath("") + File.separator + XML_PATH);
				validator.isValid(passwordConf, "ConfPassword");
				if (user.getPassword().compareTo(passwordConf) != 0) {
					throw new InvalidDataException(
							"Entered passwords do not match");
				}
			} catch (InvalidDataException e) {
				LOG.error("Break whith error --> " + e.getMessage());
				request.setAttribute("errorMessage", e.getMessage());
				request.setAttribute("idUser", idUser);
				forward = Path.PAGE_NEW_PASSWORD;
				return forward;
			}
			// hash and save new password
			user.setPassword(hash(user.getPassword()));
			manager.updateUserPassword(Long.parseLong(idUser),
					user.getPassword());
			request.setAttribute("login", user.getLogin());
			forward = Path.PAGE_LOGIN;
		}
		LOG.debug("Command finished");
		return forward;
	}
}