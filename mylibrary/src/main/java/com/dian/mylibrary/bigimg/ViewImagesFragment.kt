package com.dian.mylibrary.bigimg

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.dian.mylibrary.R
import com.dian.mylibrary.databinding.FragmentViewImageBinding
import com.dian.mylibrary.ui.base.BaseFragment
import com.dian.mylibrary.utils.img.GlideUtil
import kotlinx.android.synthetic.main.fragment_view_image.*

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/12 9:14 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/12 9:14 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class ViewImagesFragment : BaseFragment<FragmentViewImageBinding>(R.layout.fragment_view_image) {
    private var image: String? = null
    private var image1 = 0

    companion object {
        val IMAGE_DATA_EXTRA = "image"
        val IMAGE_DATA_EXTRA1 = "image1"

        @JvmStatic
        fun newInstance(imageUrl: String?): ViewImagesFragment? {
            val f = ViewImagesFragment()
            val args = Bundle()
            args.putCharSequence(IMAGE_DATA_EXTRA, imageUrl)
            f.arguments = args
            return f
        }

        @JvmStatic
        fun newInstance(imageUrl: Int?): ViewImagesFragment? {
            val f = ViewImagesFragment()
            val args = Bundle()
            args.putInt(IMAGE_DATA_EXTRA1, imageUrl!!)
            f.arguments = args
            return f
        }
    }

    override fun initData() {
        image = if (arguments != null) arguments!!.getString(
            IMAGE_DATA_EXTRA
        ) else ""
        image1 = if (arguments != null) arguments!!.getInt(
            IMAGE_DATA_EXTRA1
        ) else 0
        if (!TextUtils.isEmpty(image)) {
            GlideUtil.loadImgWithUrl(imageView, image ?: "")
        } else {
            imageView.setImageResource(image1)
        }
        imageView.setOnViewTapListener { view, x, y -> getActivity()!!.finish() }
    }

    fun cancelWork() {
        imageView.setImageDrawable(null)
    }
}