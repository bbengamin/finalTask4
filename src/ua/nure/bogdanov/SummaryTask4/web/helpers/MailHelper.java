package ua.nure.bogdanov.SummaryTask4.web.helpers;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Send mail helper
 * 
 * @author I.Bogdanov
 * 
 */
public class MailHelper {
	// here my mail credentials
	static final String USERNAME = "bbengamin@gmail.com";
	static final String PASWWORD = "qvigfnqrfhcmwxjp";

	/**
	 * Send mail to user
	 * 
	 * @param mail
	 *            user email
	 * @param subject
	 *            subject of list
	 * @param message
	 *            message for user
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void sendMailCongratulations(String mail, String subject,
			String message) throws MessagingException {
		Message msg = new MimeMessage(getSession());

		msg.setFrom(new InternetAddress("bbengamin@gmail.com"));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
		msg.setSubject(subject);
		msg.setText(message);
		Transport.send(msg);
	}

	/**
	 * Send user mail to me
	 * 
	 * @param mail
	 *            user mail
	 * @param subject
	 *            user subject
	 * @param message
	 *            user message
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void sendMail(String mail, String subject, String message)
			throws MessagingException {
		Message msg = new MimeMessage(getSession());

		msg.setFrom(new InternetAddress(mail));
		msg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(USERNAME));
		msg.setSubject(subject);
		msg.setText(message);
		Transport.send(msg);
	}

	/**
	 * Get session method
	 * 
	 * @return session
	 */
	private static Session getSession() {
		Session session = Session.getDefaultInstance(getProperties(),
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(USERNAME, PASWWORD);
					}
				});
		return session;
	}

	/**
	 * Setting properties
	 * 
	 * @return Properties
	 */
	private static Properties getProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		return properties;
	}
}