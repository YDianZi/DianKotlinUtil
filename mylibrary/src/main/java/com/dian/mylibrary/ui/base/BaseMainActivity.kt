package com.dian.mylibrary.ui.base

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dian.mylibrary.BaseMyApp
import com.dian.mylibrary.R
import com.dian.mylibrary.databinding.ActivityMainBinding
import com.dian.mylibrary.utils.ktx.showToast

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
    abstract fun getMenu(): Int
    abstract fun initMenu(menu: MenuItem)

    override fun initData() {
        initViewPager()
        initBottomNav()
    }

    /**
     * 初始化底部按钮的点击事件 切换页面
     */
    private fun initBottomNav() {
        binding.bottomNav.inflateMenu(getMenu())
        binding.bottomNav.setOnNavigationItemSelectedListener {
            initMenu(it)
            true
        }
    }

    protected fun setCurrentItem(posi: Int = 0) {
        binding.viewPager.setCurrentItem(posi, false)
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


    /**
     * 两次返回键推出程序
     */
    private var firstTime: Long = 0

    override fun onBackPressed() {
        val secondTime = System.currentTimeMillis()
        if (secondTime - firstTime > 800) {
            "再按一次退出程序".showToast()
            firstTime = secondTime // 更新firstTime
        } else {
            // 否则退出程序
            BaseMyApp.instance.exit()
            super.onBackPressed()
        }
    }
}