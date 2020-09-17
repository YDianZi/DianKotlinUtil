package com.dian.mylibrary.utils.ktx

import android.app.Activity
import android.content.Intent

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 9:26 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 9:26 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
/**
 * 开启activity  泛型实例化reified，必须用在inline内联函数中
 */
inline fun <reified T : Activity> Activity.startActivity(
    params: Intent.() -> Unit,
    requestCode: Int = -1
) {
    val intent = Intent(this, T::class.java)
    params(intent)
    if (requestCode == -1) {
        startActivity(intent)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

