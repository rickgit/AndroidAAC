package ptu.edu.retrofitdemo.http.sign;

import java.util.Map;

/**
 * @project wapclient_service
 * @author yinhui.cheng
 * @date 2011-6-7 Copyright (C) 2010-2012  Inc. All rights
 *       reserved.
 */
public class HmacSHA1Signature implements Signature {

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";


	public String sign(String content, String secretKey) throws Exception {

		byte[] encryptBytes = HmacSHA1.getHmacSHA1(content, secretKey);
		return new String(Base64.encode(encryptBytes), "UTF-8");
	}


	public boolean verify(String content, String sign, String secretKey)
			throws Exception {

		final String original = sign;
		final String expected = this.sign(content, secretKey);

		if (original.equals(expected)) {
			return true;
		}
		return false;
	}

	public static String qianMing(String url, String secretKey) {
		String param = url;

		Map<String, String> paramMap = ParameterUtil.getParametersByContents(param);
		String sortedParam = ParameterUtil.getSignData(paramMap);
		String s = null;
		try {
			Signature sig = new HmacSHA1Signature();
			s = sig.sign(sortedParam, secretKey);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
}
