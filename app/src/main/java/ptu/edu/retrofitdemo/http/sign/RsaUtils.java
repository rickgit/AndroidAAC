package ptu.edu.retrofitdemo.http.sign;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RsaUtils {

	public static String RsaPublicKey = "";// Rsa公钥
	public static String RsaPublicKeyName="RsaPublicKey";

	/**
	 * RSA私钥解密
	 * 
	 * @param ciphertextByBase64
	 *            密文base64码
	 * @param privateKeyByBase64
	 *            密钥base64码
	 * @param charset
	 *            明文对应的字符编码， 用于构造明文字符串
	 * @return 明文字符串
	 */
	public static String decryptByPrivateKey(String ciphertextByBase64, String privateKeyByBase64) {
		if (isBlank(ciphertextByBase64) || isBlank(privateKeyByBase64)) {
			throw new IllegalArgumentException("ciphertext or privateKey cann't empty!");
		}
		RFC2045Base64.decode(privateKeyByBase64);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(RFC2045Base64.decode(privateKeyByBase64));
		byte[] textBytes = null;
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(spec);
			// privateKey.getEncoded().length;
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			textBytes = cipher.doFinal(RFC2045Base64.decode(ciphertextByBase64));
		} catch (Throwable throwable) {
			throw new RuntimeException("RSA 私钥解密异常：" + throwable.getMessage(), throwable);
		}
		try {
			return new String(textBytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA公钥加密
	 * 
	 * @param text
	 *            明文
	 * @param publicKeyByBase64
	 *            公钥
	 * @return 密文的base64码
	 */
	public static String encryptByPublicKey(String text, String publicKeyByBase64) {
		if (isBlank(text) || isBlank(publicKeyByBase64)) {
			throw new IllegalArgumentException("text or publicKey cann't empty!");
		}
		X509EncodedKeySpec x509Spec = new X509EncodedKeySpec(RFC2045Base64.decode(publicKeyByBase64));
		byte[] cipherBytes = null;
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(x509Spec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			cipherBytes = cipher.doFinal(text.getBytes("utf-8"));
		} catch (Throwable throwable) {
			// TODO 删除保存的密码
//			SharePrefsUtils.setUserName(BaseApplication.mApp.getApplicationContext(), "");
//			SharePrefsUtils.setUserPwd(BaseApplication.mApp.getApplicationContext(), "");
			//throw new RuntimeException("RSA 公钥加密异常：" + throwable.getMessage() + " text:" + text, throwable);
			Log.e("RSA 公钥加密异常", "Text: " + text, throwable);
		}
		return RFC2045Base64.encodeToString(cipherBytes);
	}

	private static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static void main() {
		String privateKeyByBase64 = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAjethnaD5vLtOAAopEZ7lK+iwGa/MH/5GWmeMaV9fmxKmK1LVtNSsvJvM9RAnW0gFN75sk182xf1ZUjhkDwc0FwIDAQABAkEAiG2DXH24Ngc1N3KNAmRmWCyKxVaq+wJ8bUHnyBAoHzB0yFtMhsNCFSRvbFFBTP0cf6UbNxrZZEeyaWHZ1eWaIQIhAPs31qqn9GBWKUHUTCH+FjRFI4s83lCeENcDhsqjm5XHAiEAkJ7w5x0U/VH4P42lhLLctr9oTJuDL0sF/Oq8hmRALzECIBKjG9vhRfH3smvd0iAbd449KtvjkiKs91bcPh2+cYZ1AiBo4ewiIHQ9dzoLlUWJ5FGvc0dH76yHGui/0NliucfWsQIgO3gv+xaIU+MA+PwIuq98Drd7p5aodopm+A+Hpuj+ttA=";
		String publicKeyByBase64 = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAI3rYZ2g+by7TgAKKRGe5SvosBmvzB/+RlpnjGlfX5sSpitS1bTUrLybzPUQJ1tIBTe+bJNfNsX9WVI4ZA8HNBcCAwEAAQ==";

		String text = "defqwef=asdfadfadsfadf567567575757";
		System.out.println("加密文本:" + text);

		// // 私钥加密，公钥解密
		// Long s = System.nanoTime();
		// String cipherText = encryptByPrivateKey(text, privateKeyByBase64,
		// Charset.forName("utf-8"));
		// System.out.println("私钥加密耗时:" + ((System.nanoTime() - s)));
		// System.out.println("私钥加密后密文:" + cipherText);
		// s = System.nanoTime();
		// String decryptedText = decryptByPublicKey(cipherText,
		// publicKeyByBase64, Charset.forName("utf-8"));
		// System.out.println("公钥解ss密耗时:" + ((System.nanoTime() - s)));
		// System.out.println("公钥解密后明文:" + decryptedText);
		//
		// // 公钥加密，私钥解密
		// System.out.println("===================");
		// s = System.nanoTime();
		// cipherText = encryptByPublicKey(text, publicKeyByBase64,
		// Charset.forName("utf-8"));
		// System.out.println("公钥加密耗时:" + ((System.nanoTime() - s)));
		// System.out.println("公钥加密后密文:" + cipherText);
		// s = System.nanoTime();
		// decryptedText = decryptByPrivateKey(cipherText, privateKeyByBase64,
		// Charset.forName("utf-8"));
		// System.out.println("私钥解密耗时:" + ((System.nanoTime() - s)));
		// System.out.println("私钥钥解密后明文:" + decryptedText);

	}
}
