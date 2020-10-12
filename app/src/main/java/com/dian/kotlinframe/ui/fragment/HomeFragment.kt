package com.dian.kotlinframe.ui.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dian.kotlinframe.R
import com.dian.kotlinframe.databinding.FragmentHomeBinding
import com.dian.kotlinframe.databinding.ItemBannerBinding
import com.dian.kotlinframe.ui.activity.MainActivity
import com.dian.mylibrary.bigimg.BigImgViewPagerActivity
import com.dian.mylibrary.ui.base.BaseFragment
import com.dian.mylibrary.utils.ktx.L
import com.dian.mylibrary.widget.banner.BannerData
import com.dian.mylibrary.widget.banner.BannerUtil
import com.dian.mylibrary.widget.banner.ImageAdapter
import com.dian.youth.banner.Banner
import com.google.android.material.tabs.TabLayoutMediator

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
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    val fragments = arrayListOf(ZhiChanHotFragment(), ZhiChanServiceFragment())
    override fun initView() {
        (activity as MainActivity).setSupportActionBar(binding.toolBar)
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
    }

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
}