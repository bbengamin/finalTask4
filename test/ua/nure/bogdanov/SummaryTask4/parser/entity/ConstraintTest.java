package ua.nure.bogdanov.SummaryTask4.parser.entity;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class ConstraintTest {
	@Test
	public final void testConstructor() {
		new Constraint();
	}

	@Test
	public final void testGettersAndSetters() {
		Constraint c = new Constraint();
		Map<String, Field> fields = new HashMap<String, Field>();
		fields.put("field", new Field());

		c.setFields(fields);
		Assert.assertNotNull(c.getField("field"));
	}

	@Test
	public final void testToString() {
		new Constraint().toString();
	}

}