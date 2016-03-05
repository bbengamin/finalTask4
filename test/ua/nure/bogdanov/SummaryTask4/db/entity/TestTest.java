package ua.nure.bogdanov.SummaryTask4.db.entity;

import junit.framework.Assert;

import org.junit.Test;

public class TestTest {

	@Test
	public final void testConstructor() {
		new User();
	}

	@Test
	public final void testGetersAndSetters() {
		ua.nure.bogdanov.SummaryTask4.db.entity.Test test = new ua.nure.bogdanov.SummaryTask4.db.entity.Test();
		boolean result = true;

		test.setComplexity(5);
		result = test.getComplexity() == 5;

		test.setId(5L);
		result = test.getId() == 5L;

		test.setName("TEST");
		result = test.getName().equals("TEST");

		test.setSubject(3);
		result = test.getSubject() == 3;

		test.setTimer(50);
		result = test.getTimer() == 50;

		result = test.toString() != "";
		Assert.assertTrue(result);
	}
}
