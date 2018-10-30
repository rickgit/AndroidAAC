package ptu.edu.retrofitdemo.http.handler;

import android.os.Build;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import ptu.edu.retrofitdemo.http.config.AppConfig;
import ptu.edu.retrofitdemo.http.sign.HmacSHA1Signature;


public class ParamHandler {
    // func to inject params into url
    private static Request injectParamsIntoUrl(HttpUrl.Builder httpUrlBuilder, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        if (paramsMap.size() > 0) {
            Iterator iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
            requestBuilder.url(httpUrlBuilder.build());
            return requestBuilder.build();
        }
        return null;
    }


    /**
     * 对URL进行utf8并签名
     */
    public static String tiHuanUTF8url(Request oldRequest) {


        Iterator<String> iterator = oldRequest.url().queryParameterNames().iterator();
        StringBuilder paramUrlSbuild = new StringBuilder();
        while (iterator.hasNext()) {
            String appendingParamName = iterator.next();
            String paramValue = oldRequest.url().queryParameter(appendingParamName);
            paramUrlSbuild.append(appendingParamName + "=" + paramValue + "&");
        }
        if (paramUrlSbuild.length() > 0)
            paramUrlSbuild.deleteCharAt(paramUrlSbuild.length() - 1);
        String sign = HmacSHA1Signature.qianMing(paramUrlSbuild.toString(), AppConfig.S_signValue); // 签名

        //urlencode参数，防止出现中文字符
        String utf8URL = StringHandler.UTFCode(paramUrlSbuild.toString()).replaceAll("%3F", "?").replace("%2B", "+").replaceAll("%26", "&")
                .replaceAll("%3D", "=").replaceAll("%3A", ":").replaceAll("%21", "!").replaceAll("%2F", "/")
                .replaceAll("%2F%2F", "//").replaceAll("", "").replaceAll("%3B", ";").replaceAll("%23", "#");
//        StringBuffer result = new StringBuffer(utf8URL);
//        result.append(AppConfig.G_sign);
//        result.append(sign);

        return sign;
    }
    public static String tiHuanUTF8url(FormBody formBody) {
        StringBuilder paramUrlSbuild = new StringBuilder();
        for (int i = 0; i <formBody.size() ; i++) {
            paramUrlSbuild.append(formBody.encodedName(i) + "=" + formBody.encodedValue(i)  + "&");
        }
        if (paramUrlSbuild.length() > 0)
            paramUrlSbuild.deleteCharAt(paramUrlSbuild.length() - 1);
        String sign = HmacSHA1Signature.qianMing(paramUrlSbuild.toString(), AppConfig.S_signValue); // 签名

        //urlencode参数，防止出现中文字符
        String utf8URL = StringHandler.UTFCode(paramUrlSbuild.toString()).replaceAll("%3F", "?").replace("%2B", "+").replaceAll("%26", "&")
                .replaceAll("%3D", "=").replaceAll("%3A", ":").replaceAll("%21", "!").replaceAll("%2F", "/")
                .replaceAll("%2F%2F", "//").replaceAll("", "").replaceAll("%3B", ";").replaceAll("%23", "#");
//        StringBuffer result = new StringBuffer(utf8URL);
//        result.append(AppConfig.G_sign);
//        result.append(sign);

        return sign;
    }

    public static String getPhoneModel() {
        String info = Build.MANUFACTURER + "_" + Build.MODEL + "_" + Build.VERSION.RELEASE + "/";//+AppConfig.S_pluginVersionStr;

        info = info.replaceAll(" ", "/");//接口识别不了空格，会出现签名错误
        return info;
    }
    public static Map<String, String> getFixedParamsForURL() {
        HashMap<String, String> fixedParams = new HashMap<String, String>();
        fixedParams.put(AppConfig.G_agentId, AppConfig.S_agentIdValue);
        fixedParams.put(AppConfig.G_version, AppConfig.S_softVersion);
        fixedParams.put(AppConfig.G_appType, AppConfig.S_appTypeValue);
        String value = "";//FIXME 修改
        fixedParams.put(AppConfig.G_sessionId, value != null ? value : "");
        fixedParams.put(AppConfig.G_android_client, AppConfig.S_android);
        fixedParams.put(AppConfig.G_imei, AppConfig.S_mobileImei != null ? AppConfig.S_mobileImei : "");
        fixedParams.put(AppConfig.G_phoneModel, getPhoneModel());

        if (AppConfig.S_bettoken == null || AppConfig.S_bettoken.equals("")) { // 防止token为空
            AppConfig.S_bettoken = (System.currentTimeMillis() + 3600000) + "";
        }
        fixedParams.put(AppConfig.G_token, AppConfig.S_bettoken);
        fixedParams.put(AppConfig.G_macAdr, AppConfig.S_macAdrs);
        fixedParams.put(AppConfig.G_iv, "2");
        fixedParams.put(AppConfig.G_timeTag, String.valueOf(System.currentTimeMillis()));
        return fixedParams;
    }
    /**
     * 添加公共参数
     *
     * @param oldRequest
     * @return
     */
    public static Request addParam(Request oldRequest) {
        if (oldRequest.method().trim().equals("GET")) {
            HashMap<String, String> appendingParamMap = new HashMap<>();
            //添加返回的数据格式的参数
            if (oldRequest.url().queryParameter("format") == null)
                appendingParamMap.put("format", "json");
            Map<String, String> fixedParamsForURL = getFixedParamsForURL();
            //添加固定参数
            for (int i = 0; i < AppConfig.G_fixedParams.length; i++) {
                if (oldRequest.url().queryParameter(AppConfig.G_fixedParams[i]) == null)
                    appendingParamMap.put(AppConfig.G_fixedParams[i], fixedParamsForURL.get(AppConfig.G_fixedParams[i]));
            }
            //对URL进行utf8并签名
            String sign = tiHuanUTF8url(oldRequest);
            appendingParamMap.put(AppConfig.G_sign, sign);
            Request newRequest = injectParamsIntoUrl(oldRequest.url().newBuilder(), oldRequest.newBuilder(), appendingParamMap);
            return newRequest;
        } else if (oldRequest.method().trim().equals("POST")) {
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oidFormBody = (FormBody) oldRequest.body();
            for (int i = 0;i<oidFormBody.size();i++){
                newFormBody.addEncoded(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
            }

            newFormBody.addEncoded("format", "json");
            Map<String, String> fixedParamsForURL = getFixedParamsForURL();
            //添加固定参数
            for (int i = 0; i < AppConfig.G_fixedParams.length; i++) {
                if (oldRequest.url().queryParameter(AppConfig.G_fixedParams[i]) == null)
                    newFormBody.addEncoded(AppConfig.G_fixedParams[i], fixedParamsForURL.get(AppConfig.G_fixedParams[i]));
            }
            FormBody build = newFormBody.build();
            String sign = tiHuanUTF8url(build);
            FormBody.Builder newFormBodyWithSign = new FormBody.Builder();
            for (int i = 0;i<build.size();i++){
                newFormBodyWithSign.addEncoded(build.encodedName(i),build.encodedValue(i));
            }
            newFormBodyWithSign.addEncoded(AppConfig.G_sign, sign);
            Request newRequest =oldRequest.newBuilder().post(newFormBodyWithSign.build()).build();
            return newRequest;
        }else {
            return oldRequest;
        }
    }
}
