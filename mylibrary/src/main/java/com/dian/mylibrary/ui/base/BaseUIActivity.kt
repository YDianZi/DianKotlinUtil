package com.dian.mylibrary.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dian.mylibrary.R
import com.dian.mylibrary.utils.ktx.LogKtx
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.layout_common_title.*

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/10 11:20 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/10 11:20 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
abstract class BaseUIActivity<T : ViewDataBinding>(@LayoutRes val resId: Int) : BaseActivity() {
    lateinit var binding: T

    //获取子类dataBinding  延迟初始化，只有dataBinding第一次被调用的时候这里面的方法才会执行
    private fun <T : ViewDataBinding> getDataBinding(@LayoutRes resId: Int): T {
        return DataBindingUtil.inflate<T>(LayoutInflater.from(this), resId, flContainer, false)
    }

    //填充逻辑代码
    open fun initView() {}
    abstract fun initData()

    //设置标题
    protected fun setTitle(title: String = "测试") {
        baseToolbar.title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        val view = LayoutInflater.from(this).inflate(resId, flContainer, false)
        flContainer.addView(view)
        binding = getDataBinding(resId)
        initView()
        initData()
    }
}