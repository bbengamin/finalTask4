package ua.nure.bogdanov.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.timer.TimerThread;

/**
 * Holder for all commands.
 * 
 * @author I.Bogdanov
 * 
 */
public class CommandContainer {
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();
	private static Map<User, TimerThread> timers = new TreeMap<User, TimerThread>();

	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("restorePassword", new RestorePasswordCommand());
		commands.put("newPassword", new NewPasswordCommand());
		commands.put("register", new RegisterCommand());
		commands.put("listTests", new ListTestsCommand());
		commands.put("profile", new ViewProfileCommand());
		commands.put("updateProfile", new UpdateProfileCommand());
		commands.put("sendMail", new SendMailCommand());
		commands.put("changeLocale", new ChangeLocaleCommand());

		// client commands

		commands.put("startTest", new StartTestCommand(timers));
		commands.put("finishTest", new FinishTestCommand(timers));
		commands.put("answer", new AnswerTestCommand(timers));
		commands.put("navigation", new NavigationTestCommand(timers));
		commands.put("showResults", new ResultOfTestCommand());
		// admin commands

		commands.put("updateTestForm", new UpdateTestFormCommand());
		commands.put("updateTest", new UpdateTestCommand());
		commands.put("updateQuestionForm", new UpdateQuestionFormCommand());
		commands.put("updateQuestion", new UpdateQuestionCommand());
		commands.put("updateAnswerForm", new UpdateAnswerFormCommand());
		commands.put("updateAnswer", new UpdateAnswerCommand());
		commands.put("delete", new DeleteCommand());
		commands.put("listUsers", new ListUsersCommand());
		commands.put("stats", new StatsCommand());
		commands.put("changeStatus", new ChangeStatusCommand());
		commands.put("generatePdf", new GeneratePDFCommand());
		commands.put("changeRole", new ChangeRoleCommand());
		commands.put("listSubjects", new ListSubjectsCommand());
		commands.put("updateSubject", new UpdateSubjectCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}

}