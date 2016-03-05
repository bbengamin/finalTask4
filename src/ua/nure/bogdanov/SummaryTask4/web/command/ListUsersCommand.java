package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * List user items
 * 
 * @author I.Bogdanov
 * 
 */
public class ListUsersCommand extends Command {

	private static final long serialVersionUID = -7954267373112543495L;
	private static final Logger LOG = Logger.getLogger(ListUsersCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		// get user items from db
		DBManager manager = DBManager.getInstance();
		List<User> users = manager.findAllUsers();
		// put items into request
		request.setAttribute("users", users);
		LOG.trace("Set the request attribute: users --> " + users);

		LOG.debug("Command finished");
		return Path.PAGE_USERS;
	}

}
