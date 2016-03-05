package ua.nure.bogdanov.SummaryTask4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.db.bean.TestBean;
import ua.nure.bogdanov.SummaryTask4.db.bean.UserTestBean;
import ua.nure.bogdanov.SummaryTask4.db.entity.Answer;
import ua.nure.bogdanov.SummaryTask4.db.entity.Question;
import ua.nure.bogdanov.SummaryTask4.db.entity.Subject;
import ua.nure.bogdanov.SummaryTask4.db.entity.Test;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;
import ua.nure.bogdanov.SummaryTask4.exception.DBException;
import ua.nure.bogdanov.SummaryTask4.exception.Messages;
import org.apache.derby.jdbc.ClientDataSource;

/**
 * DB manager. Works with Apache Derby DB.
 * 
 * @author I.Bogdanov
 * 
 */
public final class DBManager {

	private static final Logger LOG = Logger.getLogger(DBManager.class);

	// //////////////////////////////////////////////////////////
	// singleton
	// //////////////////////////////////////////////////////////

	private static DBManager instance;

	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	public static synchronized DBManager getInstance(boolean notPool)
			throws DBException {
		if (instance == null) {
			instance = new DBManager(notPool);
		}
		return instance;
	}

	private DBManager() throws DBException {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			// testing - the name of data source
			ds = (DataSource) envContext.lookup("jdbc/testing");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
		}
	}

	private DBManager(boolean notPool) throws DBException {
		if (notPool) {

			ClientDataSource dataSource = new ClientDataSource();
			dataSource.setServerName("localhost");
			dataSource.setDatabaseName("testing");
			dataSource.setUser("root");
			dataSource.setPassword("root");
			ds = (DataSource) dataSource;
		}
	}

	private DataSource ds;

	// //////////////////////////////////////////////////////////
	// SQL queries
	// //////////////////////////////////////////////////////////

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

	private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

	private static final String SQL_FIND_TEST_BY_ID = "SELECT * FROM tests WHERE id=?";

	private static final String SQL_FIND_QUESTION_BY_ID = "SELECT * FROM questions WHERE id=?";

	private static final String SQL_FIND_ANSWERS_BY_QUESTION_ID = "SELECT * FROM answers WHERE question_id=?";

	private static final String SQL_FIND_ANSWER_BY_ID = "SELECT * FROM answers WHERE id=?";

	private static final String SQL_FIND_TEST_RESULTS_BY_ID = "SELECT COUNT (*), SUM (result) FROM users_tests WHERE test_id=?";

	private static final String SQL_FIND_CORRECT_ANSWERS = "SELECT * FROM answers WHERE correct=true AND question_id=?";

	private static final String SQL_FIND_QUESTIONS_BY_TEST_ID = "SELECT questions.id, questions.body "
			+ "													FROM tests_questions, questions "
			+ "													WHERE tests_questions.test_id=? AND tests_questions.question_id=questions.id";

	private static final String SQL_FIND_USER_TEST_BEAN_BY_USER_ID = "SELECT users.login, tests.name, subjects.name, tests.complexity_id, tests.timer, users_tests.result, tests.id"
			+ "														FROM tests, subjects, users, users_tests"
			+ "														WHERE subjects.id=tests.subject_id AND users.id=users_tests.user_id AND tests.id=users_tests.test_id AND users_tests.user_id=?";

	private static final String SQL_FIND_ALL_USER_TEST_BEAN = "SELECT users.login, tests.name, subjects.name, tests.complexity_id, tests.timer, users_tests.result, tests.id"
			+ "														FROM tests, subjects, users, users_tests"
			+ "														WHERE subjects.id=tests.subject_id AND users.id=users_tests.user_id AND tests.id=users_tests.test_id";

	private static final String SQL_FIND_USER_TEST_BEAN_BY_TEST_ID = "SELECT users.login, tests.name, subjects.name, tests.complexity_id, tests.timer, users_tests.result, tests.id"
			+ "														FROM tests, subjects, users, users_tests"
			+ "														WHERE subjects.id=tests.subject_id AND users.id=users_tests.user_id AND tests.id=users_tests.test_id AND users_tests.test_id=?";

	private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";

	private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";

	private static final String SQL_CREATE_NEW_USER = "INSERT INTO users (role_id , login , password, email, firstName, lastName) VALUES (?, ?, ?, ?, ?, ?)";

	private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE users SET password=? WHERE id=?";

	private static final String SQL_UPDATE_USER = "UPDATE users SET login=?, password=?, email=?, firstName=?, lastName=? WHERE id=?";

	private static final String SQL_UPDATE_USER_LOCALE = "UPDATE users SET locale=? WHERE id=?";

	private static final String SQL_UPDATE_USER_ROLE = "UPDATE users SET role_id=? WHERE id=?";

	private static final String SQL_CHENGE_STATS_USER = "UPDATE users SET status=?, block_message=? WHERE login=?";

	private static final String SQL_CREATE_NEW_TEST = "INSERT INTO tests (subject_id , complexity_id , name, timer) VALUES (?, ?, ?, ?)";

	private static final String SQL_CREATE_NEW_SUBJECT = "INSERT INTO subjects (name) VALUES (?)";

	private static final String SQL_SET_USER_RESULT = "INSERT INTO users_tests (user_id , test_id , result) VALUES (?, ?, ?)";

	private static final String SQL_GET_USER_RESULT = "SELECT users_tests.result FROM users_tests WHERE test_id=? AND user_id=?";

	private static final String SQL_CREATE_NEW_ANSWER = "INSERT INTO answers (question_id, body, correct) VALUES (?, ?, ?)";

	private static final String SQL_CREATE_NEW_QUESTION = "INSERT INTO questions (body) VALUES (?)";

	private static final String SQL_CREATE_NEW_TESTS_QUESTIONS = "INSERT INTO tests_questions (test_id, question_id) VALUES (?, ?)";

	private static final String SQL_UPDATE_TEST = "UPDATE tests SET subject_id=?, complexity_id=?, name=?, timer=? WHERE id=?";

	private static final String SQL_UPDATE_SUBJECT = "UPDATE subjects SET name=? WHERE id=?";

	private static final String SQL_UPDATE_ANSWER = "UPDATE answers SET question_id=?, body=?, correct=? WHERE id=?";

	private static final String SQL_UPDATE_QUESTION = "UPDATE questions SET body=? WHERE id=?";

	private static final String SQL_FIND_ALL_TEST_ITEMS = "SELECT * FROM tests";

	private static final String SQL_GET_ALL_SUBJECTS = "SELECT * FROM subjects";

	private static final String SQL_FIND_ALL_TESTBEAN_ITEMS = "SELECT tests.name, subjects.name, tests.complexity_id, tests.timer, tests.id FROM subjects, tests WHERE subjects.id=tests.subject_id";

	private static final String SQL_DELETE_TEST_BY_ID = "DELETE FROM tests WHERE tests.id=?";

	private static final String SQL_DELETE_SUBJECT_BY_ID = "DELETE FROM subjects WHERE id=?";

	private static final String SQL_DELETE_QUESTION_BY_ID = "DELETE FROM questions WHERE questions.id=?";

	private static final String SQL_DELETE_ANSWER_BY_ID = "DELETE FROM answers WHERE answers.id=?";

	private static final String SQL_DELETE_USER_BY_ID = "DELETE FROM users WHERE users.id=?";

	/**
	 * Returns a DB connection from the Pool Connections. Before using this
	 * method you must configure the Date Source and the Connections Pool in
	 * your WEB_APP_ROOT/META-INF/context.xml file.
	 * 
	 * @return DB connection.
	 */
	public Connection getConnection() throws DBException {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException ex) {
			LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
		return con;
	}

	/**
	 * Ñreates an entry for the new user in the database. Returns user with
	 * generated userID.
	 * 
	 * @param user
	 *            new User.
	 * @return User entity.
	 * @throws DBException
	 */
	public User createNewUser(User u) throws DBException {
		User user = u;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_NEW_USER,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, user.getRoleId());
			pstmt.setString(2, user.getLogin());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getFirstName());
			pstmt.setString(6, user.getLastName());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				user.setId(rs.getLong(1));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns a user with the given login.
	 * 
	 * @param login
	 *            User login.
	 * @return User entity.
	 * @throws DBException
	 */
	public void changeStatusOfUser(String login, boolean status,
			String blockMessage) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CHENGE_STATS_USER);
			pstmt.setBoolean(1, status);
			pstmt.setString(2, blockMessage);
			pstmt.setString(3, login);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_CHANGE_STATUS_USER, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Updates the record of user with given id.
	 * 
	 * @param id
	 *            User id.
	 * @param u
	 *            User item.
	 * @return User entity.
	 * @throws DBException
	 */
	public User updateUser(long id, User u) throws DBException {
		User user = u;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_USER);
			pstmt.setString(1, user.getLogin());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getFirstName());
			pstmt.setString(5, user.getLastName());
			pstmt.setLong(6, id);

			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Updates the record of user locale.
	 * 
	 * @param id
	 *            User id.
	 * @param locale
	 *            new locale.
	 * @throws DBException
	 */
	public void updateUserLocale(long id, String locale) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_USER_LOCALE);
			pstmt.setString(1, locale);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Update user role.
	 * 
	 * @param id
	 *            User id.
	 * @param idRole
	 *            new role.
	 * @throws DBException
	 */
	public void updateUserRole(long id, long idRole) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_USER_ROLE);
			pstmt.setLong(1, idRole);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Updates the record of user password.
	 * 
	 * @param id
	 *            User id.
	 * @param password
	 *            new password.
	 * @throws DBException
	 */
	public void updateUserPassword(long id, String password) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_USER_PASSWORD);
			pstmt.setString(1, password);
			pstmt.setLong(2, id);

			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_USER, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Ñreates an entry for the new test in the database. Returns test with
	 * generated userID.
	 * 
	 * @param t
	 *            new test.
	 * @return Test entity.
	 * @throws DBException
	 */
	public Test createNewTest(Test t) throws DBException {
		Test test = t;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_NEW_TEST,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, test.getSubject());
			pstmt.setInt(2, test.getComplexity());
			pstmt.setString(3, test.getName());
			pstmt.setInt(4, test.getTimer());

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				test.setId(rs.getLong(1));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_TEST, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return test;
	}

	/**
	 * Ñreates an entry for the new subject in the database. Returns test with
	 * generated userID.
	 * 
	 * @param s
	 *            new subject.
	 * @return Subject entity.
	 * @throws DBException
	 */
	public Subject createNewSubject(Subject s) throws DBException {
		Subject subject = s;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_NEW_SUBJECT,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, subject.getName());

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				subject.setId(rs.getLong(1));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_SUBJECT, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return subject;
	}

	/**
	 * Pushes the result of user.
	 * 
	 * @param idTest
	 *            Test id.
	 * @param idUser
	 *            User id.
	 * @param result
	 *            User result.
	 * @throws DBException
	 */
	public void setUserResult(long idTest, long idUser, double result)
			throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_SET_USER_RESULT);
			pstmt.setLong(1, idUser);
			pstmt.setLong(2, idTest);
			pstmt.setDouble(3, result);

			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_TEST, ex);
		} finally {
			close(con, pstmt, rs);
		}
	}

	/**
	 * Returns a user results with the given id test and id user.
	 * 
	 * @param idTest
	 *            Test id.
	 * @param idUser
	 *            User id.
	 * @return Double[] results.
	 * @throws DBException
	 */
	public Double[] getUserResultsByTestId(long idTest, long idUser)
			throws DBException {
		List<Double> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_GET_USER_RESULT);
			pstmt.setLong(1, idTest);
			pstmt.setLong(2, idUser);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getDouble(1));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_TEST, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return list.toArray(new Double[0]);
	}

	/**
	 * Ñreates an entry for the new answer in the database. Returns anser with
	 * generated userID.
	 * 
	 * @param a
	 *            new Answer.
	 * @return Answer entity.
	 * @throws DBException
	 */
	public Answer createNewAnswer(Answer a) throws DBException {
		Answer answer = a;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_NEW_ANSWER,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setLong(1, answer.getQuestion());
			pstmt.setString(2, answer.getBody());
			pstmt.setBoolean(3, answer.getCorrect());

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				answer.setId(rs.getLong(1));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ANSWER, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return answer;
	}

	/**
	 * Ñreates an entry for the new question in the database. Returns question
	 * with generated userID.
	 * 
	 * @param idTest
	 *            Id test to which it belongs question.
	 * @param q
	 *            new question.
	 * @return Question entity.
	 * @throws DBException
	 */
	public Question createNewQuestion(int idTest, Question q)
			throws DBException {
		Question question = q;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_CREATE_NEW_QUESTION,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, question.getBody());

			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				question.setId(rs.getLong(1));
			}
			pstmt1 = con.prepareStatement(SQL_CREATE_NEW_TESTS_QUESTIONS);
			pstmt1.setInt(1, idTest);
			pstmt1.setLong(2, question.getId());
			pstmt1.executeUpdate();

			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_QUESTION, ex);
		} finally {
			close(pstmt1);
			close(con, pstmt, rs);
		}
		return question;
	}

	/**
	 * Updates the record of test.
	 * 
	 * @param t
	 *            Test item.
	 * @return Test entity.
	 * @throws DBException
	 */
	public Test updateTest(Test t) throws DBException {
		Test test = t;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_TEST);
			pstmt.setInt(1, test.getSubject());
			pstmt.setInt(2, test.getComplexity());
			pstmt.setString(3, test.getName());
			pstmt.setInt(4, test.getTimer());
			pstmt.setLong(5, test.getId());

			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_TEST, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return test;
	}

	/**
	 * Updates the record of subject.
	 * 
	 * @param t
	 *            Subject item.
	 * @return Subject entity.
	 * @throws DBException
	 */
	public Subject updateSubject(Subject t) throws DBException {
		Subject subject = t;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_SUBJECT);
			pstmt.setString(1, subject.getName());
			pstmt.setLong(2, subject.getId());

			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_SUBJECT, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return subject;
	}

	/**
	 * Updates the record of answer.
	 * 
	 * @param a
	 *            Answer item.
	 * @return Answer entity.
	 * @throws DBException
	 */
	public Answer updateAnswer(Answer a) throws DBException {
		Answer answer = a;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_ANSWER);
			pstmt.setLong(1, answer.getQuestion());
			pstmt.setString(2, answer.getBody());
			pstmt.setBoolean(3, answer.getCorrect());
			pstmt.setLong(4, answer.getId());

			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_ANSWER, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return answer;
	}

	/**
	 * Updates the record of question.
	 * 
	 * @param q
	 *            Question item.
	 * @return Question entity.
	 * @throws DBException
	 */
	public Question updateQuestion(Question q) throws DBException {
		Question question = q;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_UPDATE_QUESTION);
			pstmt.setString(1, question.getBody());
			pstmt.setLong(2, question.getId());

			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_UPDATE_QUESTION, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return question;
	}

	/**
	 * Returns a test with the given id.
	 * 
	 * @param id
	 *            test id.
	 * @return Test entity.
	 * @throws DBException
	 */
	public Test findTestById(long id) throws DBException {
		Test test = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_TEST_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				test = extractTestItem(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return test;
	}

	/**
	 * Returns a question with the given id.
	 * 
	 * @param id
	 *            Question id.
	 * @return Question entity.
	 * @throws DBException
	 */
	public Question findQuestionById(long id) throws DBException {
		Question question = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_QUESTION_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				question = extractQuestionItem(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return question;
	}

	/**
	 * Returns a questions with the given test id.
	 * 
	 * @param id
	 *            test id.
	 * @return List of Question item entities.
	 * @throws DBException
	 */
	public List<Question> findQuestionsByTestId(long id) throws DBException {
		List<Question> questions = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_QUESTIONS_BY_TEST_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				questions.add(extractQuestionItem(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(
					Messages.ERR_CANNOT_OBTAIN_QUESTION_BY_TEST_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return questions;
	}

	/**
	 * Returns a answer with the given question id.
	 * 
	 * @param id
	 *            question id.
	 * @return List of Answer item entities.
	 * @throws DBException
	 */
	public List<Answer> findAnswersByQuestionId(long id) throws DBException {
		List<Answer> answers = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_ANSWERS_BY_QUESTION_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				answers.add(extractAnswerItem(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(
					Messages.ERR_CANNOT_OBTAIN_ANSWER_BY_QUESTION_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return answers;
	}

	/**
	 * Returns a correct answers with the given question id.
	 * 
	 * @param idQuestion
	 *            question id.
	 * @return List of Answer item entities.
	 * @throws DBException
	 */
	public List<Answer> findCorrectAnswers(long idQuestion) throws DBException {
		List<Answer> answers = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_CORRECT_ANSWERS);
			pstmt.setLong(1, idQuestion);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				answers.add(extractAnswerItem(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_CORRECT_ANSWERS,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return answers;
	}

	/**
	 * Returns a answer with the given id.
	 * 
	 * @param id
	 *            answer id.
	 * @return Answer entity.
	 * @throws DBException
	 */
	public Answer findAnswerById(long id) throws DBException {
		Answer answer = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_ANSWER_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				answer = extractAnswerItem(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ANSWER_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return answer;
	}

	/**
	 * 
	 * @param id
	 *            test id.
	 * @return Double result.
	 * @throws DBException
	 */
	public Double getTestResult(long id) throws DBException {
		double res = 0;
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_TEST_RESULTS_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
				res = rs.getDouble(2);
				if (count > 0) {
					res = res / count;
				} else {
					res = 100;
				}

			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_ANSWER_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return res / count;
	}

	/**
	 * Returns a UserTestBean with the given user id.
	 * 
	 * @param id
	 *            user id.
	 * @return List of UserTestBean item entities.
	 * @throws DBException
	 */
	public List<UserTestBean> findUserTestBeanByUserId(long id)
			throws DBException {
		List<UserTestBean> tests = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_TEST_BEAN_BY_USER_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tests.add(extractUserTestBeanItem(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_TESTS_BY_TEST_ID,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return tests;
	}

	/**
	 * Returns a UserTestBean with the given test id.
	 * 
	 * @param id
	 *            test id.
	 * @return List of UserTestBean item entities.
	 * @throws DBException
	 */
	public List<UserTestBean> findUserTestBeanByTestId(long id)
			throws DBException {
		List<UserTestBean> tests = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_TEST_BEAN_BY_TEST_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tests.add(extractUserTestBeanItem(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_TESTS_BY_TEST_ID,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return tests;
	}

	/**
	 * Returns all UserTestBean items.
	 * 
	 * @return List of UserTestBean item entities.
	 */
	public List<UserTestBean> findAllUserTestBean() throws DBException {
		List<UserTestBean> tests = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USER_TEST_BEAN);
			while (rs.next()) {
				tests.add(extractUserTestBeanItem(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_TESTS_BY_TEST_ID,
					ex);
		} finally {
			close(con, stmt, rs);
		}
		return tests;
	}

	/**
	 * Delete an entry for the test in the database.
	 * 
	 * @param id
	 *            Id test to be removed.
	 * @return Returns true if success.
	 * @throws DBException
	 */
	public boolean deleteTestById(long id) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_TEST_BY_ID);
			pstmt.setLong(1, id);
			int count = pstmt.executeUpdate();

			if (count < 1) {
				return false;
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_DELETE_TEST_BY_TEST_ID,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Delete an entry for the subject in the database.
	 * 
	 * @param id
	 *            Id subject to be removed.
	 * @return Returns true if success.
	 * @throws DBException
	 */
	public boolean deleteSubjectById(long id) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_SUBJECT_BY_ID);
			pstmt.setLong(1, id);
			int count = pstmt.executeUpdate();

			if (count < 1) {
				return false;
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_DELETE_SUBJECT_BY_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Delete an entry for the user in the database.
	 * 
	 * @param id
	 *            Id user to be removed.
	 * @return Returns true if success.
	 * @throws DBException
	 */
	public boolean deleteUserById(long id) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_USER_BY_ID);
			pstmt.setLong(1, id);
			int count = pstmt.executeUpdate();

			if (count < 1) {
				return false;
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_DELETE_USER_BY_TEST_ID,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Delete an entry for the question in the database.
	 * 
	 * @param id
	 *            Id question to be removed.
	 * @return Returns true if success.
	 * @throws DBException
	 */
	public boolean deleteQuestionById(long id) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_QUESTION_BY_ID);
			pstmt.setLong(1, id);
			int count = pstmt.executeUpdate();

			if (count < 1) {
				return false;
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(
					Messages.ERR_CANNOT_DELETE_QUESTION_BY_TEST_ID, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Delete an entry for the answer in the database.
	 * 
	 * @param id
	 *            Id answer to be removed.
	 * @return Returns true if success.
	 * @throws DBException
	 */
	public boolean deleteAnswerById(long id) throws DBException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_DELETE_ANSWER_BY_ID);
			pstmt.setLong(1, id);
			int count = pstmt.executeUpdate();

			if (count < 1) {
				return false;
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_DELETE_ANSWER_BY_TEST_ID,
					ex);
		} finally {
			close(con, pstmt, rs);
		}
		return true;
	}

	/**
	 * Returns a user with the given id.
	 * 
	 * @param id
	 *            User id.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUserById(long id) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns a user with the given login.
	 * 
	 * @param login
	 *            User login.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUserByLogin(String login) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns all user items.
	 * 
	 * @return List of user item entities.
	 */
	public List<User> findAllUsers() throws DBException {
		List<User> users = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USERS);
			while (rs.next()) {
				users.add(extractUser(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, stmt, rs);
		}
		return users;
	}

	/**
	 * Returns a user with the given email.
	 * 
	 * @param email
	 *            User email.
	 * @return User entity.
	 * @throws DBException
	 */
	public User findUserByEmail(String email) throws DBException {
		User user = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(SQL_FIND_USER_BY_EMAIL);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = extractUser(rs);
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
		} finally {
			close(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * Returns all test items.
	 * 
	 * @return List of test item entities.
	 */
	public List<Test> findAllTests() throws DBException {
		List<Test> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_TEST_ITEMS);
			while (rs.next()) {
				list.add(extractTestItem(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_TEST_ITEMS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_TEST_ITEMS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return list;
	}

	/**
	 * Returns all TestsBean items.
	 * 
	 * @return List of TestsBean item entities.
	 */
	public List<TestBean> findAllTestsBean() throws DBException {
		List<TestBean> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_TESTBEAN_ITEMS);
			while (rs.next()) {
				list.add(extractTestBeanItem(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_TEST_ITEMS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_TEST_ITEMS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return list;
	}

	/**
	 * Returns all subjects items.
	 * 
	 * @return List of subjects item entities.
	 */
	public List<Subject> findAllSubjects() throws DBException {
		List<Subject> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL_GET_ALL_SUBJECTS);
			while (rs.next()) {
				list.add(extractSubjectItem(rs));
			}
			con.commit();
		} catch (SQLException ex) {
			rollback(con);
			LOG.error(Messages.ERR_CANNOT_OBTAIN_SUBJECT_ITEMS, ex);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_SUBJECT_ITEMS, ex);
		} finally {
			close(con, stmt, rs);
		}
		return list;
	}

	/**
	 * Extracts a user entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user entity will be extracted.
	 * @return User entity
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong(Fields.ENTITY_ID));
		user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
		user.setLogin(rs.getString(Fields.USER_LOGIN));
		user.setPassword(rs.getString(Fields.USER_PASSWORD));
		user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
		user.setLastName(rs.getString(Fields.USER_LAST_NAME));
		user.setEmail(rs.getString(Fields.USER_EMAIL));
		user.setStatus(rs.getBoolean(Fields.USER_STATUS));
		user.setBlockMessage(rs.getString(Fields.USER_BLOCK_MESSAGE));
		user.setLocale(rs.getString(Fields.USER_LOCALE));
		return user;
	}

	/**
	 * Extracts a user entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a user entity will be extracted.
	 * @return User entity
	 */
	private Question extractQuestionItem(ResultSet rs) throws SQLException {
		Question question = new Question();
		question.setId(rs.getLong(Fields.ENTITY_ID));
		question.setBody(rs.getString(Fields.QUESTION_BODY));
		return question;
	}

	/**
	 * Extracts a answer entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a answer entity will be extracted.
	 * @return Answer entity
	 */
	private Answer extractAnswerItem(ResultSet rs) throws SQLException {
		Answer answer = new Answer();
		answer.setId(rs.getLong(Fields.ENTITY_ID));
		answer.setQuestion(rs.getLong(Fields.ANSWER_QUESTION_ID));
		answer.setBody(rs.getString(Fields.ANSWER_BODY));
		answer.setCorrect(rs.getBoolean(Fields.ANSWER_CORRECT));
		return answer;
	}

	/**
	 * Extracts a UserTestBean entity from the result set.
	 * 
	 * @param rs
	 *            Result set from which a UserTestBean entity will be extracted.
	 * @return UserTestBean entity
	 * @throws DBException
	 */
	private UserTestBean extractUserTestBeanItem(ResultSet rs)
			throws SQLException, DBException {
		UserTestBean test = new UserTestBean();
		test.setLogin(rs.getString(1));
		test.setName(rs.getString(2));
		test.setSubject(rs.getString(3));
		test.setComplexity(Complexity.values()[rs.getInt(4)].getName());
		test.setTimer(rs.getInt(5));
		test.setResult(String.format("%.2f", rs.getDouble(6)));
		List<Question> questions = findQuestionsByTestId(rs.getLong(7));
		test.setCountOfQuestions(questions.size());
		return test;
	}

	/**
	 * Extracts a test item from the result set.
	 * 
	 * @param rs
	 *            Result set from which a test item entity will be extracted.
	 * @return Test item entity.
	 */
	private Test extractTestItem(ResultSet rs) throws SQLException {
		Test test = new Test();
		test.setId(rs.getLong(Fields.ENTITY_ID));
		test.setName(rs.getString(Fields.TEST_NAME));
		test.setSubject(rs.getInt(Fields.TEST_SUBJECT_ID));
		test.setComplexity(rs.getInt(Fields.TEST_COMPLEXITY_ID));
		test.setTimer(rs.getInt(Fields.TEST_TIMER));
		return test;
	}

	/**
	 * Extracts a TestBean bean item from the result set.
	 * 
	 * @param rs
	 *            Result set from which a TestBean item entity will be
	 *            extracted.
	 * @return TestBean item entity.
	 * @throws DBException
	 */
	private TestBean extractTestBeanItem(ResultSet rs) throws SQLException,
			DBException {
		TestBean test = new TestBean();
		test.setName(rs.getString(1));
		test.setSubject(rs.getString(2));
		test.setComplexity(Complexity.values()[rs.getInt(3)].getName());
		test.setTimer(rs.getInt(4));
		test.setId(rs.getLong(5));
		List<Question> questions = findQuestionsByTestId(test.getId());
		test.setCountOfQuestions(questions.size());
		return test;
	}

	/**
	 * Extracts a Subject item from the result set.
	 * 
	 * @param rs
	 *            Result set from which a Subject item entity will be extracted.
	 * @return Subject item entity.
	 */
	private Subject extractSubjectItem(ResultSet rs) throws SQLException {
		Subject sub = new Subject();
		sub.setId(rs.getLong(Fields.ENTITY_ID));
		sub.setName(rs.getString(Fields.SUBJECT_NAME));
		return sub;
	}

	// //////////////////////////////////////////////////////////
	// DB util methods
	// //////////////////////////////////////////////////////////

	/**
	 * Closes a connection.
	 * 
	 * @param con
	 *            Connection to be closed.
	 */
	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
			}
		}
	}

	/**
	 * Closes a statement object.
	 */
	private void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * Closes a result set object.
	 */
	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Closes resources.
	 */
	private void close(Connection con, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(con);
	}

	/**
	 * Rollbacks a connection.
	 * 
	 * @param con
	 *            Connection to be rollbacked.
	 */
	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}

}