package ua.nure.bogdanov.SummaryTask4.db.bean;

/**
 * Provide records for virtual table:
 * 
 * <pre>
 * |users.login|tests.name|subjects.name|tests.complexity_id|tests.timer|users_tests.result|tests.id|
 * </pre>
 * 
 * @author I.Bogdanov
 * 
 */
public class UserTestBean extends TestBean {

	private static final long serialVersionUID = 927907897475042006L;

	private String login;
	private String result;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserTestBean [login=");
		builder.append(login);
		builder.append(", result=");
		builder.append(result);
		builder.append(", getSubject()=");
		builder.append(getSubject());
		builder.append(", getComplexity()=");
		builder.append(getComplexity());
		builder.append(", getName()=");
		builder.append(getName());
		builder.append(", getTimer()=");
		builder.append(getTimer());
		builder.append(", getCountOfQuestions()=");
		builder.append(getCountOfQuestions());
		builder.append(", getId()=");
		builder.append(getId());
		builder.append("]");
		return builder.toString();
	}
}
