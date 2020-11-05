package com.dian.kotlinframe.ui.activity

import android.content.Context
import android.view.MenuItem
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.dian.kotlinframe.R
import com.dian.kotlinframe.ui.fragment.HomeFragment
import com.dian.kotlinframe.ui.fragment.MineFragment
import com.dian.mylibrary.ui.base.BaseMainActivity
import com.dian.mylibrary.utils.ktx.SP

class MainActivity : BaseMainActivity() {
    override fun getFragments(): ArrayList<Fragment> {
        return arrayListOf(HomeFragment(), MineFragment())
    }

    override fun getMenu(): Int {
        return R.menu.menu_bottom_nav
    }

    override fun initMenu(menu: MenuItem) {
        when (menu.itemId) {
            R.id.home -> {
                setCurrentItem(0)
            }
            R.id.person -> {
                setCurrentItem(1)
            }
        }
    }

    override fun initData() {
        super.initData()
    }
}