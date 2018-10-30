package ptu.edu.retrofitdemo.permission

import android.Manifest
import android.app.Activity
import android.support.constraint.Constraints.TAG
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import io.reactivex.internal.util.NotificationLite.accept

class PermissionMgr{
    fun requestPermissions(  act:FragmentActivity) {
        var rxPermission =   RxPermissions(act);
        rxPermission.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS)
                .subscribe( {
                        @Override
                        if (it.granted) {
                            // 用户已经同意该权限
                            Log.d(TAG, it.name + " is granted.");
                        } else if (it.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d(TAG, it.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d(TAG, it.name + " is denied.");
                        }
                });


    }
}