package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.Enumeration;
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
 * Finish test command
 * 
 * @author I.Bogdanov
 * 
 */
public class FinishTestCommand extends Command {
	private static final long serialVersionUID = -7935016323609259816L;

	private static final Logger LOG = Logger.getLogger(FinishTestCommand.class);
	private Map<User, TimerThread> timers = null;

	public FinishTestCommand(Map<User, TimerThread> timers) {
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
			User user = (User) session.getAttribute("user");
			LOG.trace("Session parameter: user --> " + user);

			if (timers.get(user) != null) {
				TimerThread thread = timers.get(user);
				thread.interrupt();
				timers.remove(user);
			}
			// get list of questions
			List<Question> questions = manager.findQuestionsByTestId(test
					.getId());
			int result = 0;
			for (Question question : questions) {
				String currentQuestionResult = "";
				// get user answer
				String answer = String.valueOf(session.getAttribute(question
						.getId().toString()));
				List<Answer> answers = manager.findCorrectAnswers(question
						.getId());
				// results
				for (Answer answ : answers) {
					if (currentQuestionResult.isEmpty()) {
						currentQuestionResult = answ.getId().toString();
					} else {
						currentQuestionResult = currentQuestionResult + ","
								+ answ.getId().toString();
					}
				}
				if (currentQuestionResult.compareTo(answer) == 0) {
					result++;
				}
			}
			double userResult = ((double) result / questions.size()) * 100;
			// set uesr result to db
			manager.setUserResult(test.getId(), user.getId(), userResult);
			// clear session parameters
			Enumeration<String> enn = session.getAttributeNames();
			String temp = null;
			while (enn.hasMoreElements()) {
				temp = enn.nextElement();
				if (temp.compareTo("user") != 0
						&& temp.compareTo("userRole") != 0) {
					session.removeAttribute(temp);
				}
			}
			// put results into session
			String rez = String.format("%.2f", userResult);
			session.setAttribute("lastTestResult", rez);
			forward = Path.COMMAND_SHOW_RESULTS + "&idTest=" + test.getId();
			LOG.debug("Command finished");
			return forward;
		}
	}
}
