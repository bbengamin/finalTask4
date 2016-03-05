package ua.nure.bogdanov.SummaryTask4.parser.constants;

import org.junit.Assert;
import org.junit.Test;

public class XMLTest {

	@Test
	public void testConstructor1() {
		XML e = XML.ANSWER;
		XML.values();
		e.toString();
	}

	@Test
	public void testEqualsTo() {
		Assert.assertTrue(XML.ANSWER.equalsTo(XML.ANSWER.value()));
	}

	@Test
	public void testValueOf() {
		XML.valueOf("ANSWER");
	}

	@Test
	public void testValue() {
		XML.CONSTRAINT.value();
	}

}
