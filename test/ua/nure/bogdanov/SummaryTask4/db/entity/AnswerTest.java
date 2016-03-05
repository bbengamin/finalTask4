package ua.nure.bogdanov.SummaryTask4.db.entity;

import junit.framework.Assert;

import org.junit.Test;

public class AnswerTest {

	@Test
	public final void testConstructor() {
		new Answer();
	}

	@Test
	public final void testGetersAndSetters() {
		Answer test = new Answer();
		boolean result = true;
		
		test.setBody("hi");
		result = test.getBody().equals("hi");
		
		test.setCorrect(true);
		result = test.getCorrect();
		
		test.setQuestion(5L);
		result = test.getQuestion() == 5L;
		
		test.setId(5L);
		result = test.getId() == 5L;

		result = test.toString() != "";
		Assert.assertTrue(result);
	}
}
