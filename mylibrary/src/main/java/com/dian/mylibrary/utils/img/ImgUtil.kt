package com.dian.mylibrary.utils.img

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.dian.mylibrary.R
import com.dian.mylibrary.ui.widget.LoadingView
import com.dian.mylibrary.utils.ktx.L
import com.dian.mylibrary.utils.ktx.showToast
import com.dian.mylibrary.utils.permisson.PermissionX
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import top.zibin.luban.CompressionPredicate
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import top.zibin.luban.OnRenameListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


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
object ImgUtil {
    /**
     * 选择相册图片
     * @param mimeType 1:图片  2:gif  0:所有
     */
    fun chooseImg(
        activity: FragmentActivity?,
        packageName: String,
        REQUEST_CODE_CHOOSE: Int,
        minWidth: Int = 0,
        minHeight: Int = 0,
        maxWidth: Int = 0,
        maxHeight: Int = 0,
        maxSize: Int = 0,
        mimeType: Int = 0,
        maxSelect: Int = 1,
        scale: Float = 0.85f,
        takePhoto: Boolean = true,
        dirName: String = "demo"
    ) {
        if (packageName.isNullOrEmpty()) {
            "包名异常".showToast()
            return
        }
        val filter = when (mimeType) {
            2 -> {
                GifSizeFilter(minWidth, minHeight, maxWidth, maxHeight, maxSize)
            }
            else -> {
                ImgSizeFilter(minWidth, minHeight, maxWidth, maxHeight, maxSize)
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
                        .captureStrategy(
                            CaptureStrategy(
                                Build.VERSION.SDK_INT < 29,
                                "$packageName.fileprovider",
                                dirName
                            )
                        )
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
        packageName: String,
        REQUEST_CODE_CHOOSE: Int,
        minWidth: Int = 0,
        minHeight: Int = 0,
        maxWidth: Int = 0,
        maxHeight: Int = 0,
        maxSize: Int = 0,
        mimeType: Int = 0,
        maxSelect: Int = 1,
        scale: Float = 0.85f,
        takePhoto: Boolean = true,
        dirName: String = "demo"
    ) {
        if (packageName.isNullOrEmpty()) {
            "包名异常".showToast()
            return
        }
        val filter = when (mimeType) {
            2 -> {
                GifSizeFilter(minWidth, minHeight, maxWidth, maxHeight, maxSize)
            }
            else -> {
                ImgSizeFilter(minWidth, minHeight, maxWidth, maxHeight, maxSize)
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
                        .captureStrategy(
                            CaptureStrategy(
                                Build.VERSION.SDK_INT < 29,
                                "$packageName.fileprovider",
                                dirName
                            )
                        )
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
     * 从选择图片返回的数据中获取图片路径
     */
    fun fixDataUri(data: Intent?): List<Uri>? {
        if (data == null) return null
        return Matisse.obtainResult(data)
    }

    /**
     *  从选择图片返回的数据中获取图片路径
     */
    fun fixDataUrl(data: Intent?): List<String>? {
        if (data == null) return null
        return Matisse.obtainPathResult(data)
    }

    /**
     * 裁剪选择的图片
     */
    const val REQUEST_CROP = UCrop.REQUEST_CROP
    fun cropImgWithUri(context: FragmentActivity?, sourceUri: Uri?) {
        if (sourceUri == null){
            "图片地址不正确".showToast()
            return
        }
        context?.let {
            val destinationUri: Uri =
                Uri.fromFile(File(context.cacheDir, "destinationFileName.jpg"))
            UCrop.of(sourceUri, destinationUri)
                .start(it)
        }
    }

    /**
     * 裁剪图片解析
     */
    fun handleCropResult(result: Intent): Uri? {
        return UCrop.getOutput(result)
    }

    fun compressImg(context: FragmentActivity?, file: File?, success: (File) -> Unit) {
        if (file == null) {
            "图片地址不正确".showToast()
            return
        }
        context?.let {
            val loadingView = LoadingView(context)
            Luban.with(context)
                .load(file)
                .setTargetDir(context.cacheDir.absolutePath)
                .ignoreBy(100)
                .setFocusAlpha(false)
                .filter(CompressionPredicate { path ->
                    !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"))
                })
                .setCompressListener(object : OnCompressListener {
                    override fun onStart() {
                        loadingView.show()
                    }
                    override fun onSuccess(file: File) {
                        loadingView.dismiss()
                        success(file)
                    }
                    override fun onError(e: Throwable) {
                        loadingView.dismiss()
                    }
                }).launch()
        }
    }
}