package ua.nure.bogdanov.SummaryTask4.parser;

import java.io.IOException;
import java.util.HashMap;
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

import ua.nure.bogdanov.SummaryTask4.parser.constants.Constants;
import ua.nure.bogdanov.SummaryTask4.parser.constants.XML;
import ua.nure.bogdanov.SummaryTask4.parser.entity.Constraint;
import ua.nure.bogdanov.SummaryTask4.parser.entity.Field;

/**
 * Controller for DOM parser.
 * 
 * @author I.Bogdanov
 * 
 */
public class ValidatorDOMParser {

	private String xmlFileName;

	// main container
	private Constraint constraint;

	public ValidatorDOMParser(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public Constraint getConstraint() {
		return constraint;
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
		constraint = new Constraint();

		// obtain questions nodes
		NodeList fieldNodes = root.getElementsByTagName(XML.FIELD.value());
		Map<String, Field> fields = new HashMap<String, Field>();
		// process questions nodes
		for (int j = 0; j < fieldNodes.getLength(); j++) {
			Field field = getFields(fieldNodes.item(j));
			fields.put(field.getName(), field);
		}
		constraint.setFields(fields);
	}

	/**
	 * Extracts question object from the question XML node.
	 * 
	 * @param qNode
	 *            Question node.
	 * @return Question object.
	 */
	private Field getFields(Node qNode) {
		Field field = new Field();
		Element qElement = (Element) qNode;

		// process question text
		Node qtNode = qElement.getElementsByTagName(XML.NAME.value()).item(0);
		field.setName(qtNode.getTextContent());
		qtNode = qElement.getElementsByTagName(XML.MAX_LENGTH.value()).item(0);
		field.setMaxLenght(Integer.parseInt(qtNode.getTextContent()));
		qtNode = qElement.getElementsByTagName(XML.MIN_LENGHT.value()).item(0);
		field.setMinLenght(Integer.parseInt(qtNode.getTextContent()));
		qtNode = qElement.getElementsByTagName(XML.NOT_NULL.value()).item(0);
		field.setNotNull(Boolean.parseBoolean(qtNode.getTextContent()));
		qtNode = qElement.getElementsByTagName(XML.MUST_CONTAINS.value()).item(
				0);
		field.setMustContains(qtNode.getTextContent());

		return field;
	}
}