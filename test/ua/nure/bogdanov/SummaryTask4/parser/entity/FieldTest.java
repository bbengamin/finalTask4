package ua.nure.bogdanov.SummaryTask4.parser.entity;

import junit.framework.Assert;

import org.junit.Test;

public class FieldTest {
	@Test
	public final void testConstructor() {
		new Field();
	}

	@Test
	public final void testGetterAndSetters() {
		Field f = new Field();
		boolean result = true;

		f.setMaxLenght(5);
		result = f.getMaxLenght() == 5;

		f.setMinLenght(2);
		result = f.getMinLenght() == 2;

		f.setMustContains("hello");
		result = f.getMustContains().equals("hello");

		f.setName("name");
		result = f.getName().equals("name");

		f.setNotNull(true);
		result = f.isNotNull();

		Assert.assertTrue(result);
	}

	@Test
	public final void testToString() {
		new Field().toString();
	}

}
