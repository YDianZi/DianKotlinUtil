package com.dian.mylibrary.utils.permisson

import android.Manifest
import androidx.fragment.app.FragmentActivity

/**
 *
 * @Description: 权限请求
 * @Author: Little
 * @CreateDate: 2020/9/12 10:00 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 10:00 AM
 * @UpdateRemark: 弹出一个透明的fragment来进行请求
 * @Version: 1.0
 */
object PermissionX {
    private const val TAG = "InvisibleFragment"
    fun request(
        activity: FragmentActivity,
        vararg permissions: String,
        callBack: permissionCallBack
    ) {
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        val fragment = if (existedFragment != null) {
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        fragment.request(callBack, *permissions)
    }

    fun requestFilePermission(
        activity: FragmentActivity,
        callBack: permissionCallBack
    ) {
        request(activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,callBack =callBack)
    }

    fun requestChooseImgPermission(
        activity: FragmentActivity,
        callBack: permissionCallBack
    ) {
        request(activity,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,callBack =callBack)
    }

    fun requestCameraPermission(
        activity: FragmentActivity,
        callBack: permissionCallBack
    ) {
        request(activity, Manifest.permission.CAMERA,callBack =callBack)
    }
}