package ua.nure.bogdanov.SummaryTask4.db.entity;

import junit.framework.Assert;

import org.junit.Test;

public class SubjectTest {

	@Test
	public final void testConstructor() {
		new Subject();
	}

	@Test
	public final void testGetersAndSetters() {
		Subject test = new Subject();
		boolean result = true;

		test.setName("hi");
		result = test.getName().equals("hi");

		test.setId(5L);
		result = test.getId() == 5L;

		result = test.toString() != "";
		Assert.assertTrue(result);
	}
}
