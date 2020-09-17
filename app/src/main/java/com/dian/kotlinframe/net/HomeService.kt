package com.dian.kotlinframe.net

import com.dian.kotlinframe.model.News
import com.dian.mylibrary.model.HttpResponse
import retrofit2.Call
import retrofit2.http.GET
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
interface HomeService {
    /**
     * 朱耿桥:资讯查询
     *
     * @param type     资讯类型0热点  1服务
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GET("external/web/information/query")
    fun newsList(
        @Query("type") type: Int,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<HttpResponse<News>>


}