package ua.nure.bogdanov.SummaryTask4.db;

import org.junit.Assert;
import org.junit.Test;

import ua.nure.bogdanov.SummaryTask4.db.entity.User;

public class RoleTest {

	@Test
	public void testConstructor1() {
		Role e = Role.ADMIN;
		Complexity.values();
		e.toString();
	}

	@Test
	public void testGetName() {
		 Role.ADMIN.getName();
	}

	@Test
	public void testGetRole() {
		User user = new User();
		user.setRoleId(0);
		Assert.assertNotNull(Role.getRole(user));
	}

	@Test
	public void testValueOf() {
		Role.valueOf("ADMIN");
	}
}
