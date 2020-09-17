package com.dian.mylibrary.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.dian.mylibrary.ui.widget.LoadingView
import org.greenrobot.eventbus.EventBus

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 2:49 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 2:49 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val resId: Int) : Fragment() {
    lateinit var binding: T
    lateinit var activity: BaseActivity

    private fun <T : ViewDataBinding> getDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T {
        return DataBindingUtil.inflate<T>(inflater, resId, container, false)
    }


    //填充逻辑代码
    open fun initView() {}
    abstract fun initData()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getDataBinding(inflater, container)
        activity = getActivity() as BaseActivity
        initView()
        initData()
        return binding.root
    }


    /**
     * 是否可以接收EventBus事件，默认不可以，重写此方法返回true，即可接收事件
     *
     * @return
     */
    protected fun canReceiveEvent(): Boolean = false
    private fun registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }

    private fun unRegisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this)
    }

    fun showLoading() {
        activity.let {
            activity.showLoading()
        }
    }

    fun hideLoading() {
        activity.let {
            activity.hideLoading()
        }
    }

}