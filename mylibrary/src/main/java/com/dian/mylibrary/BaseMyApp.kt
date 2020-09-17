package com.dian.mylibrary

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy


/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/10 11:18 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/10 11:18 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
open class BaseMyApp : Application() {
    private val activitys = ArrayList<AppCompatActivity>()

    companion object {
        lateinit var context: Context
        lateinit var instance: BaseMyApp
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        instance = this
    }

    open fun getCurrentActivity(): AppCompatActivity? {
        if (activitys.size > 0) {
            return activitys[activitys.size - 1]
        }
        return null
    }

    open fun addActivity(activity: AppCompatActivity) {
        activitys.add(activity)
    }

    open fun removeActivity(activity: AppCompatActivity) {
        activitys.remove(activity)
    }

    fun removeAllActivity() {
        for (activity in activitys) {
            activitys.remove(activity)
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }

    fun exit() {
        //退出应用程序
        removeAllActivity()
    }
}