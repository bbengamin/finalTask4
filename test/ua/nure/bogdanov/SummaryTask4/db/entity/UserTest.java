package ua.nure.bogdanov.SummaryTask4.db.entity;

import junit.framework.Assert;

import org.junit.Test;

public class UserTest {

	@Test
	public final void testConstructor() {
		new User();
	}

	@Test
	public final void testGetersAndSetters() {
		User test = new User();
		boolean result = true;

		test.setId(5L);
		result = test.getId() == 5L;

		test.setBlockMessage("hi");
		result = test.getBlockMessage().equals("hi");

		test.setEmail("mail");
		result = test.getEmail().equals("mail");

		test.setFirstName("name");
		result = test.getFirstName().equals("name");

		test.setLastName("last");
		result = test.getLastName().equals("last");

		test.setLocale("locale");
		result = test.getLocale().equals("locale");

		test.setLogin("login");
		result = test.getLogin().equals("login");

		test.setPassword("pass");
		result = test.getPassword().equals("pass");

		test.setRoleId(5);
		result = test.getRoleId() == 5;

		test.setStatus(true);
		result = test.isStatus();

		User user1 = test;

		result = user1.compareTo(test) == 0;

		result = test.toString() != "";
		Assert.assertTrue(result);
	}
}
