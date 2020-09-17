package com.dian.lovelyweather.logic.network

import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *
 * @Description: 网络请求集中处理
 * @Author: Little
 * @CreateDate: 2020/8/27 6:07 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/27 6:07 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
open class DataNetWork {
    protected suspend fun <T> Call<T>.await(): T {
        //协程
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        val gson = Gson()
                        val bodyes = gson.toJson(body)
                        Log.d("showInfo", "$bodyes")
                        it.resume(body)
                    } else it.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }
}