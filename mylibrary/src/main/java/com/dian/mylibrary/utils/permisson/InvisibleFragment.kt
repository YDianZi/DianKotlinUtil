package com.dian.mylibrary.utils.permisson

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import com.dian.mylibrary.utils.ktx.showToast

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 10:05 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 10:05 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
//给函数类型的回调变量起个别名
typealias permissionCallBack = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {
    //定义一个函数类型的回调变量 (Boolean, List<String>) -> Unit
    private var callBack: permissionCallBack? = null

    fun request(cb: permissionCallBack, vararg permissions: String) {
        callBack = cb
        requestPermissions(permissions, 1010)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //权限请求回来
        if (requestCode == 1010) {
            val deniedList = ArrayList<String>()
            //判断权限是否同意
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            if (!allGranted) {
                "未同意全部权限".showToast()
            }
            callBack?.let { it(allGranted, deniedList) }
        }
    }
}