package com.dian.kotlinframe.net

import com.dian.mylibrary.BuildConfig
import com.dian.mylibrary.network.DataNetWork
import com.dian.mylibrary.network.ServiceCreator
import retrofit2.http.Query

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 4:29 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 4:29 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object HomeDataNetWork : DataNetWork() {
    private val homeService = ServiceCreator.create<HomeService>()

    // 朱耿桥:资讯查询
    suspend fun newsList(type: Int, pageNum: Int, pageSize: Int) =
        homeService.newsList(type, pageNum, pageSize).await()
}