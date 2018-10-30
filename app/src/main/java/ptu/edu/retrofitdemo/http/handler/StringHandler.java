package ptu.edu.retrofitdemo.http.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringHandler {
    /**
     * UTF-8编码
     */
    public static String UTFCode(String value) {
        String resultValue = null;
        try {
            resultValue = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultValue;
    }
}
