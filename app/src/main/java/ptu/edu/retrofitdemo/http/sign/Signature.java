package ptu.edu.retrofitdemo.http.sign;
/**
 * @author jun.huyj
 */
public interface Signature {

    public String sign(String content, String key) throws Exception;

    public boolean verify(String content, String sign, String key) throws Exception;
}
