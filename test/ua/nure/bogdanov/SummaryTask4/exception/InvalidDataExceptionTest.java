
package ua.nure.bogdanov.SummaryTask4.exception;

import org.junit.Test;

public class InvalidDataExceptionTest {

	@Test(expected = InvalidDataException.class)
	public final void testConstructor1() throws InvalidDataException {
		throw new InvalidDataException();
	}

	@Test(expected = InvalidDataException.class)
	public final void testConstructor3() throws InvalidDataException {
		throw new InvalidDataException("ERROR");
	}

}
