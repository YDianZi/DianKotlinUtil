package com.dian.mylibrary.utils.ktx

import android.content.Context
import android.widget.Toast
import com.dian.mylibrary.BaseMyApp

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 9:39 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 9:39 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

fun String.showToast(context: Context = BaseMyApp.context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

fun Int.showToast(context: Context = BaseMyApp.context, duration: Int = Toast.LENGTH_SHORT) {
    val string = context.resources.getString(this)
    string.showToast(context, duration)
}