package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.bean.UserTestBean;
import ua.nure.bogdanov.SummaryTask4.exception.AppException;
import ua.nure.bogdanov.SummaryTask4.exception.Messages;
import ua.nure.bogdanov.SummaryTask4.web.command.Command;

/**
 * Generate PDF command
 * 
 * @author I.Bogdanov
 * 
 */
public class GeneratePDFCommand extends Command {

	private static final long serialVersionUID = -7954267373112543495L;
	private static final String PDF_DIRECTORY = SEPARATOR + "WEB-INF"
			+ SEPARATOR + "pdf";
	private static final Logger LOG = Logger
			.getLogger(GeneratePDFCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException {
		LOG.debug("Command starts");
		String forward = Path.PAGE_ERROR_PAGE;
		DBManager manager = DBManager.getInstance();
		HttpSession session = request.getSession();
		ServletContext context = request.getServletContext();
		List<UserTestBean> tests = null;
		String idTest = request.getParameter("idTest");
		LOG.trace("Request parametr: idTest --> " + idTest);
		if (idTest != null && !idTest.isEmpty()) {
			tests = manager.findUserTestBeanByTestId(Long.parseLong(idTest));
		} else {
			tests = manager.findAllUserTestBean();
		}
		LOG.trace("Collection --> " + tests);

		// constructs the directory path to store upload file
		String uploadPath = context.getRealPath("") + PDF_DIRECTORY;
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists() && (!uploadDir.mkdir())) {
			session.setAttribute("errorMessage", Messages.MESSAGE_FOLDER_ERROR);
			LOG.error("Set attribute: errorMessage --> "
					+ Messages.MESSAGE_FOLDER_ERROR);
			forward = Path.PAGE_ERROR_PAGE;
			LOG.debug("Command finished");
			return forward;
		}
		String filePath = null;
		String fileName = null;
		int i = 0;
		try {
			fileName = "otchet-" + Integer.toString(i) + ".pdf";
			filePath = uploadPath + SEPARATOR + fileName;
			File storeFile = new File(filePath);
			while (storeFile.exists()) {
				i++;
				fileName = "otchet-" + Integer.toString(i) + ".pdf";
				filePath = uploadPath + SEPARATOR + fileName;
				storeFile = new File(filePath);
			}
			createPdf(filePath, tests);
		} catch (DocumentException e) {
			throw new AppException("Can't generate PDF", e);
		}

		forward = PDF_DIRECTORY + SEPARATOR + fileName;
		LOG.debug("Command finished");
		return forward;
	}

	/**
	 * Creates a PDF with information about the items
	 * 
	 * @param filename
	 *            the name of the PDF file that will be created.
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void createPdf(String filename, List<UserTestBean> tests)
			throws IOException, DocumentException {
		// step 1
		Document document = new Document();
		// step 2
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		// step 3
		document.open();
		// step 4
		document.add(createTable(tests));
		// step 5
		document.close();
	}

	/**
	 * Creates table
	 * 
	 * @return table
	 */
	public static PdfPTable createTable(List<UserTestBean> tests) {
		// a table with three columns
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);
		table.setSpacingBefore(0f);
		table.setSpacingAfter(0f);

		// add cells
		table.addCell("Login");
		table.addCell("Test name");
		table.addCell("Complexity");
		table.addCell("Subject");
		table.addCell("Time");
		table.addCell("Count of questions");
		table.addCell("Result");

		// add rows
		for (UserTestBean userTestBean : tests) {
			table.addCell(userTestBean.getLogin());
			table.addCell(userTestBean.getName());
			table.addCell(userTestBean.getComplexity());
			table.addCell(userTestBean.getSubject());
			table.addCell(String.valueOf(userTestBean.getTimer()));
			table.addCell(String.valueOf(userTestBean.getCountOfQuestions()));
			table.addCell(userTestBean.getResult());
		}
		return table;
	}
}
