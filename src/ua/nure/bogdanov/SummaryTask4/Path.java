package ua.nure.bogdanov.SummaryTask4;

/**
 * Path holder (jsp pages, controller commands).
 * 
 * @author I.Bogdanov
 * 
 */
public final class Path {

	// pages
	public static final String PAGE_LOGIN = "login.jsp";
	public static final String PAGE_NEW_PASSWORD = "newPassword.jsp";
	public static final String PAGE_RESTORE_PASSWORD = "restorePassword.jsp";
	public static final String PAGE_REGISTER = "register.jsp";
	public static final String PAGE_ERROR_PAGE = "error_page.jsp";
	public static final String PAGE_INDEX = "WEB-INF/jsp/index.jsp";
	public static final String PAGE_ADD_TEST = "WEB-INF/jsp/admin/updateTest.jsp";
	public static final String PAGE_UPDATE_QUESTION = "WEB-INF/jsp/admin/updateQuestion.jsp";
	public static final String PAGE_UPDATE_ANSWER = "WEB-INF/jsp/admin/updateAnswer.jsp";
	public static final String PAGE_TEST = "WEB-INF/jsp/client/test.jsp";
	public static final String PAGE_RESULTS = "WEB-INF/jsp/client/testResult.jsp";
	public static final String PAGE_USERS = "WEB-INF/jsp/admin/users.jsp";
	public static final String PAGE_SUBJECTS = "WEB-INF/jsp/admin/subjects.jsp";
	public static final String PAGE_PROFILE = "WEB-INF/jsp/profile.jsp";
	public static final String PAGE_STATS = "WEB-INF/jsp/admin/stats.jsp";
	public static final String PAGE_SETTINGS = "settings.jsp";
	public static final String PAGE_MAIL = "mail.jsp";

	// commands
	public static final String COMMAND_LIST_TESTS = "controller?command=listTests";
	public static final String COMMAND_LIST_SUBJECTS = "controller?command=listSubjects";
	public static final String COMMAND_PROFILE = "controller?command=profile";
	public static final String COMMAND_LIST_QUESTIONS = "controller?command=updateTestForm&id=";
	public static final String COMMAND_LIST_ANSWERS = "controller?command=updateQuestionForm&idTest=";
	public static final String COMMAND_LIST_USERS = "controller?command=listUsers";
	public static final String COMMAND_SHOW_RESULTS = "controller?command=showResults";

}