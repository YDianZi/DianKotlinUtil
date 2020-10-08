package com.dian.kotlinframe.data

import com.dian.kotlinframe.model.News
import com.dian.kotlinframe.net.HomeDataNetWork
import com.dian.mylibrary.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/2 2:33 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/2 2:33 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class HomeRepository : Repository() {
    fun getNews(
        type: Int,
        pageNum: Int,
        pageSize: Int
    ) = fire(Dispatchers.IO) {
        //开启协程
        coroutineScope {
            val newsResponse = async { HomeDataNetWork.newsList(type, pageNum, pageSize) }
            val newsData= newsResponse.await()
            if (newsData.code == 200) {
                //成功
               val news = newsData.data
                Result.success(news)
            } else {
                Result.failure<News>(RuntimeException("failure"))
            }
        }
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: HomeRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: HomeRepository().also { instance = it }
            }
    }
}