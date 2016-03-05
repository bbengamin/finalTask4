package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.Fields;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.exception.InvalidDataException;
import ua.nure.bogdanov.SummaryTask4.exception.Messages;
import ua.nure.bogdanov.SummaryTask4.validator.Validator;
import ua.nure.bogdanov.SummaryTask4.web.helpers.MailHelper;

/**
 * Restore password command.
 * 
 * @author I.Bogdanov
 * 
 */
public class RestorePasswordCommand extends Command {

	private static final long serialVersionUID = -3071536593627692473L;

	private static final Logger LOG = Logger
			.getLogger(RestorePasswordCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;
		String xmlPath = request.getServletContext().getRealPath("") + XML_PATH;
		DBManager manager = DBManager.getInstance();
		// get info from request
		User user = null;
		String login = request.getParameter(Fields.USER_LOGIN);
		LOG.trace("Request parameter: login --> " + login);

		Validator validator;
		try {
			validator = new Validator(xmlPath);
		} catch (InvalidDataException e) {
			throw new AppException("Validator error", e);
		}
		// validating data
		validator.isValidWithOutEx(login, Fields.USER_LOGIN);

		if (validator.isValidWithOutEx(login, "Email")) {
			user = manager.findUserByEmail(login);
		} else if (validator.isValidWithOutEx(login, Fields.USER_LOGIN)) {
			user = manager.findUserByLogin(login);
		} else {
			LOG.trace("Break whith error --> Invalid login or email");
			request.setAttribute(ERROR_ATTRIBUTE, "Invalid login or email");
			request.setAttribute(Fields.USER_LOGIN, login);
			forward = Path.PAGE_RESTORE_PASSWORD;
			return forward;
		}

		if (user == null) {
			LOG.trace("Break whith error --> " + Messages.MESSAGE_FIND_USER);
			request.setAttribute(ERROR_ATTRIBUTE, Messages.MESSAGE_FIND_USER);
			request.setAttribute(Fields.USER_LOGIN, login);
			forward = Path.PAGE_RESTORE_PASSWORD;
			return forward;
		}
		// if user blocked
		if (user.isStatus()) {
			String errorMessasge = "You are blocked! \n Administator message: "
					+ user.getBlockMessage();

			request.setAttribute(ERROR_ATTRIBUTE, errorMessasge);
			LOG.trace("Set request param: errorMessage --> " + errorMessasge);

			forward = Path.PAGE_ERROR_PAGE;
			LOG.debug("Command finished");
			return forward;
		}

		try {
			// send mail to user with restore link
			String key = sendMail(user);
			manager.updateUserPassword(user.getId(), key);
		} catch (Exception ex) {
			request.setAttribute(ERROR_ATTRIBUTE, ex.getMessage());
			LOG.error("Set request param: errorMessage --> " + ex.getMessage());
			forward = Path.PAGE_RESTORE_PASSWORD;
		}

		request.setAttribute(ERROR_ATTRIBUTE, "Check your e-mail");
		LOG.error("Set request param: errorMessage --> Check your e-mail");
		forward = Path.PAGE_RESTORE_PASSWORD;
		LOG.debug("Command finished");
		return forward;
	}

	/**
	 * Send mail to user with restore link
	 * 
	 * @param user
	 * @return
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private String sendMail(User user) throws MessagingException {
		Random rand = new Random();
		String key = String.valueOf(rand.nextInt());
		String href = "http://localhost:8080/SummaryTask4/controller?command=newPassword&idUser="
				+ user.getId() + "&key=" + key;
		String email = user.getEmail();
		String subject = "Password";
		StringBuilder sb = new StringBuilder();
		sb.append("Hi, ").append(user.getFirstName())
				.append(", use this link to restore your password! ")
				.append(System.lineSeparator()).append(href);
		MailHelper.sendMailCongratulations(email, subject, sb.toString());
		return key;
	}
}