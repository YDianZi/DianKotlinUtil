package com.dian.mylibrary.utils.device

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.DisplayMetrics
import com.dian.mylibrary.BaseMyApp
import com.dian.mylibrary.utils.ktx.SP
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/7 8:47 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/7 8:47 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object DeviceUtil {
    /**
     * 获取设备宽度（px）
     *
     * @return
     */
    fun deviceWidth(context: Context = BaseMyApp.context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 获取设备高度（px）
     *
     * @return
     */
    fun deviceHeight(context: Activity): Int {
        val dm = DisplayMetrics()
        context.windowManager.defaultDisplay.getRealMetrics(dm)
        val dwidth = dm.widthPixels
        return dm.heightPixels
    }


    /**
     * 获取状态栏高度——方法4
     * 状态栏高度 = 屏幕高度 - 应用区高度
     * *注意*该方法不能在初始化的时候用
     */
    fun contentHeight(context: Activity): Int {
        //屏幕
        val dm = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(dm)
        //应用区域
        val outRect1 = Rect()
        context.window.decorView.getWindowVisibleDisplayFrame(outRect1)
        return outRect1.height()
    }


    fun deviceDialogWidth(context: Activity): Int {
        return context.windowManager.defaultDisplay.height
    }

    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    fun getStatusBarHeight(context: Context = BaseMyApp.context): Int {
        var result = 0
        //获取状态栏高度的资源id
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    /**
     * 获取设备的唯一标识，deviceId
     *
     * @return
     */
    fun getDeviceId(context: Context = BaseMyApp.context): String? {
        var deviceId: String? = SP.getData("deviceId","")
        if (TextUtils.isEmpty(deviceId)) {
            @SuppressLint("HardwareIds") val androidId =
                Settings.Secure.getString(
                    context.contentResolver, Settings.Secure.ANDROID_ID
                )
            //            deviceId = EncryptUtils.encryptMD5ToString(androidId);
            deviceId = androidId
        }
        return deviceId
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    fun getPhoneBrand(): String? {
        return Build.BRAND
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    fun getPhoneModel(): String? {
        return Build.MODEL
    }

    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return
     */
    fun getBuildLevel(): Int {
        return Build.VERSION.SDK_INT
    }

    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return
     */
    fun getBuildVersion(): String? {
        return Build.VERSION.RELEASE
    }

    /**
     * 判断设备是否root
     *
     * @return
     */
    fun isRoot(): Boolean {
        val binPath = "/system/bin/su"
        val xBinPath = "/system/xbin/su"
        if (File(binPath).exists() && isExecutable(binPath)) return true
        return if (File(xBinPath).exists() && isExecutable(xBinPath)) true else false
    }

    private fun isExecutable(filePath: String): Boolean {
        var p: Process? = null
        try {
            p = Runtime.getRuntime().exec("ls -l $filePath")
            // 获取返回内容
            val `in` = BufferedReader(
                InputStreamReader(
                    p.inputStream
                )
            )
            val str = `in`.readLine()
            if (str != null && str.length >= 4) {
                val flag = str[3]
                if (flag == 's' || flag == 'x') return true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            p?.destroy()
        }
        return false
    }

}