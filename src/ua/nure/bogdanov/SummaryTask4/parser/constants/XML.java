package ua.nure.bogdanov.SummaryTask4.parser.constants;

/**
 * Holds entities declared in XSD document.
 * 
 * @author I.Bogdanov
 * 
 */
public enum XML {
	// elements names
	TEST("Test"), QUESTION("Question"), QUESTION_TEXT("QuestionText"), ANSWER(
			"Answer"), CONSTRAINT("Constraint"), FIELD("Field"), NAME("name"), MAX_LENGTH(
			"maxLength"), MIN_LENGHT("minLength"), NOT_NULL("notNull"), MUST_CONTAINS(
			"mustContains"),

	// attribute name
	CORRECT("correct");

	private String value;

	XML(String value) {
		this.value = value;
	}

	public boolean equalsTo(String name) {
		return value.equals(name);
	}

	public String value() {
		return value;
	}
}
