package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;

import ua.nure.bogdanov.SummaryTask4.db.Role;
import ua.nure.bogdanov.SummaryTask4.db.entity.User;

/**
 * User tag
 * 
 * @author I.Bogdanov
 * 
 */
public class UserTag extends SimpleTagSupport {

	private User user;

	public void setUser(User user) {
		this.user = user;
	}


	public void doTag() throws JspException, IOException {
		StringWriter sw = new StringWriter();
		if (user != null) {
			JspWriter out = getJspContext().getOut();
			out.println("Имя: " + user.getFirstName());
			out.flush();
			out.println("<br>Фамилия: " + user.getLastName());
			out.newLine();
			out.println("<br>Логин: " + user.getLogin());
			out.newLine();
			out.println("<br>Почта: " + user.getEmail());
			out.newLine();
			out.println("<br>Роль: " + Role.getRole(user));
			out.newLine();
			if (user.isStatus()) {
				out.println("<br>Статус: заблокирован");
			} else {
				out.println("<br>Статус: свободен");
			}
			out.newLine();
			out.println("<br>Заметка администратора: " + user.getBlockMessage()
					+ "<br><hr>");
		} else {
			/* use message from the body */
			getJspBody().invoke(sw);
			getJspContext().getOut().println(sw.toString());
		}
	}

}