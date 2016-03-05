package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import ua.nure.bogdanov.SummaryTask4.Path;
import ua.nure.bogdanov.SummaryTask4.db.DBManager;
import ua.nure.bogdanov.SummaryTask4.db.entity.Answer;
import ua.nure.bogdanov.SummaryTask4.db.entity.Question;
import ua.nure.bogdanov.SummaryTask4.exception.DBException;
import ua.nure.bogdanov.SummaryTask4.exception.Messages;
import ua.nure.bogdanov.SummaryTask4.parser.DOMController;

/**
 * Servlet to uploading files
 * 
 * @author I.Bogdanov
 * 
 */
public class UploadFile extends HttpServlet {

	private static final long serialVersionUID = 2423353715955164816L;

	private static final Logger LOG = Logger.getLogger(UploadFile.class);

	private static final String UPLOAD_DIRECTORY = "WEB-INF" + File.separator
			+ "upload";
	public static final String ERROR_ATTRIBUTE = "errorMessage";
	public static final String LOG_ERROR_MES = "Break with error --> ";
	// 3MB
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3;
	// 40MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
	// 50MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * Main method of this servlet.
	 * 
	 * @throws IOException
	 * @throws ServletException
	 * 
	 * @throws DBException
	 */
	private void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("Command starts");

		String forward = Path.PAGE_ERROR_PAGE;
		DBManager manager = null;
		String idTest = null;
		try {
			manager = DBManager.getInstance();
		} catch (DBException e) {
			LOG.trace(LOG_ERROR_MES + e.getMessage());
			request.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
			forward = Path.PAGE_ERROR_PAGE;
			request.getRequestDispatcher(forward).forward(request, response);
		}

		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			LOG.trace("Break with error --> Request does not contain upload data");
			request.setAttribute(ERROR_ATTRIBUTE,
					"Request does not contain upload data");
			forward = Path.PAGE_ERROR_PAGE;
			request.getRequestDispatcher(forward).forward(request, response);
		}

		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// constructs the directory path to store upload file
		String uploadPath = getServletContext().getRealPath("")
				+ File.separator + UPLOAD_DIRECTORY;
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists() && !uploadDir.mkdir()) {
			LOG.trace(LOG_ERROR_MES + Messages.MESSAGE_FOLDER_ERROR);
			request.setAttribute(ERROR_ATTRIBUTE, Messages.MESSAGE_FOLDER_ERROR);
			forward = Path.PAGE_ERROR_PAGE;
			request.getRequestDispatcher(forward).forward(request, response);
			return;
		}

		File storeFile = null;
		try {
			// parses the request's content to extract file data
			List<FileItem> formItems = upload.parseRequest(request);
			Iterator<FileItem> iter = formItems.iterator();
			// iterates over form's fields
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (item.getFieldName().equals("idTest")) {
					InputStream input = item.getInputStream();
					byte[] str = new byte[input.available()];
					input.read(str);
					idTest = new String(str, "UTF8");
				}
				// processes only fields that are not form fields
				if (!item.isFormField()) {
					String sourseFileName = new File(item.getName()).getName();
					int i = 0;
					String fileName = sourseFileName + ".xml";
					String filePath = uploadPath + File.separator + fileName;
					storeFile = new File(filePath);
					while (storeFile.exists()) {
						i++;
						fileName = sourseFileName + Integer.toString(i)
								+ ".xml";
						filePath = uploadPath + File.separator + fileName;
						storeFile = new File(filePath);
					}

					// saves the file on disk
					System.out.println();
					try {
						item.write(storeFile);
					} catch (Exception e) {
						LOG.trace(LOG_ERROR_MES + Messages.MESSAGE_FILE_ERROR);
						request.setAttribute(ERROR_ATTRIBUTE,
								Messages.MESSAGE_FILE_ERROR);
						forward = Path.PAGE_ERROR_PAGE;
						request.getRequestDispatcher(forward).forward(request,
								response);
						return;
					}

					// parse file
					DOMController parser = new DOMController(filePath);
					parser.parse(true);
					List<Question> questions = parser.getQuestions();
					Map<Question, List<Answer>> allAnswers = parser
							.getAnswers();
					// write item into db
					List<Answer> answers = null;
					for (Question question : questions) {
						answers = allAnswers.get(question);
						question = manager.createNewQuestion(
								Integer.parseInt(idTest), question);
						for (Answer answer : answers) {
							answer.setQuestion(question.getId());
							manager.createNewAnswer(answer);
						}
					}
				}
			}
		} catch (DBException | FileUploadException
				| ParserConfigurationException | SAXException ex) {
			LOG.trace(LOG_ERROR_MES + Messages.MESSAGE_FILE_ERROR);
			request.setAttribute(ERROR_ATTRIBUTE, Messages.MESSAGE_FILE_ERROR);
			forward = Path.PAGE_ERROR_PAGE;
			request.getRequestDispatcher(forward).forward(request, response);
			return;
		} finally {
			if (storeFile.exists()) {
				storeFile.delete();
			}
		}

		forward = Path.COMMAND_LIST_QUESTIONS + idTest;
		response.sendRedirect(forward);
		LOG.debug("Command finished");
	}
}