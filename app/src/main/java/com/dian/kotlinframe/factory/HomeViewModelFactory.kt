package com.dian.newlovelyweather.logic.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dian.kotlinframe.data.HomeRepository
import com.dian.newlovelyweather.logic.viewmodel.HomeViewModel

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/2 2:41 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/2 2:41 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class HomeViewModelFactory(
    private val homeRepository: HomeRepository,
    private val type: Int,
    private val pageNum: Int,
    private val pageSize: Int
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository, type, pageNum, pageSize) as T
    }
}