package com.dian.mylibrary.ui.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.SurfaceView
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dian.mylibrary.R
import com.dian.mylibrary.utils.app.PackageUtil
import com.dian.mylibrary.utils.img.ImgUtil
import com.dian.mylibrary.utils.ktx.L
import com.dian.mylibrary.utils.ktx.showToast
import com.dian.mylibrary.utils.permisson.PermissionX
import com.king.zxing.CaptureHelper
import com.king.zxing.OnCaptureCallback
import com.king.zxing.ViewfinderView
import com.king.zxing.util.CodeUtils
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.layout_common_title.*
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
abstract class BaseScanActivity<T : ViewDataBinding>(@LayoutRes val resId: Int) : BaseActivity() {
    private lateinit var mCaptureHelper: CaptureHelper
    lateinit var binding: T
    private val CHOOSE_IMG = 0x863

    //获取子类dataBinding  延迟初始化，只有dataBinding第一次被调用的时候这里面的方法才会执行
    private fun <T : ViewDataBinding> getDataBinding(@LayoutRes resId: Int): T {
        return DataBindingUtil.inflate<T>(LayoutInflater.from(this), resId, flContainer, false)
    }

    //填充逻辑代码
    open fun initView() {}
    abstract fun initData()
    abstract fun handResult(result: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        val view = LayoutInflater.from(this).inflate(resId, flContainer, false)
        flContainer.addView(view)
        binding = getDataBinding(resId)
        initView()
        initData()
    }

    //设置标题
    protected fun setTitle(title: String = "测试") {
        baseToolbar.title = title
    }

    //初始化界面
    protected fun init(
        surfaceView: SurfaceView,
        viewfinderView: ViewfinderView,
        ivFlash: ImageView
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mCaptureHelper = CaptureHelper(this, surfaceView, viewfinderView, ivFlash)
            mCaptureHelper?.apply {
                setOnCaptureCallback {
                    //扫码结果
                    if (it.isNullOrEmpty()) {
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
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mCaptureHelper.onTouchEvent(event)
        return super.onTouchEvent(event)
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
            val parseQRCode = CodeUtils.parseQRCode(fixDataUrl?.firstOrNull())
            L.d("识别结果：$parseQRCode")
            handResult(parseQRCode)
        }
    }
}