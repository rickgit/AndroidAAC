package ptu.edu.retrofitdemo.http.callback

abstract class BaseNetCallBack<T>: INetCallBack<T> {
    override fun OnSucce(bean:T){}
    override fun OnError(error:Throwable){}
}