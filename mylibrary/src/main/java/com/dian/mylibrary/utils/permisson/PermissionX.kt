package com.dian.mylibrary.utils.permisson

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
}