package com.dian.mylibrary.utils.img

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/8 8:41 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/8 8:41 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object BitmapUtil {
    fun getBitmapWithUrl(
        imageView: ImageView,
        url: String,
        onGlideLoadBitmapCallback: (Bitmap?) -> Unit
    ) {
        Glide.with(imageView)
            .asBitmap()
            .load(url)
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    onGlideLoadBitmapCallback(resource)
                }
            })
    }
}