package com.dian.lovelyweather.logic

import androidx.lifecycle.liveData
import com.dian.lovelyweather.logic.network.DataNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/8/28 9:48 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/28 9:48 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
open class Repository {
   protected  fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}
