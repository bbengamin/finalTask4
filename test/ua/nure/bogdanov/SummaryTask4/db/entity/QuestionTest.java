package ua.nure.bogdanov.SummaryTask4.db.entity;

import junit.framework.Assert;

import org.junit.Test;

public class QuestionTest {

	@Test
	public final void testConstructor() {
		new Question();
	}

	@Test
	public final void testGetersAndSetters() {
		Question test = new Question();
		boolean result = true;

		test.setBody("hi");
		result = test.getBody().equals("hi");

		test.setId(5L);
		result = test.getId() == 5L;

		result = test.toString() != "";
		Assert.assertTrue(result);
	}
}
