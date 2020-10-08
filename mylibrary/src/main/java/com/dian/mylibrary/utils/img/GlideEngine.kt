package com.dian.mylibrary.utils.img

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.dian.mylibrary.R
import com.zhihu.matisse.engine.ImageEngine

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/7 7:21 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/7 7:21 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
internal  class GlideEngine : ImageEngine {
    override fun loadImage(
        context: Context?,
        resizeX: Int,
        resizeY: Int,
        imageView: ImageView?,
        uri: Uri?
    ) {
        imageView?.let {
            Glide.with(imageView)
                .load(uri)
                .apply(
                    RequestOptions()
                        .override(resizeX, resizeY)
                        .priority(Priority.HIGH)
                        .fitCenter()
                        .placeholder(R.drawable.ic_default_img)
                )
                .into(imageView)
        }
    }

    override fun loadGifImage(
        context: Context?,
        resizeX: Int,
        resizeY: Int,
        imageView: ImageView?,
        uri: Uri?
    ) {
        imageView?.let {
            Glide.with(imageView)
                .asGif()
                .load(uri)
                .apply(
                    RequestOptions()
                        .override(resizeX, resizeY)
                        .priority(Priority.HIGH)
                        .fitCenter()
                )
                .into(imageView)
        }
    }

    override fun supportAnimatedGif(): Boolean {
        return true
    }

    override fun loadGifThumbnail(
        context: Context?,
        resize: Int,
        placeholder: Drawable?,
        imageView: ImageView?,
        uri: Uri?
    ) {
        imageView?.let {
            Glide.with(imageView)
                .asBitmap() // some .jpeg files are actually gif
                .load(uri)
                .apply(
                    RequestOptions()
                        .override(resize, resize)
                        .placeholder(placeholder)
                        .centerCrop()
                )
                .into(imageView)
        }
    }

    override fun loadThumbnail(
        context: Context?,
        resize: Int,
        placeholder: Drawable?,
        imageView: ImageView?,
        uri: Uri?
    ) {
        imageView?.let {
            Glide.with(imageView)
                .asBitmap() // some .jpeg files are actually gif
                .load(uri)
                .apply(
                    RequestOptions()
                        .override(resize, resize)
                        .placeholder(placeholder)
                        .centerCrop()
                )
                .into(imageView)
        }
    }

}