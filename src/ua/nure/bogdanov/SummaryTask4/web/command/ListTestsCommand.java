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
import ua.nure.bogdanov.SummaryTask4.db.bean.TestBean;
import ua.nure.bogdanov.SummaryTask4.db.entity.Subject;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * List test command
 * 
 * @author I.Bogdanov
 * 
 */
public class ListTestsCommand extends Command {

	private static final long serialVersionUID = -5498433181969451372L;

	private static final Logger LOG = Logger.getLogger(ListTestsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {

		LOG.debug("Command starts");
		DBManager manager = DBManager.getInstance();

		// get test items list
		List<TestBean> listTestsBeans = manager.findAllTestsBean();
		LOG.trace("Found in DB: listTestsBeans --> " + listTestsBeans);

		List<Subject> subjects = manager.findAllSubjects();

		LOG.trace("Set the request attribute: subjects --> " + subjects);
		request.setAttribute("subjects", subjects);
		long idTestMinResult = 0;
		double minResult = 100;
		for (TestBean test : listTestsBeans) {
			double result = manager.getTestResult(test.getId());
			if (result < minResult) {
				idTestMinResult = test.getId();
				minResult = result;
			}
		}

		// sort test by name
		Collections.sort(listTestsBeans, new Comparator<TestBean>() {
			public int compare(TestBean o1, TestBean o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		request.setAttribute("minResult", idTestMinResult);
		LOG.trace("Set the request attribute: minResult --> " + idTestMinResult);

		// put test items list to the request
		request.setAttribute("listTestsBeans", listTestsBeans);
		LOG.trace("Set the request attribute: listTestsBeans --> "
				+ listTestsBeans);

		LOG.debug("Command finished");
		return Path.PAGE_INDEX;
	}

}
