package com.dian.mylibrary.utils.device

import android.content.Context
import com.dian.mylibrary.BaseMyApp

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/11/9 2:19 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/11/9 2:19 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object DensityUtils {
    /**
     * dp 转化为 px
     *
     * @param context
     * @param dpValue
     * @return
     */
    fun dp2px(dpValue: Float, context: Context = BaseMyApp.context): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px 转化为 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    fun px2dp(pxValue: Float, context: Context = BaseMyApp.context): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * convert px to its equivalent sp
     *
     * 将px转换为sp
     */
    fun px2sp(pxValue: Float,context: Context = BaseMyApp.context): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }


    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    fun sp2px(spValue: Float,context: Context = BaseMyApp.context): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}