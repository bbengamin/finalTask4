package ua.nure.bogdanov.SummaryTask4.parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import junit.framework.Assert;

public class ValidatorDOMParserTest {
	private final String path = "test2.xml";
	private final String path2 = "test2-invalid.xml";

	@Test
	public final void testConstructor() {
		new ValidatorDOMParser(path);
	}

	@Test
	public final void testParse1() {
		ValidatorDOMParser con = new ValidatorDOMParser(path);
		try {
			con.parse(true);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = SAXException.class)
	public final void testParse2() throws ParserConfigurationException,
			SAXException, IOException {
		ValidatorDOMParser con = new ValidatorDOMParser(path2);
		con.parse(true);
	}

	@Test
	public final void testGetters() {
		ValidatorDOMParser con = new ValidatorDOMParser(path);
		boolean result = true;
		try {
			con.parse(true);
			if (con.getConstraint() == null) {
				result = false;
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(result);
	}

}