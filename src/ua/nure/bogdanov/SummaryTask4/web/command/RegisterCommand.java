package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.exception.DBException;
import ua.nure.bogdanov.SummaryTask4.exception.InvalidDataException;
import ua.nure.bogdanov.SummaryTask4.validator.Validator;
import ua.nure.bogdanov.SummaryTask4.web.helpers.MailHelper;
import ua.nure.bogdanov.SummaryTask4.web.helpers.VerifyRecaptcha;
import ua.nure.bogdanov.SummaryTask4.exception.Messages;

/**
 * Register new user is system.
 * 
 * @author I.Bogdanov
 * 
 */
public class RegisterCommand extends Command {

	private static final long serialVersionUID = -2015785958942333088L;

	private static final Logger LOG = Logger.getLogger(RegisterCommand.class);
	

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		String forward = Path.PAGE_ERROR_PAGE;
		String xmlPath = request.getServletContext().getRealPath("") + XML_PATH;
		DBManager manager = DBManager.getInstance();

		User user = new User();
		user.setRoleId(1);
		// get params from request
		user.setLogin(request.getParameter("login"));
		LOG.trace("Request parameter: loging --> " + user.getLogin());
		user.setPassword(request.getParameter("password"));
		LOG.trace("Request parameter: password --> " + user.getPassword());
		user.setEmail(request.getParameter("email"));
		LOG.trace("Request parameter: email --> " + user.getEmail());
		user.setFirstName(request.getParameter("firstName"));
		LOG.trace("Request parameter: firstName --> " + user.getFirstName());
		user.setLastName(request.getParameter("lastName"));
		LOG.trace("Request parameter: lastName --> " + user.getLastName());

		// Verify REcaptcha
		String gRecaptchaResponse = request
				.getParameter("g-recaptcha-response");
		System.out.println(gRecaptchaResponse);
	/*	boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
		if (!verify) {
			LOG.error("Break whith error --> Incorrect captcha");
			session.setAttribute(ERROR_ATTRIBUTE, "Incorrect captcha");
			session.setAttribute("temp", user);
			forward = Path.PAGE_REGISTER;
			return forward;
		}*/

		try {
			// validate user data
			Validator validator = new Validator(xmlPath);
			validateUser(user, request.getParameter("password_conf"), validator);
		} catch (InvalidDataException e) {
			LOG.error("Break whith error --> " + e.getMessage());
			session.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
			session.setAttribute("temp", user);
			forward = Path.PAGE_REGISTER;
			return forward;
		}
		user.setPassword(hash(user.getPassword()));
		// save changes in db
		user = manager.createNewUser(user);
		LOG.trace("Create new user --> " + user);

		try {
			sendWeclomeMessage(user);
		} catch (Exception ex) {
			session.setAttribute(ERROR_ATTRIBUTE, ex.getMessage());
			LOG.error("Set the request attribute: errorMessage --> "
					+ ex.getMessage());
			forward = Path.PAGE_REGISTER;
		}
		session.setAttribute("temp", null);
		session.setAttribute(ERROR_ATTRIBUTE, Messages.MESSAGE_REGISTER);
		session.setAttribute("login", user.getLogin());

		forward = Path.PAGE_LOGIN;
		LOG.debug("Command finished");
		return forward;
	}

	/**
	 * Validating user and throws {@link InvalidDataException} if field is not
	 * valid
	 * 
	 * @param user
	 *            user data
	 * @param passwordConf
	 *            new password
	 * @param val
	 *            Validator with which validate
	 * @throws InvalidDataException
	 * @throws DBException
	 */
	private void validateUser(User user, String passwordConf, Validator val)
			throws InvalidDataException, DBException {

		DBManager manager = DBManager.getInstance();
		Validator validator = val;
		validator.isValid(user.getLogin(), "Login");
		validator.isValid(user.getLastName(), "LastName");
		validator.isValid(user.getFirstName(), "FirstName");
		validator.isValid(user.getPassword(), "Password");
		validator.isValid(user.getEmail(), "Email");

		if (manager.findUserByLogin(user.getLogin()) != null) {
			throw new InvalidDataException("Current login is already use");
		}
		if (manager.findUserByEmail(user.getEmail()) != null) {
			throw new InvalidDataException("Current email already in use");
		}
		validator.isValid(passwordConf, "ConfPassword");
		if (user.getPassword().compareTo(passwordConf) != 0) {
			throw new InvalidDataException("Entered passwords do not match");
		}
	}

	private void sendWeclomeMessage(User user) throws MessagingException {
		String email = user.getEmail();
		String subject = "Welcome to my site!";
		StringBuilder sb = new StringBuilder();
		sb.append("Hi, ").append(user.getFirstName())
				.append(", thanks for register at my site!")
				.append(System.lineSeparator());
		sb.append("Your login - ").append(user.getLogin());
		MailHelper.sendMailCongratulations(email, subject, sb.toString());
	}

}
