package ua.nure.bogdanov.SummaryTask4.parser.entity;

/**
 * Constraint item entity.
 * 
 * @author I.Bogdanov
 * 
 */
public class Field {
	private String name;
	private int maxLenght;
	private int minLenght;
	private boolean notNull;
	private String mustContains;

	public int getMaxLenght() {
		return maxLenght;
	}

	public void setMaxLenght(int maxLenght) {
		this.maxLenght = maxLenght;
	}

	public int getMinLenght() {
		return minLenght;
	}

	public void setMinLenght(int minLenght) {
		this.minLenght = minLenght;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public String getMustContains() {
		return mustContains;
	}

	public void setMustContains(String mustContains) {
		this.mustContains = mustContains;
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
		builder.append("Field [name=");
		builder.append(name);
		builder.append(", maxLenght=");
		builder.append(maxLenght);
		builder.append(", minLenght=");
		builder.append(minLenght);
		builder.append(", notNull=");
		builder.append(notNull);
		builder.append(", mustContains=");
		builder.append(mustContains);
		builder.append("]");
		return builder.toString();
	}

}
