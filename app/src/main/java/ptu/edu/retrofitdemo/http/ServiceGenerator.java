package ptu.edu.retrofitdemo.http;


import android.os.Build;

import com.safframework.http.interceptor.LoggingInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ptu.edu.retrofitdemo.http.config.AppConfig;
import ptu.edu.retrofitdemo.http.handler.ParamHandler;
import ptu.edu.retrofitdemo.http.handler.StringHandler;
import ptu.edu.retrofitdemo.http.sign.HmacSHA1Signature;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

public class ServiceGenerator {
    //https://api.github.com/
    //http://wthrcdn.etouch.cn/weather_mini?city=北京
    private static final String BASE_URL = "http://wthrcdn.etouch.cn/";//

    //测试环境
    static LoggingInterceptor loggingInterceptor = new LoggingInterceptor.Builder()
            .loggable(true) // TODO: 发布到生产环境需要改成false
            .request()
            .requestTag("Request")
            .response()
            .responseTag("Response")
            //.hideVerticalLine()// 隐藏竖线边框
            .build();
    static OkHttpClient apiOkhttpClient = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    request = ParamHandler.addParam(request);
                    Response response = chain.proceed(request);
                    return response;
                }
            }).build();


    private static Retrofit apiRetrofit =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(apiOkhttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .build();

    public static <S> S createService(Class<S> serviceClass) {
        return apiRetrofit.create(serviceClass);
    }

}