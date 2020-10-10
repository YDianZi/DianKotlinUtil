package com.dian.mylibrary.widget.banner

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.dian.mylibrary.widget.adapter.MyBaseViewHolder
import com.dian.youth.banner.Banner
import com.dian.youth.banner.indicator.CircleIndicator
import com.google.android.material.snackbar.Snackbar

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/10 3:10 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/10 3:10 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object BannerUtil {
    /**
     * 常规banner
     */
    fun <D : ViewDataBinding> normalBanner(
        context: Context?,
        banner: Banner<BannerData,ImageAdapter<D>>,
        @LayoutRes layoutResId: Int,
        datas: List<BannerData>,
        initData: (
            holder: MyBaseViewHolder?,
            data: BannerData?,
            position: Int,
            size: Int
        ) -> Unit,
        click: (data: Any, position: Int) -> Unit
    ) {
        val adapter: ImageAdapter<D> = ImageAdapter(layoutResId, datas, initData)
        banner.setAdapter(adapter)
//            .addBannerLifecycleObserver(this) //添加生命周期观察者
            .setIndicator(CircleIndicator(context)) //设置指示器
            .setOnBannerListener { data: Any, position: Int ->
                click(data, position)
            }
    }
}