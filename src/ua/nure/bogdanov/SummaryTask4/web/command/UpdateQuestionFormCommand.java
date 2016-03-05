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
import ua.nure.bogdanov.SummaryTask4.db.entity.Answer;
import ua.nure.bogdanov.SummaryTask4.db.entity.Question;
import ua.nure.bogdanov.SummaryTask4.db.entity.Subject;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Generate form to update question
 * 
 * @author I.Bogdanov
 * 
 */
public class UpdateQuestionFormCommand extends Command {

	private static final long serialVersionUID = 2971094913454148453L;

	private static final Logger LOG = Logger
			.getLogger(UpdateQuestionFormCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;

		DBManager manager = DBManager.getInstance();
		List<Subject> subjects = manager.findAllSubjects();
		// get parameters from requset
		LOG.trace("Set the request attribute: subjects --> " + subjects);
		request.setAttribute("subjects", subjects);
		int idTest = Integer.parseInt(request.getParameter("idTest"));
		LOG.trace("Set the request attribute: idTest --> " + idTest);
		request.setAttribute("idTest", idTest);
		Complexity[] complexitys = Complexity.values();
		request.setAttribute("complexitys", complexitys);

		// set info into request
		String idQuestionStr = request.getParameter("idQuestion");
		Question question = null;
		List<Answer> answers = null;
		if (idQuestionStr != null && !idQuestionStr.isEmpty()) {
			question = manager
					.findQuestionById(Integer.parseInt(idQuestionStr));
			LOG.trace("Set the request attribute: question --> " + question);
			request.setAttribute("question", question);

			answers = manager.findAnswersByQuestionId(question.getId());
			LOG.trace("Set the request attribute: answers --> " + answers);
			request.setAttribute("answers", answers);
		}

		forward = Path.PAGE_UPDATE_QUESTION;

		LOG.debug("Command finished");
		return forward;
	}

}
