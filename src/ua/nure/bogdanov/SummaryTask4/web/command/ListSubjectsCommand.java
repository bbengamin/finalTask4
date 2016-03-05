package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.Subject;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * List test command
 * 
 * @author I.Bogdanov
 * 
 */
public class ListSubjectsCommand extends Command {

	private static final long serialVersionUID = -5498433181969451372L;

	private static final Logger LOG = Logger
			.getLogger(ListSubjectsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {

		LOG.debug("Command starts");
		DBManager manager = DBManager.getInstance();

		// get subjects items list
		List<Subject> list = manager.findAllSubjects();

		// sort test by name
		Collections.sort(list, new Comparator<Subject>() {
			public int compare(Subject o1, Subject o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		LOG.trace("Set the request attribute: list --> " + list);
		request.setAttribute("list", list);

		LOG.debug("Command finished");
		return Path.PAGE_SUBJECTS;
	}

}
