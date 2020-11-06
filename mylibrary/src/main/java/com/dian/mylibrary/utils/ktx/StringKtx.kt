package com.dian.mylibrary.utils.ktx

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 9:35 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 9:35 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

/**
 * json string 转 obj
 */
fun <T> String.jsonToObj(clazz: Class<T>): T {
    return Gson().fromJson(this, clazz)
}

/**
 * json string 转 list
 */
fun <T> String.jsonToList(): List<T> {
    if (this.isNullOrEmpty())return ArrayList<T>()
    return Gson().fromJson(this, object : TypeToken<List<T>>() {}.type)
}

