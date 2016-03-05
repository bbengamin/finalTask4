package ua.nure.bogdanov.SummaryTask4.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import ua.nure.bogdanov.SummaryTask4.db.entity.Answer;
import ua.nure.bogdanov.SummaryTask4.db.entity.Question;
import ua.nure.bogdanov.SummaryTask4.db.entity.Test;
import ua.nure.bogdanov.SummaryTask4.parser.constants.Constants;
import ua.nure.bogdanov.SummaryTask4.parser.constants.XML;

/**
 * Controller for DOM parser.
 * 
 * @author I.Bogdanov
 * 
 */
public class DOMController {

	private String xmlFileName;
	// main container
	private Test test;
	private List<Question> questions;
	private Map<Question, List<Answer>> answers;

	public DOMController(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public Test getTest() {
		return test;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public Map<Question, List<Answer>> getAnswers() {
		return answers;
	}

	/**
	 * Parses XML document.
	 * 
	 * @param validate
	 *            If true validate XML document against its XML schema.
	 */
	public void parse(boolean validate) throws ParserConfigurationException,
			SAXException, IOException {

		// obtain DOM parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// set properties for Factory

		// XML document contains namespaces
		dbf.setNamespaceAware(true);

		// make parser validating
		if (validate) {
			// turn validation on
			dbf.setFeature(Constants.FEATURE_TURN_VALIDATION_ON, true);

			// turn on xsd validation
			dbf.setFeature(Constants.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
		}

		DocumentBuilder db = dbf.newDocumentBuilder();

		// set error handler
		db.setErrorHandler(new DefaultHandler() {
			@Override
			public void error(SAXParseException e) throws SAXException {
				// throw exception if XML document is NOT valid
				throw e;
			}
		});

		// parse XML document
		Document document = db.parse(xmlFileName);

		// get root element
		Element root = document.getDocumentElement();

		// create container
		test = new Test();
		questions = new ArrayList<>();
		answers = new HashMap<>();

		// obtain questions nodes
		NodeList questionNodes = root
				.getElementsByTagName(XML.QUESTION.value());

		// process questions nodes
		for (int j = 0; j < questionNodes.getLength(); j++) {
			Question question = getQuestion(questionNodes.item(j));
			// add question to container
			questions.add(question);
		}
	}

	/**
	 * Extracts question object from the question XML node.
	 * 
	 * @param qNode
	 *            Question node.
	 * @return Question object.
	 */
	private Question getQuestion(Node qNode) {
		Question question = new Question();
		List<Answer> tempAnswers = new ArrayList<>();
		Element qElement = (Element) qNode;

		// process question text
		Node qtNode = qElement.getElementsByTagName(XML.QUESTION_TEXT.value())
				.item(0);
		// set question text
		question.setBody(qtNode.getTextContent());

		// process answers
		NodeList aNodeList = qElement.getElementsByTagName(XML.ANSWER.value());
		for (int j = 0; j < aNodeList.getLength(); j++) {
			Answer answer = getAnswer(aNodeList.item(j));
			// add answer
			tempAnswers.add(answer);
		}
		answers.put(question, tempAnswers);

		return question;
	}

	/**
	 * Extracts answer object from the answer XML node.
	 * 
	 * @param aNode
	 *            Answer node.
	 * @return Answer object.
	 */
	private Answer getAnswer(Node aNode) {
		Answer answer = new Answer();
		Element aElement = (Element) aNode;

		// process correct
		String correct = aElement.getAttribute(XML.CORRECT.value());
		answer.setCorrect(Boolean.valueOf(correct));

		// process content
		String content = aElement.getTextContent();
		answer.setBody(content);

		return answer;
	}
}