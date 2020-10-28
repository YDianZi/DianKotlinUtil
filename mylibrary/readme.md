#### MainActivity用法
````kotlin
class MainActivity : BaseMainActivity() {
    override fun getFragments(): ArrayList<Fragment> {
        return arrayListOf(HomeFragment(),MineFragment())
    }
    override fun initData() {
        super.initData()
    }
}
````
#### ScanActivity用法
````kotlin
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
````
#### TabLayout+ViewPage2用法
````kotlin
  binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }
        }
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabText(position)
        }.attach()
 fun getTabIcon(position: Int): Int {
        return when (position) {
            0 -> R.drawable.ic_home
            1 -> R.drawable.ic_person
            else -> throw  IndexOutOfBoundsException()
        }
    }

    fun getTabText(position: Int): String? {
        return when (position) {
            0 -> "知产热点"
            1 -> "知产服务"
            else -> throw  IndexOutOfBoundsException()
        }
    }
````
#### banner使用
````kotlin
 override fun initData() {
        val bannerDatas = listOf<BannerData>(
            BannerData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602320903484&di=205b0229252a818e7b7f7e223b1a025b&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg"),
            BannerData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602320903484&di=205b0229252a818e7b7f7e223b1a025b&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg"),
            BannerData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602320903484&di=205b0229252a818e7b7f7e223b1a025b&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg"),
            BannerData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602320903484&di=205b0229252a818e7b7f7e223b1a025b&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg")
        )
        val bigImg = arrayListOf(
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602320903484&di=205b0229252a818e7b7f7e223b1a025b&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602320903484&di=205b0229252a818e7b7f7e223b1a025b&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602320903484&di=205b0229252a818e7b7f7e223b1a025b&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602320903484&di=205b0229252a818e7b7f7e223b1a025b&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg"
        )
        BannerUtil.normalBanner<ItemBannerBinding>(
            context,
            binding.banner as Banner<BannerData, ImageAdapter<ItemBannerBinding>>,
            R.layout.item_banner,
            bannerDatas,
            initData = { holder, data, position, size ->
                holder?.apply { loadImg(R.id.image, data?.url ?: "") }
            },
            click = { data, position ->
                if (data is BannerData) {
                    L.d("click==${data.url}")
                    BigImgViewPagerActivity.startBigImgViewPagerActivity(
                        getActivity(),
                        bigImg, position
                    )
                }
            })
    }
````
#### 图片选择及裁剪压缩使用
````kotlin
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
````