package ua.nure.bogdanov.SummaryTask4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ua.nure.bogdanov.SummaryTask4.db.ComplexityTest;
import ua.nure.bogdanov.SummaryTask4.db.FieldsTest;
import ua.nure.bogdanov.SummaryTask4.db.RoleTest;
import ua.nure.bogdanov.SummaryTask4.db.bean.TestBeanTest;
import ua.nure.bogdanov.SummaryTask4.db.bean.UserTestBeanTest;
import ua.nure.bogdanov.SummaryTask4.db.entity.AnswerTest;
import ua.nure.bogdanov.SummaryTask4.db.entity.QuestionTest;
import ua.nure.bogdanov.SummaryTask4.db.entity.SubjectTest;
import ua.nure.bogdanov.SummaryTask4.db.entity.TestTest;
import ua.nure.bogdanov.SummaryTask4.db.entity.UserTest;
import ua.nure.bogdanov.SummaryTask4.exception.AppExceptionTest;
import ua.nure.bogdanov.SummaryTask4.exception.DBExceptionTest;
import ua.nure.bogdanov.SummaryTask4.exception.InvalidDataExceptionTest;
import ua.nure.bogdanov.SummaryTask4.exception.MessagesTest;
import ua.nure.bogdanov.SummaryTask4.parser.DOMControllerTest;
import ua.nure.bogdanov.SummaryTask4.parser.ValidatorDOMParserTest;
import ua.nure.bogdanov.SummaryTask4.parser.constants.ConstantsTest;
import ua.nure.bogdanov.SummaryTask4.parser.constants.XMLTest;
import ua.nure.bogdanov.SummaryTask4.parser.entity.ConstraintTest;
import ua.nure.bogdanov.SummaryTask4.parser.entity.FieldTest;
import ua.nure.bogdanov.SummaryTask4.timer.TimerThreadTest;
import ua.nure.bogdanov.SummaryTask4.validator.ValidatorTest;

@RunWith(Suite.class)
@SuiteClasses({ TestBeanTest.class, UserTestBeanTest.class, AnswerTest.class,
		QuestionTest.class, SubjectTest.class, TestTest.class, UserTest.class,
		FieldsTest.class, RoleTest.class, ComplexityTest.class,
		AppExceptionTest.class, DBExceptionTest.class,
		InvalidDataExceptionTest.class, MessagesTest.class, PathTest.class,
		ConstantsTest.class, XMLTest.class, ConstraintTest.class,
		FieldTest.class, ValidatorDOMParserTest.class, DOMControllerTest.class,
		TimerThreadTest.class, ValidatorTest.class })
public class AllTests {
}
