package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.bean.UserTestBean;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * No command.
 * 
 * @author I.Bogdanov
 * 
 */
public class ViewProfileCommand extends Command {

	private static final long serialVersionUID = 1043441273004473355L;

	private static final Logger LOG = Logger
			.getLogger(ViewProfileCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession(true);
		DBManager manager = DBManager.getInstance();
		User user = null;
		User currentUser = (User) session.getAttribute("user");

		request.setAttribute("message", request.getParameter("message"));

		String login = request.getParameter("login");
		if (login == null || login.isEmpty()) {
			user = currentUser;
			request.setAttribute("enabledToChange", true);
			LOG.trace("Set the request attribute: enabledToChange --> " + true);
		} else if (login.compareTo(currentUser.getLogin()) == 0) {
			user = currentUser;
			request.setAttribute("enabledToChange", true);
			LOG.trace("Set the request attribute: enabledToChange --> " + true);
		} else {

			user = manager.findUserByLogin(login);
			request.setAttribute("enabledToChange", false);
			LOG.trace("Set the request attribute: enabledToChange --> " + false);
		}

		request.setAttribute("userProfile", user);
		LOG.trace("Set the request attribute: userProfile --> " + user);
		// get info from db
		List<UserTestBean> tests = manager.findUserTestBeanByUserId(user
				.getId());
		// set info to request
		request.setAttribute("UserTestBeans", tests);
		LOG.trace("Set the request attribute: UserTestBeans --> " + tests);

		LOG.debug("Command finished");
		return Path.PAGE_PROFILE;
	}
}
