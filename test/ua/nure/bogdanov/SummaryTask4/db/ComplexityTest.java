package ua.nure.bogdanov.SummaryTask4.db;

import org.junit.Assert;
import org.junit.Test;

public class ComplexityTest {

	@Test
	public void testConstructor1() {
		Complexity e = Complexity.HARD;
		Complexity.values();
		e.toString();
	}

	@Test
	public void testGetName() {
		Complexity.HARD.getName();
	}

	@Test
	public void testGetComplexity() {
		ua.nure.bogdanov.SummaryTask4.db.entity.Test test = new ua.nure.bogdanov.SummaryTask4.db.entity.Test();
		test.setComplexity(2);
		Assert.assertNotNull(Complexity.getComplexity(test));
	}

	@Test
	public void testValueOf() {
		Complexity.valueOf("LOW");
	}

	@Test
	public void testGetId() {
		Assert.assertNotNull(Complexity.HARD.getId());
	}

}
