package ptu.edu.retrofitdemo.permission

abstract class PermissionCallBack {
    abstract fun onSuccess();
    abstract fun onFailure();
    abstract fun on();
}