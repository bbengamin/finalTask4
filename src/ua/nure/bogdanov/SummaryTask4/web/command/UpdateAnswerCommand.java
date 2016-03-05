package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.Answer;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Update answer command
 * 
 * @author I.Bogdanov
 * 
 */
public class UpdateAnswerCommand extends Command {
	private static final long serialVersionUID = -1163448123650388694L;

	private static final Logger LOG = Logger
			.getLogger(UpdateAnswerCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;
		DBManager manager = DBManager.getInstance();
		Answer answer = new Answer();
		// get info from request
		answer.setBody(request.getParameter("body"));
		LOG.trace("Request parameter: body --> " + answer.getBody());
		answer.setQuestion(Long.parseLong(request.getParameter("idQuestion")));
		LOG.trace("Request parameter: idQuestion --> " + answer.getQuestion());
		String idTest = request.getParameter("idTest");
		LOG.trace("Request parameter: idTest --> " + idTest);
		String idAnswer = request.getParameter("idAnswer");
		LOG.trace("Request parameter: idTest --> " + idAnswer);

		String correct = request.getParameter("correct");
		if (correct == null) {
			answer.setCorrect(false);
		} else if (request.getParameter("correct").compareTo("on") == 0) {
			answer.setCorrect(true);
		}
		LOG.trace("Request parameter: correct --> " + answer.getCorrect());

		// save changes in db
		if (idAnswer != null && !idAnswer.isEmpty()) {
			answer.setId(Long.parseLong(idAnswer));
			answer = manager.updateAnswer(answer);
			LOG.trace("Update new answer --> " + answer);
		} else {
			answer = manager.createNewAnswer(answer);
			LOG.trace("Create answer --> " + answer);
		}

		forward = Path.COMMAND_LIST_ANSWERS + idTest + "&idQuestion="
				+ answer.getQuestion();

		LOG.debug("Command finished");
		return forward;
	}
}
