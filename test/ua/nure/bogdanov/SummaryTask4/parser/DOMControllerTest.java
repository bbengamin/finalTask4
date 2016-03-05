package ua.nure.bogdanov.SummaryTask4.parser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import junit.framework.Assert;

public class DOMControllerTest {
	private final String path = "test.xml";
	private final String path2 = "test-invalid.xml";

	@Test
	public final void testConstructor() {
		new DOMController(path);
	}

	@Test
	public final void testParse1() {
		DOMController con = new DOMController(path);
		try {
			con.parse(true);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = SAXException.class)
	public final void testParse2() throws ParserConfigurationException,
			SAXException, IOException {
		DOMController con = new DOMController(path2);
		con.parse(true);
	}

	@Test
	public final void testGetters() {
		DOMController con = new DOMController(path);
		boolean result = true;
		try {
			con.parse(true);
			if (con.getAnswers() == null) {
				result = false;
			}
			if (con.getQuestions() == null) {
				result = false;
			}
			if (con.getTest() == null) {
				result = false;
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(result);
	}

}