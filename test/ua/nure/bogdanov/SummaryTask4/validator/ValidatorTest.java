package ua.nure.bogdanov.SummaryTask4.validator;

import junit.framework.Assert;

import org.junit.Test;

import ua.nure.bogdanov.SummaryTask4.exception.InvalidDataException;

public class ValidatorTest {
	private final String path = "test2.xml";
	private final String path2 = "test2-invalid.xml";

	@Test
	public final void testConstructor1() throws InvalidDataException {
		new Validator(path);
	}

	@Test(expected = InvalidDataException.class)
	public final void testConstructor2() throws InvalidDataException {
		new Validator(path2);
	}

	@Test
	public final void testIsValid1() throws InvalidDataException {
		Validator validator = new Validator(path);
		Assert.assertTrue(validator.isValid("login", "Login"));
	}

	@Test
	public final void testIsValid2() throws InvalidDataException {
		Validator validator = new Validator(path);
		Assert.assertTrue(validator.isValid("login", "TEST"));
	}

	@Test(expected = InvalidDataException.class)
	public final void testIsValid3() throws InvalidDataException {
		Validator validator = new Validator(path);
		validator.isValid("", "Login");
	}

	@Test(expected = InvalidDataException.class)
	public final void testIsValid4() throws InvalidDataException {
		Validator validator = new Validator(path);
		validator.isValid("loginloginloginloginloginlogin", "Login");
	}

	@Test(expected = InvalidDataException.class)
	public final void testIsValid5() throws InvalidDataException {
		Validator validator = new Validator(path);
		validator.isValid("hi", "Login");
	}

	@Test(expected = InvalidDataException.class)
	public final void testIsValid6() throws InvalidDataException {
		Validator validator = new Validator(path);
		validator.isValid("login", "Email");
	}

	@Test
	public final void isValidWithOutEx1() throws InvalidDataException {
		Validator validator = new Validator(path);
		Assert.assertTrue(validator.isValidWithOutEx("login", "Login"));
	}

	@Test
	public final void isValidWithOutEx2() throws InvalidDataException {
		Validator validator = new Validator(path);
		Assert.assertTrue(validator.isValidWithOutEx("login", "TEST"));
	}

	@Test
	public final void isValidWithOutEx3() throws InvalidDataException {
		Validator validator = new Validator(path);
		Assert.assertFalse(validator.isValidWithOutEx("", "Login"));
	}

	@Test
	public final void isValidWithOutEx4() throws InvalidDataException {
		Validator validator = new Validator(path);
		Assert.assertFalse(validator.isValidWithOutEx(
				"loginloginloginloginloginlogin", "Login"));
	}

	@Test
	public final void isValidWithOutEx5() throws InvalidDataException {
		Validator validator = new Validator(path);
		Assert.assertFalse(validator.isValidWithOutEx("hi", "Login"));
	}

	@Test
	public final void isValidWithOutEx6() throws InvalidDataException {
		Validator validator = new Validator(path);
		Assert.assertFalse(validator.isValidWithOutEx("login", "Email"));
	}

}
