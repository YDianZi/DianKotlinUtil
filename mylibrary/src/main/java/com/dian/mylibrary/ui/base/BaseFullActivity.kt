package com.dian.mylibrary.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dian.mylibrary.databinding.ActivityMainBinding
import com.dian.mylibrary.utils.ktx.LogKtx
import kotlinx.android.synthetic.main.activity_base.*

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 1:55 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 1:55 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
abstract class BaseFullActivity<T : ViewDataBinding>(@LayoutRes val resId: Int) : BaseActivity() {
    protected val binding by getDataBinding<T>()

    //获取子类dataBinding  延迟初始化，只有dataBinding第一次被调用的时候这里面的方法才会执行
    private fun <T : ViewDataBinding> getDataBinding(): Lazy<T> {
        return lazy<T> {
            DataBindingUtil.setContentView(this, resId)
        }
    }

    //填充逻辑代码
    open fun initView() {}
    abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

}