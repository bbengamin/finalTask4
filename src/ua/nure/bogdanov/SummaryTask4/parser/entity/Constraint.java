package ua.nure.bogdanov.SummaryTask4.parser.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Constraint item entity.
 * 
 * @author I.Bogdanov
 * 
 */
public class Constraint {
	private Map<String, Field> fields;

	public Constraint() {
		this.fields = new HashMap<>();
	}

	public Field getField(String fieldName) {
		return fields.get(fieldName);
	}

	public void setFields(Map<String, Field> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Constraint [fields=");
		builder.append(fields);
		builder.append("]");
		return builder.toString();
	}

}
