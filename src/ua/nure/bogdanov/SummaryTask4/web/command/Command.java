package ua.nure.bogdanov.SummaryTask4.web.command;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.bogdanov.SummaryTask4.exception.AppException;

/**
 * Main interface for the Command pattern implementation.
 * 
 * @author I.Bogdanov
 * 
 */
public abstract class Command implements Serializable {
	private static final long serialVersionUID = 8879403039606311780L;

	public static final String SEPARATOR = File.separator;
	public static final String XML_PATH = SEPARATOR
			+ "WEB-INF\\classes\\ua\\nure\\bogdanov\\SummaryTask4\\validator\\input.xml";
	public static final String ERROR_ATTRIBUTE = "errorMessage";

	/**
	 * Execution method for command.
	 * 
	 * @return Address to go once the command is executed.
	 */

	public abstract String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			AppException;

	@Override
	public final String toString() {
		return getClass().getSimpleName();
	}

	public String encode(HttpServletRequest request, String paramName)
			throws UnsupportedEncodingException {
		String requestEnc = "ISO-8859-1";
		String clientEnc = request.getParameter("charset");
		if (clientEnc == null) {
			clientEnc = "UTF-8";
		}

		return new String(request.getParameter(paramName).getBytes(requestEnc),
				clientEnc);
	}

	/**
	 * Returns md5 hash of input string
	 * 
	 * @param input
	 *            input string
	 * @return String of md5 hash
	 * @throws AppException
	 */
	public static String hash(String input) throws AppException {
		String result = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes("UTF8"));
			byte[] hash = digest.digest();

			Formatter f = new Formatter();
			for (byte b : hash) {
				f.format("%02x", Byte.valueOf(b));
			}
			result = f.toString();

		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			throw new AppException(e.getMessage(), e);
		}
		return result;
	}
}