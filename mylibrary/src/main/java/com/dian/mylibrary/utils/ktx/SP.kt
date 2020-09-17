package com.dian.mylibrary.utils.ktx

import android.content.Context
import androidx.core.content.edit
import com.dian.mylibrary.BaseMyApp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/14 4:49 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/14 4:49 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object SP {
    private val name = "data"
    fun <B> putData(
        context: Context = BaseMyApp.context,
        commit: Boolean = false,
        vararg pair: Pair<String, B>
    ) {
        context.getSharedPreferences(name, Context.MODE_PRIVATE).edit(commit) {
            for ((key, value) in pair) {
                when (value) {
                    is Int -> putInt(key, value)
                    is String -> putString(key, value)
                    else -> {
                        val gson = Gson()
                        val result = gson.toJson(value)
                        putString(key, result)
                    }
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getData(context: Context = BaseMyApp.context, key: String, defaultValue: T): T {
        val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        with(sharedPreferences) {
            val result = when (defaultValue) {
                is Int -> getInt(key, defaultValue)
                is String -> getString(key, defaultValue)
                else -> {
                    val resultStr = getString(key, "")
                    val type = object : TypeToken<T>() {}.type
                    return Gson().fromJson(resultStr, type)
                }
            }
            return result as T
        }
    }
}