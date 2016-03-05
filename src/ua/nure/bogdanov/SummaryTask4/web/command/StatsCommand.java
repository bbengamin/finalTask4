package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.bean.UserTestBean;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Get statistics.
 * 
 * @author I.Bogdanov
 * 
 */
public class StatsCommand extends Command {

	private static final long serialVersionUID = -6958020196937669013L;
	private static final Logger LOG = Logger.getLogger(StatsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		DBManager manager = DBManager.getInstance();
		List<UserTestBean> tests = null;
		// get request parameters
		String idTest = request.getParameter("idTest");
		LOG.debug("Request parametr: idTest --> " + idTest);
		if (idTest != null && !idTest.isEmpty()) {
			tests = manager.findUserTestBeanByTestId(Long.parseLong(idTest));
		} else {
			tests = manager.findAllUserTestBean();
		}
		// put results into request
		request.setAttribute("UserTestBeans", tests);
		LOG.debug("Set the request attribute: UserTestBeans --> " + tests);

		request.setAttribute("idTest", idTest);
		LOG.debug("Set the request attribute: idTest --> " + idTest);

		LOG.debug("Command finished");
		return Path.PAGE_STATS;
	}

}
