package com.dian.mylibrary.ui.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.dian.mylibrary.model.HttpListResponse
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 3:11 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 3:11 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
abstract class BaseRefreshFragment<T : ViewDataBinding, E>(@LayoutRes val resIda: Int) :
    BaseFragment<T>(resIda) {
    private var pageNo = 1
    private val pageSize = 20
    abstract fun getSmartRefreshView(): SmartRefreshLayout
    abstract fun getData(pageNo: Int, pageSize: Int)
    abstract fun setData(pageNo: Int, datas: List<E>)
    override fun initData() {
        val smartRefreshLayout = getSmartRefreshView()
        smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                pageNo++
                getData(pageNo, pageSize)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                pageNo = 1
                getData(pageNo, pageSize)
            }
        })
    }

    fun handData(httpListResponse: HttpListResponse<E>) {
        if (pageNo.plus(pageSize) >= httpListResponse.totalCount) {
            //没有更多了
            getSmartRefreshView().finishLoadMoreWithNoMoreData()
        }
        setData(httpListResponse.pageNum, httpListResponse.datas)
    }
}