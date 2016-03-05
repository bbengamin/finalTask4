package ua.nure.bogdanov.SummaryTask4.db.bean;

import ua.nure.bogdanov.SummaryTask4.db.entity.Entity;

/**
 * Provide records for virtual table:
 * 
 * <pre>
 * |tests.id|tests.name|subjects.name|tests.complexity_id|tests.timer|
 * </pre>
 * 
 * @author I.Bogdanov
 * 
 */
public class TestBean extends Entity {

	private static final long serialVersionUID = -5654982557199337483L;

	private String subject;

	private String complexity;

	private String name;

	private Integer timer;
	
	private Integer countOfQuestions;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getComplexity() {
		return complexity;
	}

	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTimer() {
		return timer;
	}

	public void setTimer(Integer timer) {
		this.timer = timer;
	}

	public Integer getCountOfQuestions() {
		return countOfQuestions;
	}

	public void setCountOfQuestions(Integer countOfQuestions) {
		this.countOfQuestions = countOfQuestions;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestBean [subject=");
		builder.append(subject);
		builder.append(", complexity=");
		builder.append(complexity);
		builder.append(", name=");
		builder.append(name);
		builder.append(", timer=");
		builder.append(timer);
		builder.append(", countOfQuestions=");
		builder.append(countOfQuestions);
		builder.append("]");
		return builder.toString();
	}
}
