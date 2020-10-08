package com.dian.mylibrary.ui.base

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dian.mylibrary.R
import com.dian.mylibrary.databinding.ActivityMainBinding

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/9/10 11:25 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/10 11:25 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
abstract class BaseMainActivity : BaseFullActivity<ActivityMainBinding>(R.layout.activity_main) {
    //获取fragments
    abstract fun getFragments(): ArrayList<Fragment>

    override fun initData() {
        initViewPager()
        initBottomNav()
    }

    /**
     * 初始化底部按钮的点击事件 切换页面
     */
    private fun initBottomNav() {
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    binding.viewPager.setCurrentItem(0, false)
                }
                R.id.person -> {
                    binding.viewPager.setCurrentItem(1, false)
                }
            }
            true
        }
    }

    /**
     * 初始化viewpager
     */
    private fun initViewPager() {
        val fragments = getFragments()
        fragments.let {
            binding.viewPager.offscreenPageLimit = fragments.size
            //禁止用户滑动
            binding.viewPager.isUserInputEnabled = false
            binding.viewPager.adapter = object : FragmentStateAdapter(this) {
                override fun getItemCount(): Int {
                    return fragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragments[position]
                }
            }
        }
    }

}