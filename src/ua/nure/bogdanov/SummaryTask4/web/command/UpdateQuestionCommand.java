package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.Question;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Update question command
 * 
 * @author I.Bogdanov
 * 
 */
public class UpdateQuestionCommand extends Command {
	private static final long serialVersionUID = -1163448123650388694L;

	private static final Logger LOG = Logger
			.getLogger(UpdateQuestionCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");

		String forward = Path.PAGE_ERROR_PAGE;
		DBManager manager = DBManager.getInstance();
		// get parameters from request
		Question question = new Question();
		question.setBody(request.getParameter("body"));
		LOG.trace("Request parameter: body --> " + question.getBody());
		request.removeAttribute("body");
		int idTest = Integer.parseInt(request.getParameter("idTest"));
		LOG.trace("Request parameter: idTest --> " + idTest);

		// write changes into db
		String idQuestion = request.getParameter("idQuestion");
		if (idQuestion != null && !idQuestion.isEmpty()) {
			question.setId(Long.parseLong(idQuestion));
			question = manager.updateQuestion(question);
			LOG.trace("Update new question --> " + question);
		} else {
			question = manager.createNewQuestion(idTest, question);
			LOG.trace("Create question --> " + question);
		}

		forward = Path.COMMAND_LIST_QUESTIONS + idTest;
		LOG.debug("Command finished");
		return forward;
	}
}
