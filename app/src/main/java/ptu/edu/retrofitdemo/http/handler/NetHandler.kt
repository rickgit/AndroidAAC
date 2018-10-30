package ptu.edu.retrofitdemo.http.handler

import android.arch.lifecycle.LifecycleOwner
import com.alibaba.fastjson.JSONException
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ptu.edu.retrofitdemo.http.ServiceGenerator
import ptu.edu.retrofitdemo.http.bean.WeatherBean
import ptu.edu.retrofitdemo.http.callback.BaseNetCallBack
import ptu.edu.retrofitdemo.http.service.TaskService

class NetHandler {
    //静态方法
    companion object {
        fun handlerQueryData(lifecleOwner: LifecycleOwner, paramsMap: Map<String, String>, netCallBack: BaseNetCallBack<WeatherBean>) {
            ServiceGenerator.createService(TaskService::class.java)
                    .listSubTasksAsync(paramsMap)
                    .observeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.from(object : Executor {
//                    override fun execute(command: Runnable) {
//                        runOnUiThread(command)
//                    }
//                }))
                    .subscribeOn(Schedulers.io())//IO线程加载数据
                    .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                    .`as`(AutoDispose.autoDisposable<WeatherBean>(AndroidLifecycleScopeProvider.from(lifecleOwner)))
                    .subscribe({
                        println(it)
                        netCallBack.OnSucce(it)
                    }, {
                        if (it is JSONException)
                            println("complete" + it.message)
                        netCallBack.OnError(it)
                    }, {
                        println("complete+关闭对话框")
                    })
        }
    }


}