package ua.nure.bogdanov.SummaryTask4.db;

import ua.nure.bogdanov.SummaryTask4.db.entity.Test;

/**
 * Complexity entity.
 * 
 * @author I.Bogdanov
 * 
 */
public enum Complexity {
	LOW(0), MEDDIUM(1), HARD(2);

	private int id;

	private Complexity(int x) {
		id = x;
	}

	public static Complexity getComplexity(Test test) {
		int complexityId = test.getComplexity();
		return Complexity.values()[complexityId];
	}

	public String getName() {
		return name().toLowerCase();
	}

	public Integer getId() {
		return id;
	}
}