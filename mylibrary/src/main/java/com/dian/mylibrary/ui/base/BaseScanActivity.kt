package com.dian.mylibrary.ui.base

import android.content.Intent
import android.os.Build
import android.view.MotionEvent
import android.view.SurfaceView
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.dian.mylibrary.utils.app.PackageUtil
import com.dian.mylibrary.utils.img.ImgUtil
import com.dian.mylibrary.utils.ktx.L
import com.dian.mylibrary.utils.ktx.showToast
import com.dian.zxing.CaptureHelper
import com.dian.zxing.DecodeFormatManager
import com.dian.zxing.ViewfinderView
import com.dian.zxing.util.CodeUtils
import com.google.zxing.BarcodeFormat
import com.google.zxing.DecodeHintType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/8 4:32 PM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/8 4:32 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
abstract class BaseScanActivity<T : ViewDataBinding>(@LayoutRes val res: Int) :
    BaseUIActivity<T>(res) {
    private lateinit var mCaptureHelper: CaptureHelper

    private lateinit var job: Job
    private lateinit var scope: CoroutineScope
    private val CHOOSE_IMG = 0x863

    abstract fun handResult(result: String)

    //初始化界面
    protected fun init(
        surfaceView: SurfaceView,
        viewfinderView: ViewfinderView,
        ivFlash: ImageView
    ) {
        job = Job()
        scope = CoroutineScope(job)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mCaptureHelper = CaptureHelper(this, surfaceView, viewfinderView, ivFlash)
            mCaptureHelper.apply {
                setOnCaptureCallback {
                    //扫码结果
                    if (it.isNullOrEmpty()) {
                        "识别失败".showToast()
                        return@setOnCaptureCallback true
                    }
                    L.d("扫码结果：$it")
                    handResult(it)
                    return@setOnCaptureCallback true
                }
                onCreate()
                vibrate(true)
                    .fullScreenScan(true) //全屏扫码
                    .supportVerticalCode(true) //支持扫垂直条码，建议有此需求时才使用。
                    .supportLuminanceInvert(true) //是否支持识别反色码（黑白反色的码），增加识别率
                    .continuousScan(false)
            }
        } else {
            "当前设备不支持扫码".showToast()
        }
    }

    /**
     * 选择图片
     */
    protected fun chooseImg() {
        ImgUtil.chooseImg(this, PackageUtil.getPackageName(this), CHOOSE_IMG, mimeType = 1)
    }

    override fun onResume() {
        super.onResume()
        mCaptureHelper.onResume()
    }

    override fun onPause() {
        super.onPause()
        mCaptureHelper.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mCaptureHelper.onDestroy()
        job.cancel()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mCaptureHelper.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    /**
     * 重新启动扫码和解码器
     */
    protected fun restartPreviewAndDecode() {
        mCaptureHelper.restartPreviewAndDecode()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (RESULT_OK == resultCode && CHOOSE_IMG == requestCode) {
            //图片
            val fixDataUrl = ImgUtil.fixDataUrl(data)
            val fileUrl = fixDataUrl?.firstOrNull()

            L.d("decodeQR:runBlocking开启协程，线程${Thread.currentThread().name}")
            scope.launch {
                L.d("decodeQR:识别开始，线程${Thread.currentThread().name}，图片地址=$fileUrl")
                if (fileUrl.isNullOrEmpty()) {
                    "图片错误".showToast()
                    return@launch
                }
                val hints: MutableMap<DecodeHintType, Any> =
                    HashMap()
                //添加可以解析的编码类型
                val parseQRCode = CodeUtils.parseCode(fileUrl)
                L.d("decodeQR:识别结果：$parseQRCode，线程${Thread.currentThread().name}")
                launch(Dispatchers.Main) {
                    if (parseQRCode.isNullOrEmpty()) {
                        "识别失败".showToast()
                        return@launch
                    }
                    handResult(parseQRCode)
                }
            }
            L.d("decodeQR:runBlocking执行完毕，线程${Thread.currentThread().name}")
        }
    }

}