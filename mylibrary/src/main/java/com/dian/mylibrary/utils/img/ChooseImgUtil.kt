package com.dian.mylibrary.utils.img

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.dian.mylibrary.BaseMyApp
import com.dian.mylibrary.R
import com.dian.mylibrary.utils.ktx.L
import com.dian.mylibrary.utils.ktx.showToast
import com.dian.mylibrary.utils.permisson.PermissionX
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import java.util.HashSet

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/7 5:38 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/7 5:38 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object ChooseImgUtil {
    /**
     * 选择相册图片
     * @param mimeType 1:图片  2:gif  0:所有
     */
    fun chooseImg(
        activity: FragmentActivity?,
        minWidth: Int = 0,
        minHeight: Int = 0,
        maxWidth: Int = 0,
        maxHeight: Int = 0,
        maxSize: Int = 0,
        mimeType: Int = 0,
        maxSelect: Int = 1,
        scale: Float = 0.85f,
        takePhoto: Boolean = true,
        packageName: String,
        dirName: String = "demo",
        REQUEST_CODE_CHOOSE: Int
    ) {
        if (packageName.isNullOrEmpty()){
            "包名异常".showToast()
            return
        }
        val filter = when(mimeType){
            2->{
                GifSizeFilter(minWidth, minHeight, maxWidth,maxHeight,maxSize)
            }
            else->{
                ImgSizeFilter(minWidth, minHeight,maxWidth,maxHeight, maxSize)
            }
        }
        val mimeTypes: Set<MimeType> = when (mimeType) {
            1 -> {
                MimeType.ofImage()
            }
            2 -> {
                MimeType.ofGif()
            }
            else -> {
                MimeType.ofAll()
            }
        }
        activity?.let {
            //申请权限
            PermissionX.requestChooseImgPermission(it) { allGranted, deniedList ->
                if (allGranted) {
                    Matisse.from(it)
                        .choose(mimeTypes)
                        .capture(takePhoto)
                        .captureStrategy(CaptureStrategy(Build.VERSION.SDK_INT < 29, "$packageName.fileprovider", dirName))
                        .theme(R.style.Matisse_Dracula)
                        .countable(true)
                        .maxSelectable(maxSelect)
                        .addFilter(filter)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .thumbnailScale(scale)
                        .imageEngine(GlideEngine())
                        .showSingleMediaType(true)
                        .originalEnable(true)
                        .maxOriginalSize(10)
                        .autoHideToolbarOnSingleTap(true)
                        .forResult(REQUEST_CODE_CHOOSE)
                }
            }
        }
    }

    /**
     * 选择相册图片
     * @param mimeType 1:图片  2:gif  0:所有
     */
    fun chooseImg(
        activity: FragmentActivity?,
        fragment: Fragment?,
        minWidth: Int = 0,
        minHeight: Int = 0,
        maxWidth: Int = 0,
        maxHeight: Int = 0,
        maxSize: Int = 0,
        mimeType: Int = 0,
        maxSelect: Int = 1,
        scale: Float = 0.85f,
        takePhoto: Boolean = true,
        packageName: String,
        dirName: String = "demo",
        REQUEST_CODE_CHOOSE: Int
    ) {
        if (packageName.isNullOrEmpty()){
            "包名异常".showToast()
            return
        }
        val filter = when(mimeType){
            2->{
                GifSizeFilter(minWidth, minHeight, maxWidth,maxHeight,maxSize)
            }
            else->{
                ImgSizeFilter(minWidth, minHeight,maxWidth,maxHeight, maxSize)
            }
        }
        val mimeTypes: Set<MimeType> = when (mimeType) {
            1 -> {
                MimeType.ofImage()
            }
            2 -> {
                MimeType.ofGif()
            }
            else -> {
                MimeType.ofAll()
            }
        }
        activity?.let {
            //申请权限
            PermissionX.requestChooseImgPermission(it) { allGranted, deniedList ->
                if (allGranted) {
                    Matisse.from(fragment)
                        .choose(mimeTypes)
                        .capture(takePhoto)
                        .captureStrategy(CaptureStrategy(Build.VERSION.SDK_INT < 29, "$packageName.fileprovider", dirName))
                        .theme(R.style.Matisse_Dracula)
                        .countable(true)
                        .maxSelectable(maxSelect)
                        .addFilter(filter)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .thumbnailScale(scale)
                        .imageEngine(GlideEngine())
                        .showSingleMediaType(true)
                        .originalEnable(true)
                        .maxOriginalSize(10)
                        .autoHideToolbarOnSingleTap(true)
                        .forResult(REQUEST_CODE_CHOOSE)
                }
            }
        }
    }


    fun fixDataUri(data: Intent?): List<Uri>? {
        if (data == null) return null
        return Matisse.obtainResult(data)
    }

    fun fixDataUrl(data: Intent?): List<String>? {
        if (data == null) return null
        return Matisse.obtainPathResult(data)
    }
}