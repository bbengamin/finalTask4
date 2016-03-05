package ua.nure.bogdanov.SummaryTask4.db.entity;

/**
 * Question entity.
 * 
 * @author I.Bogdanov
 * 
 */
public class Question extends Entity {

	private static final long serialVersionUID = 2386302708905518585L;

	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Question [body=");
		builder.append(body);
		builder.append("]");
		return builder.toString();
	}

}
