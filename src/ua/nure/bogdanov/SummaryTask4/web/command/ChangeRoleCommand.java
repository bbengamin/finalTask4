package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;

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
 * Change role of user.
 * 
 * @author I.Bogdanov
 * 
 */
public class ChangeRoleCommand extends Command {

	private static final long serialVersionUID = 4382382807501633513L;

	private static final Logger LOG = Logger.getLogger(ChangeRoleCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;

		DBManager manager = DBManager.getInstance();
		// get request params
		String idUser = request.getParameter("idUser");
		LOG.trace("Request parameter: idUser --> " + idUser);

		// get user from db
		User user = manager.findUserById(Long.parseLong(idUser));
		if (user != null) {
			if (user.getRoleId() == 0) {
				manager.updateUserRole(user.getId(), 1);
			}
			if (user.getRoleId() == 1) {
				manager.updateUserRole(user.getId(), 0);
			}
			if (user.getLogin() != null) {
				forward = Path.COMMAND_PROFILE + "&login=" + user.getLogin();
			}
		}
		LOG.debug("Command finished");
		return forward;
	}
}
