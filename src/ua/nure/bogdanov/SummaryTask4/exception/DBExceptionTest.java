package ua.nure.bogdanov.SummaryTask4.exception;

import org.junit.Test;

public class DBExceptionTest {

	@Test(expected = DBException.class)
	public final void testConstructor1() throws DBException {
		throw new DBException();
	}

	@Test(expected = DBException.class)
	public final void testConstructor3() throws DBException {
		throw new DBException("ERROR", new Throwable());
	}

}
