package com.dian.kotlinframe.ui.fragment

import com.dian.kotlinframe.R
import com.dian.kotlinframe.databinding.FragmentZhichanHotBinding
import com.dian.mylibrary.ui.base.BaseFragment
import com.dian.mylibrary.ui.base.BaseListFragment
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 3:36 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 3:36 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class ZhiChanHotFragment :
    BaseListFragment<FragmentZhichanHotBinding, String>(R.layout.fragment_zhichan_hot) {
    override fun initData() {

    }

    override fun getSmartRefreshView(): SmartRefreshLayout = binding.smartRefreshLayout

    override fun getData(pageNo: Int, pageSize: Int) {
    }

    override fun setData(pageNo: Int, datas: List<String>) {
    }

}
