package ptu.edu.retrofitdemo.viewModel

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ptu.edu.retrofitdemo.http.bean.WeatherBean
import ptu.edu.retrofitdemo.http.callback.BaseNetCallBack
import ptu.edu.retrofitdemo.http.handler.NetHandler

class MainViewModel(var lifecleOwner: LifecycleOwner) :ViewModel(){
    private var liveData:LiveData<WeatherBean> = MutableLiveData()
    fun queryData(){
       NetHandler.handlerQueryData(lifecleOwner, mapOf("city" to "北京"),object : BaseNetCallBack<WeatherBean>(){
           override fun OnSucce(bean: WeatherBean) {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
           }
       })
    }

     override fun onCleared() {
        super.onCleared()
    }
}