package ua.nure.bogdanov.SummaryTask4.db;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.Assert;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import ua.nure.bogdanov.SummaryTask4.db.entity.Answer;
import ua.nure.bogdanov.SummaryTask4.db.entity.Question;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.DBException;

public class DBManagerTest {

	@Before
	public void setUp() {
		try {
			File file = new File("WebContent/WEB-INF/log4j.properties");
			PropertyConfigurator.configure(file.getAbsolutePath());
		} catch (Exception ex) {
			log("Cannot configure Log4j");
			ex.printStackTrace();
		}
	}

	@Test
	public final void testConstructor() throws DBException, SQLException {
		// manager = DBManager.getInstance();
		DBManager manager = DBManager.getInstance(true);
		Connection con = manager.getConnection();
		con.close();
	}

	@Test
	public final void testGetConnection() throws DBException, SQLException {
		DBManager manager = DBManager.getInstance(true);
		Connection con = manager.getConnection();
		con.close();
	}

	@Test
	public final void testAddFindUpdateDeleteUser() throws DBException {
		DBManager manager = DBManager.getInstance(true);

		User user = new User();
		boolean result = true;
		user.setEmail("mail");
		user.setFirstName("test");
		user.setLastName("TEST");
		user.setLocale("en");
		user.setLogin("login");
		user.setPassword("password");
		user.setRoleId(1);

		user = manager.createNewUser(user);
		if (manager.findUserByEmail("mail") == null) {
			result = false;
		}
		if (manager.findAllUsers() == null) {
			result = false;
		}
		if (manager.findUserById(user.getId()) == null) {
			result = false;
		}
		if (manager.findUserByLogin("login") == null) {
			result = false;
		}
		user.setFirstName("newTest");
		if (manager.updateUser(user.getId(), user) == null) {
			result = false;
		}
		user.setPassword("newPassword");
		manager.changeStatusOfUser(user.getLogin(), false, "block");
		manager.updateUserPassword(user.getId(), user.getPassword());
		manager.updateUserRole(user.getId(), user.getRoleId());
		manager.updateUserLocale(user.getId(), user.getLocale());
		result = manager.deleteUserById(user.getId());
		Assert.assertTrue(result);
	}

	@Test
	public final void testUserTestBean() throws DBException {
		DBManager manager = DBManager.getInstance(true);

		User user = new User();
		boolean result = true;
		user.setEmail("mail");
		user.setFirstName("test");
		user.setLastName("TEST");
		user.setLogin("login");
		user.setPassword("password");
		user.setRoleId(1);

		ua.nure.bogdanov.SummaryTask4.db.entity.Test test = new ua.nure.bogdanov.SummaryTask4.db.entity.Test();
		test.setComplexity(2);
		test.setName("TEST");
		test.setSubject(1);
		test.setTimer(55);

		user = manager.createNewUser(user);
		test = manager.createNewTest(test);
		manager.setUserResult(test.getId(), user.getId(), 100);

		if (manager.getUserResultsByTestId(test.getId(), user.getId()) == null) {
			result = false;
		}
		if (manager.findAllUserTestBean() == null) {
			result = false;
		}
		if (manager.findUserTestBeanByTestId(test.getId()) == null) {
			result = false;
		}
		if (manager.findUserTestBeanByUserId(user.getId()) == null) {
			result = false;
		}

		result = manager.deleteUserById(user.getId());
		result = manager.deleteTestById(test.getId());
		Assert.assertTrue(result);
	}

	@Test
	public final void testFindAllSubjectsTest() throws DBException {
		DBManager manager = DBManager.getInstance(true);
		boolean result = true;
		if (manager.findAllSubjects() == null) {
			result = false;
		}
		Assert.assertTrue(result);
	}

	@Test
	public final void testAddFindUpdateDeleteTest() throws DBException {
		DBManager manager = DBManager.getInstance(true);
		boolean result = true;
		ua.nure.bogdanov.SummaryTask4.db.entity.Test test = new ua.nure.bogdanov.SummaryTask4.db.entity.Test();
		test.setComplexity(2);
		test.setName("TEST");
		test.setSubject(1);
		test.setTimer(55);

		test = manager.createNewTest(test);
		if (manager.findTestById(test.getId()) == null) {
			result = false;
		}
		if (manager.findAllTests() == null) {
			result = false;
		}
		if (manager.findAllTestsBean() == null) {
			result = false;
		}
		test.setName("new");
		if ((test = manager.updateTest(test)) == null) {
			result = false;
		}
		result = manager.deleteTestById(test.getId());
		Assert.assertTrue(result);
	}

	@Test
	public final void testAddFindUpdateDeleteQuestion() throws DBException {
		DBManager manager = DBManager.getInstance(true);
		boolean result = true;
		Question question = new Question();
		question.setBody("BODY");

		question = manager.createNewQuestion(1, question);
		if (manager.findQuestionById(question.getId()) == null) {
			result = false;
		}
		if (manager.findQuestionsByTestId(1) == null) {
			result = false;
		}
		question.setBody("new");
		if ((question = manager.updateQuestion(question)) == null) {
			result = false;
		}

		result = manager.deleteQuestionById(question.getId());
		Assert.assertTrue(result);
	}

	@Test
	public final void testAddFindUpdateDeleteAnswer() throws DBException {
		DBManager manager = DBManager.getInstance(true);
		boolean result = true;
		Answer answer = new Answer();
		answer.setBody("BODY");
		answer.setCorrect(true);
		answer.setQuestion(1L);

		answer = manager.createNewAnswer(answer);
		if (manager.findAnswerById(answer.getId()) == null) {
			result = false;
		}
		if (manager.findAnswersByQuestionId(answer.getQuestion()) == null) {
			result = false;
		}
		if (manager.findCorrectAnswers(answer.getQuestion()) == null) {
			result = false;
		}
		answer.setBody("new");
		if ((answer = manager.updateAnswer(answer)) == null) {
			result = false;
		}
		result = manager.deleteAnswerById(answer.getId());
		Assert.assertTrue(result);
	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}

}
