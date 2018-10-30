package ptu.edu.retrofitdemo.http.config;


import java.util.HashMap;
import java.util.HashSet;


public class AppConfig {

    public static String S_serverURL = "https://client.dididapiao.com";// open

    /**
     * 应用类型
     */
    public static final String G_appType = "&appType=";
    public static String S_softVersion = "tzz_android_1.4.9";// 客户端版本号;// 客户端版本号
    public static String S_appTypeValue = "21";// android应用的编号; // 应用类型:   21，我去彩票站
    public static String S_android = "1"; // 客户端类型: 1 Android

    public static String S_macAdrs = "";
    public static String S_bettoken = "";
    public static String S_agentIdValue = "100005"; // 测试渠道号
    public static String S_signValue = "CZkqAm5ElEfsjyeqyooA"; // 渠道密钥
    public static String S_mobileImei = "";


    public static final String G_agentId = "agentId=";
    public static final String G_version = "&version=";
    public static final String G_sign = "&sign=";
    public static final String G_android_client = "&clientType="; // 客户端类型
    public static final String G_type = "&type=";
    public static final String G_sessionId = "&sid=";
    public static final String G_imei = "&imei=";
    public static final String G_macAdr = "&macAdrs="; // 用户微博ID
    public static final String G_phoneModel = "&phoneModel=";


    /**
     * token, 用于区别每次的请求
     */
    public static final String G_token = "&token=";
    /**
     * iv, 用于防止重复购买(与token一起)
     */
    public static final String G_iv = "&iv=";
    public static final String G_timeTag = "&timeTag=";





    /**
     * 接口地址的通用参数数组
     */
    public static final String[] G_fixedParams = {G_agentId, G_sessionId, G_version, G_android_client, G_imei,
            G_appType, G_token, G_iv, G_macAdr, G_timeTag, G_phoneModel};
    /**
     * 接口地址中需要排除的参数数组(分享时)
     */
    public static HashSet<String> G_excludeParamsSet = new HashSet<String>();
    public static HashSet<String> G_wbViewExcludeParamsSet = new HashSet<String>();

    static {
        G_excludeParamsSet.add(G_sessionId);
        G_excludeParamsSet.add(G_imei);
        G_excludeParamsSet.add(G_token);
        G_excludeParamsSet.add(G_macAdr);
        G_excludeParamsSet.add(G_android_client);
        G_excludeParamsSet.add(G_sign);
        G_excludeParamsSet.add(G_timeTag);
        G_excludeParamsSet.add(G_iv);
        G_wbViewExcludeParamsSet.add(G_sessionId);
    }





}
