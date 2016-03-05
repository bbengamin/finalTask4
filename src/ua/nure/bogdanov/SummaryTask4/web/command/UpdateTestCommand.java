package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.Complexity;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.Test;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Update test command
 * 
 * @author I.Bogdanov
 * 
 */
public class UpdateTestCommand extends Command {
	private static final long serialVersionUID = -1163448123650388694L;

	private static final Logger LOG = Logger.getLogger(UpdateTestCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;

		DBManager manager = DBManager.getInstance();
		Test test = new Test();
		// get parameters from request
		test.setName(request.getParameter("name"));
		LOG.trace("Request parameter: name --> " + test.getName());
		test.setSubject(Integer.parseInt(request.getParameter("sub")));
		LOG.trace("Request parameter: subjects --> " + test.getSubject());
		test.setComplexity(Complexity.valueOf(
				request.getParameter("compl").toUpperCase()).getId());
		LOG.trace("Request parameter: complexitys --> " + test.getComplexity());
		test.setTimer(Integer.parseInt(request.getParameter("time")));
		LOG.trace("Request parameter: time --> " + test.getTimer());
		String idTest = request.getParameter("idTest");
		// save changes into db
		if (idTest != null && !idTest.isEmpty()) {
			test.setId(Long.parseLong(idTest));
			test = manager.updateTest(test);
			LOG.trace("Update new test --> " + test);
		} else {
			test = manager.createNewTest(test);
			LOG.trace("Create test --> " + test);
		}

		forward = Path.COMMAND_LIST_TESTS + "&id=" + test.getId();

		LOG.debug("Command finished");
		return forward;
	}
}
