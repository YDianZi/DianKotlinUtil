package com.dian.kotlinframe.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.dian.kotlinframe.R
import com.dian.kotlinframe.databinding.FragmentMineBinding
import com.dian.kotlinframe.ui.activity.ScanActivity
import com.dian.mylibrary.ui.base.BaseFragment
import com.dian.mylibrary.utils.app.PackageUtil
import com.dian.mylibrary.utils.img.ImgUtil
import com.dian.mylibrary.utils.img.GlideUtil
import com.dian.mylibrary.utils.ktx.L
import com.dian.mylibrary.utils.ktx.myStartActivity
import com.dian.mylibrary.utils.permisson.PermissionX
import kotlinx.android.synthetic.main.fragment_mine.*
import java.io.File

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
class MineFragment : BaseFragment<FragmentMineBinding>(R.layout.fragment_mine) {
    override fun initData() {
        btChoose.setOnClickListener {
            ImgUtil.chooseImg(
                getActivity(),
                this,
                PackageUtil.getPackageName(getActivity()),
                10101,
                minWidth = 0,
                minHeight = 0,
                maxWidth = 0,
                maxHeight = 0,
                maxSize = 0,
                dirName = "demo"
            )
        }

        btScan.setOnClickListener {
            PermissionX.requestCameraPermission(activity) { a, b ->
                if (a) {
                    activity.myStartActivity<ScanActivity>()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        L.d("OnActivityResult ")
        if (resultCode == Activity.RESULT_OK && requestCode == 10101) {
            ImgUtil.run {
                val fixDataUrl = fixDataUrl(data)
                val fixDataUri = fixDataUri(data)
                L.d("fixDataUrl=$fixDataUrl")
                val uri = fixDataUri?.first()
                val url = fixDataUrl?.first()
                //压缩
                compressImg(getActivity(), File(url)) {
                    GlideUtil.loadImgWithUrl(ivImg,it.absolutePath )
                }
                //裁剪
//                cropImgWithUri(getActivity(),uri)
            }
        } else if (requestCode == ImgUtil.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
            //裁剪照片回来
            data?.let {
                GlideUtil.loadImgWithUri(ivImg, ImgUtil.handleCropResult(it))
            }
        }
    }
}