package com.dian.kotlinframe.model

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 4:32 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 4:32 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
data class News(
    val id: Int,
    val type: Int,//0热点   1服务
    val top: Int,
    val homePage: Boolean,
    val title: String,
    val intro: String,
    val masterMap: String,
    val body: String,
    val releaseTime: String,
    val createTime: String,
    val updateTime: String,
    val clickNum: Int
){
    data class RequestNews( val type: Int,
                            val pageNum: Int,
                            val pageSize: Int)
}