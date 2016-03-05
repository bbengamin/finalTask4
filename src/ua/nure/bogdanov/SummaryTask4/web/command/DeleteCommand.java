package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Delete item command.
 * 
 * @author I.Bogdanov
 * 
 */
public class DeleteCommand extends Command {
	private static final long serialVersionUID = -1163448123650388694L;

	private static final Logger LOG = Logger.getLogger(DeleteCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;

		DBManager manager = DBManager.getInstance();
		// get request parameters
		String idTestStr = request.getParameter("idTest");
		LOG.trace("Request parameter: idTest --> " + idTestStr);
		String idQuestionStr = request.getParameter("idQuestion");
		LOG.trace("Request parameter: idQuestion --> " + idQuestionStr);
		String idAnswerStr = request.getParameter("idAnswer");
		LOG.trace("Request parameter: idAnswer --> " + idAnswerStr);
		String idSubjectStr = request.getParameter("idSubject");
		LOG.trace("Request parameter: idSubject --> " + idSubjectStr);

		// delete item id db
		if (idAnswerStr != null && !idAnswerStr.isEmpty()) {
			manager.deleteAnswerById(Integer.parseInt(idAnswerStr));
			forward = Path.COMMAND_LIST_ANSWERS + idTestStr + "&idQuestion="
					+ idQuestionStr;
			LOG.debug("Command finished");
			return forward;
		} else if (idQuestionStr != null && !idQuestionStr.isEmpty()) {
			manager.deleteQuestionById(Integer.parseInt(idQuestionStr));
			forward = Path.COMMAND_LIST_QUESTIONS + idTestStr;
			LOG.debug("Command finished");
			return forward;
		} else if (idTestStr != null && !idTestStr.isEmpty()) {
			manager.deleteTestById(Integer.parseInt(idTestStr));
			forward = Path.COMMAND_LIST_TESTS;
			return forward;
		} else if (idSubjectStr != null && !idSubjectStr.isEmpty()) {
			manager.deleteSubjectById(Long.parseLong(idSubjectStr));
			forward = Path.COMMAND_LIST_SUBJECTS;
		}
		LOG.debug("Command finished");
		return forward;
	}
}
