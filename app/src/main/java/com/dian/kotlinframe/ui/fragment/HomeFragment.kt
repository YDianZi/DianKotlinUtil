package com.dian.kotlinframe.ui.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dian.kotlinframe.R
import com.dian.kotlinframe.databinding.FragmentHomeBinding
import com.dian.kotlinframe.ui.activity.MainActivity
import com.dian.mylibrary.ui.base.BaseFragment
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