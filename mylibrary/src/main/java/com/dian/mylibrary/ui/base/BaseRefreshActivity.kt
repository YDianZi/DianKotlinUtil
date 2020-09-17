package com.dian.mylibrary.ui.base

import android.os.Bundle
import com.scwang.smart.refresh.layout.SmartRefreshLayout

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
abstract class BaseRefreshActivity : BaseActivity() {

    /**
     * 获取刷新控件
     */
    abstract fun getSmartRefreshView():SmartRefreshLayout

}