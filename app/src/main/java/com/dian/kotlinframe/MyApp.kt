package com.dian.kotlinframe

import com.dian.mylibrary.BaseMyApp
import com.dian.mylibrary.utils.ktx.L

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 9:20 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 9:20 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class MyApp : BaseMyApp() {

    override fun onCreate() {
        super.onCreate()
        L.d("MyApp:1")
        L.init(true)
        L.d("MyApp:2")
        L.init(false)
        L.d("MyApp:3")
    }
}