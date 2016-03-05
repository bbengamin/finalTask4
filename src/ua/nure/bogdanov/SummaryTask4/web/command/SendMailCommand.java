package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;
import ua.nure.bogdanov.SummaryTask4.web.helpers.MailHelper;

/**
 * Send mail command
 * 
 * @author I.Bogdanov
 * 
 */
public class SendMailCommand extends Command {

	private static final long serialVersionUID = -5498433181969451372L;

	private static final Logger LOG = Logger.getLogger(SendMailCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		String forward = Path.COMMAND_LIST_TESTS;
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		// get info from request
		String email = request.getParameter("mail");
		String subject = request.getParameter("subject");
		String message = "From: " + email + "Message: "
				+ request.getParameter("message");
		// validating parameters
		if (isValidParameters(email, subject, message)) {
			try {
				// send user mail
				MailHelper.sendMail(email, subject, message);
				session.setAttribute(ERROR_ATTRIBUTE,
						"Your mail has been send!");
				LOG.error("Set the session attribute: errorMessage --> Your mail has been send!");
				forward = Path.PAGE_MAIL;
			} catch (Exception ex) {
				session.setAttribute(ERROR_ATTRIBUTE, ex.getMessage());
				LOG.error("Set the request attribute: errorMessage --> "
						+ ex.getMessage());
				forward = Path.PAGE_ERROR_PAGE;
			}
		} else {
			session.setAttribute(ERROR_ATTRIBUTE, "Error");
			LOG.error("Set the request attribute: errorMessage --> " + "Error");
			forward = Path.PAGE_ERROR_PAGE;
		}

		LOG.debug("Command finished");
		return forward;
	}

	/**
	 * Validating parameters
	 * 
	 * @param parameters
	 * @return true if all parameters is valid
	 */
	private boolean isValidParameters(String... parameters) {
		boolean isValid = true;
		for (String param : parameters) {
			if (param == null || param.isEmpty()) {
				isValid = false;
				break;
			}
		}
		return isValid;
	}

}
