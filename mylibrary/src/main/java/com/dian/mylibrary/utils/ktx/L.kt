package com.dian.mylibrary.utils.ktx

import com.dian.mylibrary.BuildConfig
import com.google.gson.Gson
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import java.util.*

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 9:34 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 9:34 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object L {
    init {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            .methodCount(0) // (Optional) How many method line to show. Default 2
            .methodOffset(7) // (Optional) Hides internal method calls up to offset. Default 5
            .tag(BuildConfig.LIBRARY_PACKAGE_NAME) // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    fun d(msg: String) {
        if ("debug".equals(BuildConfig.BUILD_TYPE)) {
            Logger.d(msg)
        }
    }

    fun json(obj: Objects) {
        if ("debug".equals(BuildConfig.BUILD_TYPE)) {
            val msg = Gson().toJson(obj)
            d(msg)
        }
    }
}