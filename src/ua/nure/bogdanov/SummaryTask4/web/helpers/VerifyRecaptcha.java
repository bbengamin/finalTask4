package ua.nure.bogdanov.SummaryTask4.web.helpers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import ua.nure.bogdanov.SummaryTask4.exception.AppException;

/**
 * Verify Recaptcha
 * 
 * @author I.Bogdanov
 * 
 */
public class VerifyRecaptcha {
	private static final Logger LOG = Logger.getLogger(VerifyRecaptcha.class);

	public static final String URL = "https://www.google.com/recaptcha/api/siteverify";
	public static final String SECRET = "6LfgOQsTAAAAAJxkduWuRK31uTkhc3QuyvNO0siE";
	private static final String USER_AGENT = "Mozilla/5.0";

	public static boolean verify(String gRecaptchaResponse) throws AppException {

		LOG.debug("Command starts");
		if (gRecaptchaResponse == null || "".equals(gRecaptchaResponse)) {
			return false;
		}

		try {
			URL obj = new URL(URL);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			String postParams = "secret=" + SECRET + "&response="
					+ gRecaptchaResponse;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			LOG.trace("Sending 'POST' request to URL : " + URL);
			LOG.trace("Post parameters : " + postParams);
			LOG.trace("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			LOG.trace("Response result : " + response.toString());

			if (response.toString().contains("true")) {
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new AppException("Captcha", e);
		}
	}
}