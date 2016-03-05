package ua.nure.bogdanov.SummaryTask4.db.entity;

/**
 * Subject entity.
 * 
 * @author I.Bogdanov
 * 
 */
public class Subject extends Entity {

	private static final long serialVersionUID = 5692708766041889396L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Subject [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
