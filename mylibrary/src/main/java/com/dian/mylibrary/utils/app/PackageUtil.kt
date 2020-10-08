package com.dian.mylibrary.utils.app

import android.content.Context

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/7 8:29 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/7 8:29 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object PackageUtil {
    fun getPackageName(context: Context?): String {
        return context?.packageName?:""
    }
}