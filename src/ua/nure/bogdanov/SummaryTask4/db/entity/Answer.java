package ua.nure.bogdanov.SummaryTask4.db.entity;

/**
 * Answer entity.
 * 
 * @author I.Bogdanov
 * 
 */

public class Answer extends Entity {

	private static final long serialVersionUID = 5520691267734973068L;

	private Long question;

	private String body;

	private Boolean correct;

	public Long getQuestion() {
		return question;
	}

	public void setQuestion(Long question) {
		this.question = question;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Answer [question=");
		builder.append(question);
		builder.append(", body=");
		builder.append(body);
		builder.append(", correct=");
		builder.append(correct);
		builder.append("]");
		return builder.toString();
	}

}
