package com.dian.kotlinframe.ui.activity

import android.app.AlertDialog
import com.dian.kotlinframe.R
import com.dian.kotlinframe.databinding.ActivityScanBinding
import com.dian.mylibrary.ui.base.BaseScanActivity
import com.dian.mylibrary.utils.ktx.showToast
import com.dian.mylibrary.utils.permisson.PermissionX
import kotlinx.android.synthetic.main.activity_scan.*

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/8 4:56 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/8 4:56 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class ScanActivity : BaseScanActivity<ActivityScanBinding>(R.layout.activity_scan) {
    override fun initData() {
        init(surfaceView, viewfinderView, ivFlash)
        btChooseImg.setOnClickListener { chooseImg() }
        setTitle("扫码")
    }

    /**
     * 处理结果
     */
    override fun handResult(result: String) {
        result.showToast()
        AlertDialog.Builder(this@ScanActivity)
            .setMessage(result)
            .setPositiveButton("好") { p0, p1 -> //重新启动扫码和解码器
                restartPreviewAndDecode()
            }.show()
    }

}