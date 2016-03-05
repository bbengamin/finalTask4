package ua.nure.bogdanov.SummaryTask4.db;

/**
 * Holder for fields names of DB tables and beans.
 * 
 * @author I.Bogdanov
 * 
 */
public final class Fields {

	// entities
	public static final String ENTITY_ID = "id";
	//user
	public static final String USER_ROLE_ID = "role_id";
	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_EMAIL = "email";
	public static final String USER_FIRST_NAME = "firstName";
	public static final String USER_LAST_NAME = "lastName";
	public static final String USER_STATUS = "status";
	public static final String USER_BLOCK_MESSAGE = "block_message";
	public static final String USER_LOCALE = "locale";
	//test
	public static final String TEST_COMPLEXITY_ID = "complexity_id";
	public static final String TEST_SUBJECT_ID = "subject_id";
	public static final String TEST_NAME = "name";
	public static final String TEST_TIMER = "timer";
	//answer
	public static final String ANSWER_QUESTION_ID = "question_id";
	public static final String ANSWER_BODY = "body";
	public static final String ANSWER_CORRECT = "correct";
	//subject
	public static final String SUBJECT_NAME = "name";
	//question
	public static final String QUESTION_BODY = "body";
}