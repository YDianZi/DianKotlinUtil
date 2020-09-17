package com.dian.mylibrary.model

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 4:18 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 4:18 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
data class HttpResponse<T>(
     val code: Int,
     val msg: String,
     val data: T
)

data class HttpListResponse<T>(
     val datas: List<T>,
     val pageNum: Int,
     val totalCount: Int
)