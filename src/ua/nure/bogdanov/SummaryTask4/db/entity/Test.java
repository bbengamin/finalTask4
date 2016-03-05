package ua.nure.bogdanov.SummaryTask4.db.entity;

/**
 * Test item entity.
 * 
 * @author I.Bogdanov
 * 
 */
public class Test extends Entity {

	private static final long serialVersionUID = 4716395168539434663L;

	private Integer subject;

	private Integer complexity;

	private String name;

	private Integer timer;

	public Integer getSubject() {
		return subject;
	}

	public void setSubject(Integer subject) {
		this.subject = subject;
	}

	public Integer getComplexity() {
		return complexity;
	}

	public void setComplexity(Integer complexity) {
		this.complexity = complexity;
	}

	public Integer getTimer() {
		return timer;
	}

	public void setTimer(Integer timer) {
		this.timer = timer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Test [subject=");
		builder.append(subject);
		builder.append(", complexity=");
		builder.append(complexity);
		builder.append(", name=");
		builder.append(name);
		builder.append(", timer=");
		builder.append(timer);
		builder.append("]");
		return builder.toString();
	}

}