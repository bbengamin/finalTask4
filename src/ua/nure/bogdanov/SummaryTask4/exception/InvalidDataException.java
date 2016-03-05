package ua.nure.bogdanov.SummaryTask4.exception;

/**
 * An exception that provides information on a invalid data input.
 * 
 * @author I.Bogdanov
 * 
 */
public class InvalidDataException extends Exception {

	private static final long serialVersionUID = 2310625212372642991L;

	public InvalidDataException() {
		super("Invalid Data Supplied.");
	}

	public InvalidDataException(String message) {
		super(message);
	}
}
