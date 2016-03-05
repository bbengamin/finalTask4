package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;

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
 * Update subject command
 * 
 * @author I.Bogdanov
 * 
 */
public class UpdateSubjectCommand extends Command {
	private static final long serialVersionUID = -1163448123650388694L;

	private static final Logger LOG = Logger
			.getLogger(UpdateSubjectCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;
		DBManager manager = DBManager.getInstance();
		Subject subject = new Subject();
		// get info from request
		subject.setName(request.getParameter("name"));
		LOG.trace("Request parameter: name --> " + subject.getName());
		String idSubject = request.getParameter("idSubject");
		LOG.trace("Request parameter: idSubject --> " + idSubject);
		// save changes in db
		if (idSubject != null && !idSubject.isEmpty()) {
			
			 subject.setId(Long.parseLong(idSubject)); 
			 subject = manager.updateSubject(subject);
			 LOG.trace("Update subject --> " +  subject);
			 
		} else {
			subject = manager.createNewSubject(subject);
			LOG.trace("Create subject --> " + subject);
		}

		forward = Path.COMMAND_LIST_SUBJECTS;

		LOG.debug("Command finished");
		return forward;
	}
}
