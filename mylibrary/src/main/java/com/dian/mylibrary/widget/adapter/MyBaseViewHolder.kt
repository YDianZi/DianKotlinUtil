package com.dian.mylibrary.widget.adapter

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dian.mylibrary.utils.img.GlideUtil

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/10 4:12 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/10 4:12 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class MyBaseViewHolder(v:View): BaseViewHolder(v) {
     fun loadImg(viewId: Int, url:String): BaseViewHolder {
         GlideUtil.loadImgWithUrl(getView(viewId),url)
        return this
    }
    fun loadImg(imageView: ImageView, url:String): BaseViewHolder {
        GlideUtil.loadImgWithUrl(imageView,url)
        return this
    }
}