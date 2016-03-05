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
 * Start test command
 * 
 * @author I.Bogdanov
 * 
 */
public class StartTestCommand extends Command {
	private static final long serialVersionUID = -7935016323609259816L;

	private static final Logger LOG = Logger.getLogger(StartTestCommand.class);
	private Map<User, TimerThread> timers = null;

	public StartTestCommand(Map<User, TimerThread> timers) {
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

			String idTest = request.getParameter("idTest");
			LOG.trace("Request parameter: idTest --> " + idTest);
			User user = (User) session.getAttribute("user");
			LOG.trace("Session parameter: user --> " + user);

			Test test = manager.findTestById(Integer.parseInt(idTest));

			session.setAttribute("test", test);
			LOG.trace("Set the session attribute: test --> " + test);
			// get list questions from db
			List<Question> questions = manager.findQuestionsByTestId(test
					.getId());
			if (questions.size() > 0) {
				request.setAttribute("questionNumber", 1);
				LOG.trace("Set the request attribute: questionNumber --> " + 1);
				session.setAttribute("questionCount", questions.size());
				LOG.trace("Set the session attribute: questionCount --> "
						+ questions.size());
				// set first question to request
				request.setAttribute("question", questions.get(0));
				LOG.trace("Set the request attribute: question --> "
						+ questions.get(0));
				// set list of answer to request
				List<Answer> answers = manager
						.findAnswersByQuestionId(questions.get(0).getId());
				request.setAttribute("answers", answers);
				LOG.trace("Set the request attribute: answers --> " + answers);
			}

			// starting timer
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
			String timerStr = "00:" + thread.getTime();
			LOG.trace("Set the request attribute: timer --> " + timerStr);
			request.setAttribute("timer", timerStr);

			forward = Path.PAGE_TEST;
			LOG.debug("Command finished");
			return forward;
		}
	}
}
