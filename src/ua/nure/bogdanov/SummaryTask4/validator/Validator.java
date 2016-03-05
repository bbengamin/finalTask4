package ua.nure.bogdanov.SummaryTask4.validator;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ua.nure.bogdanov.SummaryTask4.exception.InvalidDataException;
import ua.nure.bogdanov.SummaryTask4.parser.ValidatorDOMParser;
import ua.nure.bogdanov.SummaryTask4.parser.entity.Constraint;
import ua.nure.bogdanov.SummaryTask4.parser.entity.Field;

/**
 * Validator of user fields.
 * 
 * @author I.Bogdanov
 * 
 */
public class Validator {
	private Constraint constraint;

	/**
	 * Constructor
	 * 
	 * @param xmlFileName
	 *            name of the file where the settings are recorded.
	 * @throws InvalidDataException
	 */
	public Validator(String xmlFileName) throws InvalidDataException {
		if (xmlFileName != null) {
			getPropertyFormXML(xmlFileName);
		}
	}

	/**
	 * Get properties from xml
	 * 
	 * @param xmlFileName
	 *            name of the file where the settings are recorded.
	 * @throws InvalidDataException
	 */
	private void getPropertyFormXML(String xmlFileName)
			throws InvalidDataException {
		ValidatorDOMParser parser = new ValidatorDOMParser(xmlFileName);
		try {
			parser.parse(true);
			constraint = parser.getConstraint();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new InvalidDataException("Cant load settings --> " + e);
		}
	}

	/**
	 * Valaidationg field o with given fieldName
	 * 
	 * @param o
	 *            - field which validating
	 * @param fieldName
	 * @return true if the field is valid
	 * @throws InvalidDataException
	 */
	public boolean isValid(Object o, String fieldName)
			throws InvalidDataException {
		String field = String.valueOf(o);
		Field propertys = constraint.getField(fieldName);
		if (propertys == null) {
			return true;
		}
		if (propertys.isNotNull() && (field == null || field.isEmpty())) {
			throw new InvalidDataException(fieldName + " is empty");
		}
		if (field.length() > propertys.getMaxLenght()) {
			throw new InvalidDataException(fieldName + " is not small enough");
		}
		if (field.length() < propertys.getMinLenght()) {
			throw new InvalidDataException(fieldName + " is not large enough");
		}
		if (propertys.getMustContains() != null) {
			String[] mas = propertys.getMustContains().split(",");
			for (String string : mas) {
				if (!field.contains(string)) {
					throw new InvalidDataException(fieldName
							+ " does not contain the required symbols");
				}
			}
		}

		return true;
	}

	/**
	 * Valaidating field o with given fieldName
	 * 
	 * @param o
	 *            - field which validating
	 * @param fieldName
	 * @return true if the field is valid
	 */
	public boolean isValidWithOutEx(Object o, String fieldName) {
		String field = String.valueOf(o);
		Field propertys = constraint.getField(fieldName);
		if (propertys == null) {
			return true;
		}
		if (propertys.isNotNull() && (field == null || field.isEmpty())) {
			return false;
		}
		if (field.length() > propertys.getMaxLenght()) {
			return false;
		}
		if (field.length() < propertys.getMinLenght()) {
			return false;
		}
		if (propertys.getMustContains() != null) {
			String[] mas = propertys.getMustContains().split(",");
			for (String string : mas) {
				if (!field.contains(string)) {
					return false;
				}
			}
		}
		return true;
	}
}
