package ua.nure.bogdanov.SummaryTask4.db;

import ua.nure.bogdanov.SummaryTask4.db.entity.User;

/**
 * Role entity.
 * 
 * @author I.Bogdanov
 * 
 */

public enum Role {
	ADMIN, STUDENT;
	
	public static Role getRole(User user) {
		int roleId = user.getRoleId();
		return Role.values()[roleId];
	}
	
	public String getName() {
		return name().toLowerCase();
	}
	
}
