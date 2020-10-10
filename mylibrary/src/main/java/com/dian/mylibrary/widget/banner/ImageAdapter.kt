package com.dian.mylibrary.widget.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dian.mylibrary.widget.adapter.MyBaseViewHolder
import com.dian.youth.banner.adapter.BannerAdapter

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/10 3:30 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/10 3:30 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class ImageAdapter<D : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
    private val datas: List<BannerData>,
    private val initData:( holder: MyBaseViewHolder?,
                           data: BannerData?,
                           position: Int,
                           size: Int)->Unit
) : BannerAdapter<BannerData, MyBaseViewHolder>(datas) {
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): MyBaseViewHolder {
        val viewBinding = DataBindingUtil.inflate<D>(
            LayoutInflater.from(parent?.context),
            layoutResId,
            parent,
            false
        )
        return MyBaseViewHolder(viewBinding.root)
    }

    override fun onBindView(
        holder: MyBaseViewHolder?,
        data: BannerData?,
        position: Int,
        size: Int
    ) {
        initData(holder, data, position, size)
    }
}