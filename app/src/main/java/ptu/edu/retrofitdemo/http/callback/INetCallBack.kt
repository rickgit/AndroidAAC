package ptu.edu.retrofitdemo.http.callback


interface INetCallBack<T>{
    fun OnSucce(bean:T)
    fun OnError(error:Throwable)
}