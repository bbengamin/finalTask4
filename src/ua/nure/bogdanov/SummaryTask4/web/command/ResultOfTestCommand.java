package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Results of test command
 * 
 * @author I.Bogdanov
 * 
 */
public class ResultOfTestCommand extends Command {
	private static final long serialVersionUID = -7935016323609259816L;

	private static final Logger LOG = Logger
			.getLogger(ResultOfTestCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {

		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;
		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();
		// get parameters from session
		User user = (User) session.getAttribute("user");
		String idTest = request.getParameter("idTest");
		LOG.trace("Request parameter: idTest --> " + idTest);
		// get results from db
		Double[] userResult = manager.getUserResultsByTestId(
				Long.parseLong(idTest), user.getId());

		Arrays.sort(userResult);
		double userMaxResult = userResult[userResult.length - 1];
		// set max user result to request
		String rez = String.format("%.2f", userMaxResult);
		request.setAttribute("userMaxResult", rez);
		LOG.trace("Set request parametr: userMaxResult --> " + rez);

		forward = Path.PAGE_RESULTS;
		LOG.debug("Command finished");
		return forward;
	}
}
