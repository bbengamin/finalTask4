package ua.nure.bogdanov.SummaryTask4.db.bean;

import junit.framework.Assert;

import org.junit.Test;

public class UserTestBeanTest {

	@Test
	public final void testConstructor() {
		new UserTestBean();
	}

	@Test
	public final void testGetersAndSetters() {
		UserTestBean test = new UserTestBean();
		boolean result = true;
		test.setComplexity("low");
		result = test.getComplexity().equals("low");

		test.setCountOfQuestions(5);
		result = test.getCountOfQuestions() == 5;

		test.setId(5L);
		result = test.getId() == 5L;

		test.setName("TEST");
		result = test.getName().equals("TEST");

		test.setSubject("JAVA");
		result = test.getSubject().equals("JAVA");

		test.setTimer(50);
		result = test.getTimer() == 50;

		test.setLogin("login");
		result = test.getLogin().equals("login");

		test.setResult("5.5");
		result = test.getResult().compareTo("5.5") == 0;

		result = test.toString() != "";
		Assert.assertTrue(result);
	}
}
