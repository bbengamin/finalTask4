package ua.nure.bogdanov.SummaryTask4.exception;

/**
 * Holder for messages of exceptions.
 * 
 * @author I.Bogdanov
 * 
 */
public class Messages {

	public Messages() {
		// no op
	}

	// ERRORS
	public static final String ERR_THREAD = "Thread error";

	public static final String ERR_CANNOT_OBTAIN_USER_ORDER_BEANS = "Cannot obtain user order beans";

	public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";

	public static final String ERR_CANNOT_OBTAIN_CATEGORIES = "Cannot obtain categories";

	public static final String ERR_CANNOT_OBTAIN_MENU_ITEMS = "Cannot obtain menu items";

	public static final String ERR_CANNOT_OBTAIN_MENU_ITEMS_BY_ORDER = "Cannot obtain menu items by order";

	public static final String ERR_CANNOT_OBTAIN_MENU_ITEMS_BY_IDENTIFIERS = "Cannot obtain menu items by its identifiers";

	public static final String ERR_CANNOT_OBTAIN_ORDERS = "Cannot obtain orders";

	public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_STATUS_ID = "Cannot obtain orders by status id";

	public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_IDENTIFIERS = "Cannot obtain orders by its identifiers";

	public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_USER_AND_STATUS_ID = "Cannot obtain orders by user and status id";

	public static final String ERR_CANNOT_OBTAIN_USER_BY_ID = "Cannot obtain a user by its id";

	public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Cannot obtain a user by its login";

	public static final String ERR_CANNOT_OBTAIN_QUESTION_BY_ID = "Cannot obtain a question by its id";

	public static final String ERR_CANNOT_OBTAIN_QUESTION_BY_TEST_ID = "Cannot obtain a question by test_id";

	public static final String ERR_CANNOT_OBTAIN_TESTS_BY_TEST_ID = "Cannot obtain a tests by test_id";

	public static final String ERR_CANNOT_DELETE_TEST_BY_TEST_ID = "Cannot delete a test by id";
	
	public static final String ERR_CANNOT_DELETE_SUBJECT_BY_ID = "Cannot delete a subject by id";
	
	public static final String ERR_CANNOT_DELETE_USER_BY_TEST_ID = "Cannot delete a user by id";

	public static final String ERR_CANNOT_DELETE_QUESTION_BY_TEST_ID = "Cannot delete a question by id";

	public static final String ERR_CANNOT_DELETE_ANSWER_BY_TEST_ID = "Cannot delete a answer by id";

	public static final String ERR_CANNOT_OBTAIN_ANSWER_BY_QUESTION_ID = "Cannot obtain a answer by question_id";

	public static final String ERR_CANNOT_OBTAIN_CORRECT_ANSWERS = "Cannot obtain correct answers";

	public static final String ERR_CANNOT_OBTAIN_ANSWER_BY_ID = "Cannot obtain a answer by id";

	public static final String ERR_CANNOT_UPDATE_USER = "Cannot update a user";

	public static final String ERR_CANNOT_CHANGE_STATUS_USER = "Cannot change status of user";

	public static final String ERR_CANNOT_UPDATE_TEST = "Cannot update a test";
	
	public static final String ERR_CANNOT_UPDATE_SUBJECT = "Cannot update a subject";

	public static final String ERR_CANNOT_UPDATE_ANSWER = "Cannot update a answer";

	public static final String ERR_CANNOT_UPDATE_QUESTION = "Cannot update a question";

	public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection";

	public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close a result set";

	public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement";

	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";

	public static final String ERR_CANNOT_OBTAIN_SUBJECT_ITEMS = "Cannot obtain subject items";

	public static final String ERR_CANNOT_OBTAIN_TEST_ITEMS = "Cannot obtain test items";

	// MESSAGES
	public static final String MESSAGE_REGISTER = "Registration successful. Confirm entry!";
	public static final String MESSAGE_FIND_USER = "Cannot find user with such login/email";
	public static final String MESSAGE_FILE_ERROR = "Cant load file. Or file is not valid!";
	public static final String MESSAGE_ACCESS_ERROR = "You do not have permission to access the requested resource";
	public static final String MESSAGE_FOLDER_ERROR = "Can't create new folder";
}