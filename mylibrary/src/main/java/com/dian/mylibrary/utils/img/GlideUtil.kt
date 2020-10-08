package com.dian.mylibrary.utils.img

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dian.mylibrary.R

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/8 8:28 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/8 8:28 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object GlideUtil {

    fun loadImgWithUrl(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .asBitmap()
            .load(url)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_default_img))
            .into(imageView)
    }

    fun loadImgWithUri(imageView: ImageView, uri: Uri?) {
        Glide.with(imageView)
            .asBitmap()
            .load(uri)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_default_img))
            .into(imageView)
    }

    fun loadImgWithResId(imageView: ImageView, resId: Int) {
        Glide.with(imageView)
            .asBitmap()
            .load(resId)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_default_img))
            .into(imageView)
    }

    fun loadGifWithUrl(imageView: ImageView, url: String) {
        Glide.with(imageView)
            .asGif()
            .load(url)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_default_img))
            .into(imageView)
    }

    fun loadGifWithUri(imageView: ImageView, uri: Uri?) {
        Glide.with(imageView)
            .asGif()
            .load(uri)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_default_img))
            .into(imageView)
    }

    fun loadGifWithResId(imageView: ImageView, resId: Int) {
        Glide.with(imageView)
            .asGif()
            .load(resId)
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_default_img))
            .into(imageView)
    }


}