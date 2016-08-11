package app.base;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.account.entity.Account;

public class CookieTool {

	public static String getCookies(HttpServletRequest request, String key)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		if (null != cookies && cookies.length > 0) {
			String value = null;
			for (int i = 0; i < cookies.length; i++) {
				Cookie newCookie = cookies[i];
				if (newCookie.getName().equals(key)
						&& newCookie.getValue() != null
						&& !newCookie.getValue().equals("")) {
					value = newCookie.getValue();
					break;
				}
			}
			if (value != null) {
				return URLDecoder.decode(value, "utf-8");
			}
		}
		return null;
	}

	public static void setCookies(HttpServletResponse response, String key,
			String value) throws Exception {
		value = URLEncoder.encode(value, "UTF-8");
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 30);
		response.addCookie(cookie);
	}

	public static void cleanCookies(HttpServletResponse response, String key) {
		Cookie cookie = new Cookie(key, null);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.contains(",")) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	public static String getKey(HttpServletRequest request) throws Exception {
		StringBuffer key = new StringBuffer(request.getRequestURI());
		for (Object k : request.getParameterMap().keySet()) {
			String value = request.getParameter(k.toString());
			if (null != value && !"".equals(value.trim())) {
				key = key.append((key.indexOf("?") != -1) ? "&" : "?")
						.append(k.toString()).append("=").append(value);
			}
		}
		return key.toString();
	}

	private final static String MAXTHON = "Maxthon";
	private final static String QQ = "QQBrowser";
	private final static String GREEN = "GreenBrowser";
	private final static String FIREFOX = "Firefox";
	private final static String OPERA = "Opera";
	private final static String SAFARI = "Safari";
	private final static String OTHER = "其它";

	public static String checkBrowse(String userAgent) {
		if (regex("Opera", userAgent))
			return OPERA;
		if (regex("Chrome", userAgent))
			return "Chrome";
		if (regex("Firefox", userAgent))
			return FIREFOX;
		if (regex("Safari", userAgent))
			return SAFARI;
		if (regex("360SE", userAgent))
			return "360SE";
		if (regex("GreenBrowser", userAgent))
			return GREEN;
		if (regex("QQBrowser", userAgent))
			return QQ;
		if (regex("Maxthon", userAgent))
			return MAXTHON;
		if (regex("rv:11.0", userAgent))
			return "IE11";
		if (regex("MSIE 10.0", userAgent))
			return "IE10";
		if (regex("MSIE 9.0", userAgent))
			return "IE9";
		if (regex("MSIE 8.0", userAgent))
			return "IE8";
		if (regex("MSIE 7.0", userAgent))
			return "IE7";
		if (regex("MSIE 6.0", userAgent))
			return "IE6";
		return OTHER;
	}

	public static boolean regex(String regex, String str) {
		Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
		Matcher m = p.matcher(str);
		return m.find();
	}
	
	public static void logCookie(Account account,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String input = account.getAccount() + "," + account.getPassword();
		String dbc = cookieEncrypt(input);
		request.getSession().setAttribute("dbc", dbc);
		setCookies(response, "dbc_", dbc);
	}
	
	private static String cookieEncrypt(String input) {
		input = Base64Encoder.encode(input);
		StringBuffer output = new StringBuffer();
		for (int n = 0; n < input.toCharArray().length; n++) {
			int c = input.toCharArray()[n];
			if (n % 2 == 0) {
				c = c + 1;
				output.append(String.valueOf(((char) c)));
			} else {
				c = c - 1;
				output.append(String.valueOf(((char) c)));
			}
		}
		return output.toString();
	}
	
	private static String cookieDecipher(String input) {
		StringBuffer output = new StringBuffer();
		for (int n = 0; n < input.toCharArray().length; n++) {
			int c = input.toCharArray()[n];
			if (n % 2 == 0) {
				c = c - 1;
				output.append(String.valueOf(((char) c)));
			} else {
				c = c + 1;
				output.append(String.valueOf(((char) c)));
			}
		}
		return Base64Decoder.decode(output.toString());
	}
	
	public static Account getCookieAccount(HttpServletRequest request) throws Exception{
		String dbc = getCookies(request, "dbc_");
		String[] all = cookieDecipher(dbc).split(",");
		if (null == all || all.length != 2) {
			return null;
		}
		Account account = new Account();
		account.setAccount(all[0]);
		account.setPassword(all[1]);
		return account;
	}
}