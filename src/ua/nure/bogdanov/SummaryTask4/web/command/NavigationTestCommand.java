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
 * Navigation test command
 * 
 * @author I.Bogdanov
 * 
 */
public class NavigationTestCommand extends Command {
	private static final long serialVersionUID = 5813899583612136329L;
	private static final Logger LOG = Logger
			.getLogger(NavigationTestCommand.class);
	private Map<User, TimerThread> timers = null;

	public NavigationTestCommand(Map<User, TimerThread> timers) {
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
			// get parameters from session
			Test test = (Test) session.getAttribute("test");
			LOG.trace("Session parameter: test --> " + test);
			String idQuestion = request.getParameter("idQuestion");
			LOG.trace("Request parameter: idQuestion --> " + idQuestion);

			User user = (User) session.getAttribute("user");
			LOG.trace("Session parameter: user --> " + user);

			Question currentQuestion = manager.findQuestionById(Integer
					.parseInt(idQuestion));
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
				// setting next question index
				if (request.getParameter("way").compareTo("prev") == 0) {
					cursor -= 2;
				} else if (request.getParameter("way").compareTo("next") != 0) {
					String way = request.getParameter("way");
					try {
						cursor = Integer.parseInt(way) - 1;
					} catch (IllegalArgumentException e) {
						LOG.trace("Error --> " + e.getMessage());
					}
				}
				if (cursor == questions.size()) {
					cursor = 0;
				} else if (cursor < 0) {
					cursor = questions.size() - 1;
				}
				// setting parameters to request
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
				// setting list of answer to request
				List<Answer> answers = manager
						.findAnswersByQuestionId(questions.get(cursor).getId());
				request.setAttribute("answers", answers);
				LOG.trace("Set the request attribute: answers --> " + answers);
			}
			// checking time
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
