package ua.nure.bogdanov.SummaryTask4.exception;

import org.junit.Test;
public class AppExceptionTest {

	@Test(expected = AppException.class)
	public final void testConstructor1() throws AppException {
		throw new AppException();
	}
	@Test(expected = AppException.class)
	public final void testConstructor2() throws AppException {
		throw new AppException("ERROR");
	}
	@Test(expected = AppException.class)
	public final void testConstructor3() throws AppException {
		throw new AppException("ERROR", new Throwable());
	}

}
