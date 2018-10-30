package ptu.edu.retrofitdemo.http.sign;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterUtil {

	/**
	 * 
	 * @param params
	 * @return
	 */
	public static String getSignData(Map<String, String> params) {
		StringBuffer content = new StringBuffer();

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);

			if ("sign".equals(key)) {
				continue;
			}
			String value = (String) params.get(key);
			if (value != null) {
				content.append((i == 0 ? "" : "&") + key + "=" + value);
			} else {
				content.append((i == 0 ? "" : "&") + key + "=");
			}

		}

		return content.toString();
	}

	/**
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String mapToUrl(Map<String, String> params)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		for (String key : params.keySet()) {
			String value = params.get(key);
			// System.out.println(key+":"+value);
			if (isFirst) {
				sb.append(key + "=" + URLEncoder.encode(value, "utf-8"));
				isFirst = false;
			} else {
				if (value != null) {
					sb.append("&" + key + "="
							+ URLEncoder.encode(value, "utf-8"));
				} else {
					sb.append("&" + key + "=");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @param url
	 * @param name
	 * @return
	 */
	public static String getParameter(String url, String name) {
		if (name == null || name.equals("")) {
			return null;
		}
		name = name + "=";
		int start = url.indexOf(name);
		if (start < 0) {
			return null;
		}
		start += name.length();
		int end = url.indexOf("&", start);
		if (end == -1) {
			end = url.length();
		}
		return url.substring(start, end);
	}

	/**
	 * @param header
	 * @return
	 */
	final static public HashMap<String, String> getParametersByHttpHead(
			String header) {

		String data = header.substring(header.indexOf("?") + 1, header
				.indexOf(" HTTP"));
		return getParametersByContents(data);
	}

	/**
	 * @param contents
	 * @return
	 */
	final static public HashMap<String, String> getParametersByContents(
			String contents) {
		HashMap<String, String> map = new HashMap<String, String>();
		String[] keyValues = contents.split("&");
		for (int i = 0; i < keyValues.length; i++) {
//			if (!keyValues[i].contains("[=]"))continue;
			String key = keyValues[i].substring(0, keyValues[i].indexOf("="));
			String value = keyValues[i]
					.substring(keyValues[i].indexOf("=") + 1);
			map.put(key, value);
		}
		return map;
	}
}
