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
 * Generate form to update answer
 * 
 * @author I.Bogdanov
 * 
 */
public class UpdateAnswerFormCommand extends Command {

	private static final long serialVersionUID = 2971094913454148453L;

	private static final Logger LOG = Logger
			.getLogger(UpdateAnswerFormCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;

		DBManager manager = DBManager.getInstance();

		// get parameters from request
		int idTest = Integer.parseInt(request.getParameter("idTest"));
		LOG.trace("Request parameter: idTest --> " + idTest);
		String idQuestion = request.getParameter("idQuestion");
		request.setAttribute("idTest", idTest);
		LOG.trace("Request parameter: idQuestion --> " + idQuestion);
		request.setAttribute("idQuestion", idQuestion);

		String idAnswerStr = request.getParameter("idAnswer");
		LOG.trace("Request parameter: idAnswer --> " + idAnswerStr);
		// get info from db, and put results into request
		Answer answer = null;
		if (idAnswerStr != null && !idAnswerStr.isEmpty()) {
			answer = manager.findAnswerById(Integer.parseInt(idAnswerStr));
			LOG.trace("Set the request attribute: answer --> " + answer);
			request.setAttribute("answer", answer);
		}

		forward = Path.PAGE_UPDATE_ANSWER;

		LOG.debug("Command finished");
		return forward;
	}

}
