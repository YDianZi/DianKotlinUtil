package com.dian.mylibrary.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dian.mylibrary.BaseMyApp
import com.dian.mylibrary.ui.widget.LoadingView
import kotlinx.android.synthetic.main.activity_base.*
import org.greenrobot.eventbus.EventBus

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 8:59 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 8:59 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
abstract class BaseActivity : AppCompatActivity() {
    private val loadingView by lazy {
        LoadingView(this)
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
        loadingView.show()
    }

    fun hideLoading() {
        loadingView.dismiss()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseMyApp.instance.addActivity(this)
        if (canReceiveEvent()) {
            registerEventBus()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        BaseMyApp.instance.removeActivity(this)
        if (canReceiveEvent()) {
            unRegisterEventBus()
        }
    }
}