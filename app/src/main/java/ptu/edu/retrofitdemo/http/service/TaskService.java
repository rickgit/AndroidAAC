package ptu.edu.retrofitdemo.http.service;



import io.reactivex.Observable;
import ptu.edu.retrofitdemo.http.bean.WeatherBean;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface TaskService {
    @GET("weather_mini")///weather_mini?city=北京
    Observable<String> listSubTasks(@QueryMap Map<String,String> params);
    @GET("weather_mini")///weather_mini?city=北京
    Observable<WeatherBean> listSubTasksAsync(@QueryMap Map<String,String> params);
    @FormUrlEncoded
    @POST("news/list")
    Observable<WeatherBean> getNewsData(@FieldMap Map<String, String> entity);
}