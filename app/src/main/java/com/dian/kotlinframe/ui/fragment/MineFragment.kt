package com.dian.kotlinframe.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dian.kotlinframe.R
import com.dian.kotlinframe.databinding.FragmentMineBinding
import com.dian.mylibrary.ui.base.BaseFragment
import com.dian.mylibrary.utils.app.PackageUtil
import com.dian.mylibrary.utils.img.ChooseImgUtil
import com.dian.mylibrary.utils.img.GlideUtil
import com.dian.mylibrary.utils.ktx.L
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/12 1:38 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/12 1:38 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class MineFragment:BaseFragment<FragmentMineBinding>(R.layout.fragment_mine) {
    override fun initData() {
        btChoose.setOnClickListener {
            ChooseImgUtil.chooseImg(
                getActivity(),
                this,
                minWidth =0,
                minHeight = 0,
                maxWidth = 0,
                maxHeight = 0,
                maxSize = 0,
                packageName = PackageUtil.getPackageName(getActivity()),
                dirName = "demo",
                REQUEST_CODE_CHOOSE = 10101
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        L.d("OnActivityResult ")
        if (resultCode == Activity.RESULT_OK && requestCode == 10101) {
            val fixDataUrl = ChooseImgUtil.fixDataUrl(data)
            val fixDataUri = ChooseImgUtil.fixDataUri(data)
            L.d("fixDataUrl=$fixDataUrl")
            GlideUtil.loadImgWithUri(ivImg, fixDataUri?.first() ?: Uri.parse(""))
        }
    }
}