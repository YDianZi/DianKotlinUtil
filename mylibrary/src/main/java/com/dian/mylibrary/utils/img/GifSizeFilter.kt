package com.dian.mylibrary.utils.img

import android.content.Context
import android.widget.Toast
import com.dian.mylibrary.R
import com.dian.mylibrary.utils.ktx.showToast
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.IncapableCause
import com.zhihu.matisse.internal.entity.Item
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils
import java.util.*
import kotlin.collections.HashSet

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/7 5:47 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/7 5:47 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class GifSizeFilter(
    minWidth: Int,
    minHeight: Int,
    maxWidth: Int,
    maxHeight: Int,
    maxSize: Int
) : Filter() {
    private var mMinWidth = 0
    private var mMinHeight = 0
    private var mMaxWidth = 0
    private var mMaxHeight = 0
    private var mMaxSize = 0

    init {
        mMinWidth = minWidth
        mMinHeight = minHeight
        mMaxWidth = maxWidth
        mMaxHeight = maxHeight
        mMaxSize = maxSize
    }

    override fun filter(context: Context?, item: Item?): IncapableCause? {
        if (mMaxWidth == 0 || mMaxHeight == 0||mMinWidth == 0 || mMinHeight == 0 || mMaxSize == 0) {
            //无需验证
            return null
        }
        if (!needFiltering(context, item)) return null
        val size = PhotoMetadataUtils.getBitmapBound(context?.contentResolver, item?.contentUri)
        val bo = item?.size ?: 0 > mMaxSize * K * K
        if (size.x > mMaxWidth || size.y > mMaxHeight || size.x < mMinWidth || size.y < mMinHeight || bo) {
            //不合法
            val tips =
                "图片合法宽高：最小宽=$mMinWidth,最小高=$mMinHeight,最大宽=$mMaxWidth,最大高=$mMaxHeight；最大限制=$mMaxSize M,"
            tips.showToast(duration = Toast.LENGTH_LONG)
            return IncapableCause(tips)
        }
        return null
    }

    override fun constraintTypes(): MutableSet<MimeType> {
        return MimeType.ofGif()
    }
}