package com.dian.mylibrary.ui.base

import android.annotation.TargetApi
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.webkit.WebChromeClient.FileChooserParams
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import com.dian.mylibrary.BaseMyApp
import com.dian.mylibrary.R
import com.dian.mylibrary.databinding.ActivityBaseWebviewBinding
import com.dian.mylibrary.utils.ktx.L
import com.dian.mylibrary.utils.ktx.myStartActivity
import kotlinx.android.synthetic.main.activity_base_webview.*
import java.net.URISyntaxException

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/12 9:53 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/12 9:53 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
open class BaseWebActivity : BaseUIActivity<ActivityBaseWebviewBinding>(R.layout.activity_base_webview) {
    private var listener: OnAudioFocusChangeListener? = null

    private val WEB_PERMISSION = 0x123

    //解决不支持H5 input type = file
    private var mUploadMessage: ValueCallback<Uri>? = null
    var uploadMessage: ValueCallback<Array<Uri>>? = null
    val REQUEST_SELECT_FILE = 100
    private val FILECHOOSER_RESULTCODE = 2

    companion object {
        private var audioManager: AudioManager? = null
        private var url: String = ""
        private var webTitle: String = ""
        fun startWebActivity(activity: FragmentActivity, url: String, title: String) {
            activity.myStartActivity<BaseWebActivity>(params = {
                putExtra("url", url)
                putExtra("title", title)
            })
        }
    }

    override fun initData() {
        url = intent.getStringExtra("url") ?: ""
        webTitle = intent.getStringExtra("title") ?: ""
        setTitle(webTitle)

        initWeb()

    }
    private fun initWeb() {
        val webSettings: WebSettings = webDetails.settings

        // 设置WebView属性，能够执行Javascript脚本
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically =
            true //设置js可以直接打开窗口，如window.open()，默认为false
        webSettings.setSupportZoom(true) //是否可以缩放，默认true
        webSettings.builtInZoomControls = false //是否显示缩放按钮，默认false
        webSettings.allowContentAccess = true // 是否可访问Content Provider的资源，默认值 true
        webSettings.allowFileAccess = true // 是否可访问本地文件，默认值 true
        //        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
//        webSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webSettings.setAppCacheEnabled(false) //是否使用缓存
        webSettings.domStorageEnabled = true //DOM Storage
        webSettings.pluginState = WebSettings.PluginState.ON //播放视频添加
        webSettings.mediaPlaybackRequiresUserGesture //播放游戏声音
        webSettings.mediaPlaybackRequiresUserGesture = true //播放游戏声音
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

//        String userAgentString = webSettings.getUserAgentString();
//        L.e("userAgentString==" + userAgentString);
//        webSettings.setUserAgentString(userAgentString + " BTW_Wallet/ANDROID");
//        String userAgentString2 = webSettings.getUserAgentString();
//        L.e("userAgentString2==" + userAgentString2);


        // 设置Web视图
        L.d("url===$url")
        webDetails.loadUrl(url)
        webDetails.webViewClient = webViewClient
        webDetails.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                if (newProgress == 100) {
                    // 网页加载完成
                    progressBar.visibility = View.GONE //加载完网页进度条消失
                } else {
                    // 加载中
                    progressBar.visibility = View.VISIBLE //开始加载网页时显示进度条
                    progressBar.progress = newProgress //设置进度值
                }
            }

            override fun onPermissionRequest(request: PermissionRequest) {
                super.onPermissionRequest(request)
            }

            override fun onPermissionRequestCanceled(request: PermissionRequest) {
                super.onPermissionRequestCanceled(request)
            }

            // For 3.0+ Devices (Start)
            // onActivityResult attached before constructor
            protected fun openFileChooser(
                uploadMsg: ValueCallback<Uri>,
                acceptType: String?
            ) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(i, "File Browser"),
                   FILECHOOSER_RESULTCODE
                )
            }

            // For Lollipop 5.0+ Devices
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onShowFileChooser(
                mWebView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                if (uploadMessage != null) {
                    uploadMessage!!.onReceiveValue(null)
                    uploadMessage = null
                }
                uploadMessage = filePathCallback
                val intent = fileChooserParams.createIntent()
                try {
                    startActivityForResult(
                        intent,
                        REQUEST_SELECT_FILE
                    )
                } catch (e: ActivityNotFoundException) {
                    uploadMessage = null
                    Toast.makeText(baseContext, "Cannot Open File Chooser", Toast.LENGTH_LONG)
                        .show()
                    return false
                }
                return true
            }

            //For Android 4.1 only
            protected fun openFileChooser(
                uploadMsg: ValueCallback<Uri>,
                acceptType: String?,
                capture: String?
            ) {
                mUploadMessage = uploadMsg
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(intent, "File Browser"),
                    FILECHOOSER_RESULTCODE
                )
            }

            protected fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE
                )
            }
        }
    }

    // Web视图
    private val webViewClient =object : WebViewClient() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest
        ): Boolean {
            return shouldOverrideUrlLoading(view, request.url.toString())
        }

        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            if (!TextUtils.isEmpty(url)) {
                //处理intent协议
                if (url.startsWith("intent://")) {
                    val intent: Intent
                    try {
                        intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        intent.addCategory("android.intent.category.BROWSABLE")
                        intent.component = null
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                            intent.selector = null
                        }
                        val resolves: List<ResolveInfo> =
                            getPackageManager().queryIntentActivities(intent, 0)
                        if (resolves.isNotEmpty()) {
                            startActivity(intent)
                        }
                        return true
                    } catch (e: URISyntaxException) {
                        e.printStackTrace()
                    }
                } else if (!url.startsWith("http")) {
                    // 处理自定义scheme协议
                    L.d("处理自定义scheme-->$url")
                    val parse = Uri.parse(url)
                    try {
                        // 以下固定写法
                        val intent = Intent(Intent.ACTION_VIEW, parse)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        startActivity(intent)
                    } catch (e: Exception) {
                        // 防止没有安装的情况
                        e.printStackTrace()
                        //                        showToast("你所打开的第三方App未安装!");
                        val scheme = parse.scheme
                        if ("yideng" == scheme) { //如果是一灯社区，则打开官网链接
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://pc.yideng.com/static/download/index.html")
                            )
                            startActivity(intent)
                        }
                    }
                    return true
                } else {
                    view.loadUrl(url)
                    return true
                }
            }
            return false
        }

        //访问ssl证书网页的问题
        override fun onReceivedSslError(
            view: WebView,
            handler: SslErrorHandler,
            error: SslError
        ) {
            // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
            // super.onReceivedSslError(view, handler, error);

            // 接受所有网站的证书，忽略SSL错误，执行访问网页
            handler.proceed()
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            L.d("onReceivedError")
        }

        // 网页开始加载
        override fun onPageStarted(
            view: WebView,
            url: String,
            favicon: Bitmap
        ) { // 网页页面开始加载的时候
            super.onPageStarted(view, url, favicon)
        }

        // 网页加载结束
        override fun onPageFinished(
            view: WebView,
            url: String
        ) {
            super.onPageFinished(view, url)
            L.d("onPageFinished" + view.title)
            if (TextUtils.isEmpty(webTitle)) {
                if (!TextUtils.isEmpty(view.title)) {
//                if (view.getTitle().contains("www.")) {
//                    tvTitle.setText("");
//                } else {
//                    tvTitle.setText(view.getTitle());
//                }
                } else {
//                    tvTitle.setText("");
                }
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null) return
                uploadMessage!!.onReceiveValue(
                    FileChooserParams.parseResult(
                        resultCode,
                        data
                    )
                )
                uploadMessage = null
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            val result =
                if (data == null || resultCode != Activity.RESULT_OK) null else data.data
            mUploadMessage?.onReceiveValue(result)
            mUploadMessage = null
        } else Toast.makeText(baseContext, "Failed to Upload Image", Toast.LENGTH_LONG).show()
    }

    //判断app是否安装
    private fun isInstall(intent: Intent): Boolean {
        return BaseMyApp.instance.packageManager
            .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)?.size > 0
    }

    //打开app
    private fun openApp(url: String): Boolean {
        if (TextUtils.isEmpty(url)) return false
        try {
            if (!url.startsWith("http") || !url.startsWith("https") || !url.startsWith("ftp")) {
                val uri = Uri.parse(url)
                val host = uri.host
                val scheme = uri.scheme
                //host 和 scheme 都不能为null
                if (!TextUtils.isEmpty(host) && !TextUtils.isEmpty(scheme)) {
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    if (isInstall(intent)) {
                        startActivity(intent)
                        return true
                    }
                }
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webDetails.canGoBack()) {
                webDetails.goBack()
            } else {
                finish()
            }
        }
        super.onKeyDown(keyCode, event)
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        webDetails.clearHistory()
        webDetails.clearCache(true)
        webDetails.destroy()
    }

    override fun onResume() {
        if (audioManager != null) {
            audioManager?.abandonAudioFocus(listener)
            audioManager = null
        }
        super.onResume()
        webDetails.onResume()
    }


    override fun onPause() {
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        listener = OnAudioFocusChangeListener { }
        val result = audioManager?.requestAudioFocus(
            listener,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
        )
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        }
        super.onPause()
        webDetails.onPause()
    }

}