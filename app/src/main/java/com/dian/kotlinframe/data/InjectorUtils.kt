package com.dian.kotlinframe.data

import com.dian.newlovelyweather.logic.factory.HomeViewModelFactory


/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/2 2:24 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/2 2:24 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object InjectorUtils {
    private fun getHomeRepository(): HomeRepository {
        return HomeRepository.getInstance()
    }

    fun provideHomeViewModelFactory(
        type: Int, pageNum: Int, pageSize: Int
    ): HomeViewModelFactory {
        return HomeViewModelFactory(getHomeRepository(), type, pageNum, pageSize)
    }
}