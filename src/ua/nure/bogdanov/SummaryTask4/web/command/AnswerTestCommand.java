package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.Answer;
import ua.nure.bogdanov.SummaryTask4.db.entity.Question;
import ua.nure.bogdanov.SummaryTask4.db.entity.Test;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.timer.TimerThread;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Do answer test command
 * 
 * @author I.Bogdanov
 * 
 */
public class AnswerTestCommand extends Command {

	private static final long serialVersionUID = -3028418245433383650L;
	private static final Logger LOG = Logger.getLogger(AnswerTestCommand.class);
	private Map<User, TimerThread> timers = null;

	public AnswerTestCommand(Map<User, TimerThread> timers) {
		this.timers = timers;
	}

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		synchronized (timers) {

			LOG.debug("Command starts");
			String forward = Path.PAGE_ERROR_PAGE;
			HttpSession session = request.getSession();
			DBManager manager = DBManager.getInstance();
			// get info from request and sesion
			Test test = (Test) session.getAttribute("test");
			LOG.trace("Session parameter: test --> " + test);
			String idQuestion = request.getParameter("idQuestion");
			LOG.trace("Request parameter: idQuestion --> " + idQuestion);

			User user = (User) session.getAttribute("user");
			LOG.trace("Session parameter: user --> " + user);

			// get user answers to session
			List<Answer> answers = manager.findAnswersByQuestionId(Long
					.parseLong(idQuestion));
			String answersStr = null;
			for (int i = 0; i < answers.size(); i++) {
				String checked = request.getParameter("answer"
						+ answers.get(i).getId());
				if (checked != null && checked.compareTo("on") == 0) {
					if (answersStr == null) {
						answersStr = answers.get(i).getId().toString();
					} else {
						answersStr = answersStr + ","
								+ answers.get(i).getId().toString();
					}
				}
			}
			// save user answers to session
			session.setAttribute(idQuestion, answersStr);
			LOG.trace("Set the session attribute: " + idQuestion + " --> "
					+ answersStr);

			Question currentQuestion = manager.findQuestionById(Integer
					.parseInt(idQuestion));
			// get list of question grom db
			List<Question> questions = manager.findQuestionsByTestId(test
					.getId());
			if (questions.size() > 0) {
				int cursor = 0;
				// find index of current question
				for (Question questionBean : questions) {
					cursor++;
					if (questionBean.getId().compareTo(currentQuestion.getId()) == 0) {
						break;
					}
				}
				if (cursor == questions.size()) {
					cursor = 0;
				}
				// save naxt question and list answers to request
				String attr = (String) session.getAttribute(questions
						.get(cursor).getId().toString());
				request.setAttribute("checked", attr);
				LOG.trace("Set the request attribute: checked --> " + attr);
				request.setAttribute("questionNumber", cursor + 1);
				LOG.trace("Set the request attribute: questionNumber --> "
						+ (cursor + 1));

				request.setAttribute("question", questions.get(cursor));
				LOG.trace("Set the request attribute: question --> "
						+ questions.get(cursor));

				answers = manager.findAnswersByQuestionId(questions.get(cursor)
						.getId());
				request.setAttribute("answers", answers);
				LOG.trace("Set the request attribute: answers --> " + answers);
			}
			// check timer
			if (timers.get(user) == null) {
				TimerThread thread = new TimerThread(user.getId(),
						test.getId(), test.getTimer());
				thread.setDaemon(true);
				thread.start();
				timers.put(user, thread);
			}
			TimerThread thread = timers.get(user);
			if (!thread.isAlive()) {
				thread = new TimerThread(user.getId(), test.getId(),
						test.getTimer());
				thread.setDaemon(true);
				thread.start();
				timers.put(user, thread);
			}
			// set time to request
			String timerStr = "00:" + thread.getTime();
			LOG.trace("Set the request attribute: timer --> " + timerStr);
			request.setAttribute("timer", timerStr);

			forward = Path.PAGE_TEST;
			LOG.debug("Command finished");
			return forward;
		}
	}
}
