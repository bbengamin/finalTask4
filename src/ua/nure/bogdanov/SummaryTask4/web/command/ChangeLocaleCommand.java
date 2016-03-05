package ua.nure.bogdanov.SummaryTask4.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.DBException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Change current locale command.
 * 
 * @author I.Bogdanov
 * 
 */
public class ChangeLocaleCommand extends Command {

	private static final long serialVersionUID = -6538100486848233124L;
	private static final Logger LOG = Logger
			.getLogger(ChangeLocaleCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws DBException {
		LOG.debug("Command starts");
		HttpSession session = request.getSession();
		String locale = request.getParameter("locale");
		DBManager manager = DBManager.getInstance();
		// set new locale to session
		session.setAttribute("currentLocale", locale);
		LOG.trace("Set the session attribute: currentLocale --> " + locale);

		// set new locale to fmt
		Config.set(session, Config.FMT_LOCALE, new java.util.Locale(locale));
		LOG.trace("Set the session attribute: locale --> " + locale);

		// set locale to user profile in db
		User user = (User) session.getAttribute("user");
		if (user != null) {
			manager.updateUserLocale(user.getId(), locale);
		}

		LOG.debug("Command finished");
		return Path.PAGE_SETTINGS;
	}

}