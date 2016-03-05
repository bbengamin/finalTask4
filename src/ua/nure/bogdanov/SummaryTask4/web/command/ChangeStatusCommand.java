package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Change status of user.
 * 
 * @author I.Bogdanov
 * 
 */
public class ChangeStatusCommand extends Command {

	private static final long serialVersionUID = 4382382807501633513L;

	private static final Logger LOG = Logger
			.getLogger(ChangeStatusCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		DBManager manager = DBManager.getInstance();
		// get request params
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);
		boolean currentStatus = Boolean.valueOf(request
				.getParameter("currentStatus"));
		LOG.trace("Request parameter: currentStatus --> " + currentStatus);
		String blockMessage = request.getParameter("block_message");
		LOG.trace("Request parameter: block_message --> " + blockMessage);

		if (blockMessage == null || blockMessage.isEmpty()) {
			blockMessage = "Fuck society!";
		}
		// change status id db
		manager.changeStatusOfUser(login, !currentStatus, blockMessage);
		String forward = Path.COMMAND_PROFILE + "&login=" + login;
		LOG.debug("Command finished");
		return forward;
	}
}
