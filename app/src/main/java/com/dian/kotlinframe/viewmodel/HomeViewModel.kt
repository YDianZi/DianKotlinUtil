package com.dian.newlovelyweather.logic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dian.kotlinframe.data.HomeRepository
import com.dian.kotlinframe.model.News
import retrofit2.http.Query

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/8/28 3:59 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/28 3:59 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class HomeViewModel(
    private val homeRepository: HomeRepository,
    val type: Int,
    val pageNum: Int,
    val pageSize: Int
) : ViewModel() {

    private val requestNewsLiveData = MutableLiveData<News.RequestNews>()

    fun getNews(
        type: Int,
        pageNum: Int,
        pageSize: Int
    ) {
        requestNewsLiveData.value = News.RequestNews(type, pageNum, pageSize)
    }

    val newsLiveData = Transformations.switchMap(requestNewsLiveData) {
        homeRepository.getNews(it.type, it.pageNum, it.pageSize)
    }

}