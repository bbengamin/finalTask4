package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.Complexity;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.Question;
import ua.nure.bogdanov.SummaryTask4.db.entity.Subject;
import ua.nure.bogdanov.SummaryTask4.db.entity.Test;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Generate form to update test.
 * 
 * @author I.Bogdanov
 * 
 */
public class UpdateTestFormCommand extends Command {

	private static final long serialVersionUID = 8520127427965683275L;

	private static final Logger LOG = Logger
			.getLogger(UpdateTestFormCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;

		DBManager manager = DBManager.getInstance();
		List<Subject> subjects = manager.findAllSubjects();
		// get request parameters
		LOG.trace("Set the request attribute: subjects --> " + subjects);
		request.setAttribute("subjects", subjects);
		Complexity[] complexitys = Complexity.values();
		request.setAttribute("complexitys", complexitys);

		String idTestStr = request.getParameter("id");
		Test test = null;
		List<Question> questions = null;
		// get info from db, and set info into request
		if (idTestStr != null) {
			test = manager.findTestById(Integer.parseInt(idTestStr));
			LOG.trace("Set the request attribute: test --> " + test);
			request.setAttribute("test", test);

			questions = manager.findQuestionsByTestId(test.getId());
			LOG.trace("Set the request attribute: questions --> " + questions);
			request.setAttribute("questions", questions);
		}

		forward = Path.PAGE_ADD_TEST;

		LOG.debug("Command finished");
		return forward;
	}

}
