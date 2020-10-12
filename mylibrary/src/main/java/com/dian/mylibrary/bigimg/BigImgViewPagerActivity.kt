package com.dian.mylibrary.bigimg

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.dian.mylibrary.R
import com.dian.mylibrary.databinding.ActivityBigImgViewPagerBinding
import com.dian.mylibrary.ui.base.BaseUIActivity
import com.dian.mylibrary.utils.ktx.myStartActivity
import kotlinx.android.synthetic.main.activity_big_img_view_pager.*
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/10/12 9:17 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/12 9:17 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class BigImgViewPagerActivity :
    BaseUIActivity<ActivityBigImgViewPagerBinding>(R.layout.activity_big_img_view_pager) {

    private var images: ArrayList<String>? = null
    private var images1: ArrayList<Int>? = null
    private var currentPage = 0
    private var totlePage = 0

    companion object{
        val IMAGES = "images"
        val IMAGES1 = "images1"
        val SHOW_INDEX = "showIndex"

        @JvmStatic
        fun startBigImgViewPagerActivity(
            activity: FragmentActivity?,
            images: ArrayList<String>,
            currentPage: Int
        ) {
            activity?.myStartActivity<BigImgViewPagerActivity>(params = {
                putStringArrayListExtra(IMAGES, images)
                putExtra(SHOW_INDEX, currentPage)
            })
        }

        @JvmStatic
        fun startLocalBigImgViewPagerActivity(
            activity: Activity?,
            images: ArrayList<Int>,
            currentPage: Int
        ) {
            activity?.myStartActivity<BigImgViewPagerActivity>(params = {
                putIntegerArrayListExtra(IMAGES1, images)
                putExtra(SHOW_INDEX, currentPage)
            })
        }

    }



    override fun initData() {
        images = this.intent.getStringArrayListExtra(IMAGES)
        images1 = this.intent.getIntegerArrayListExtra(IMAGES1)
        currentPage = this.intent.getIntExtra(SHOW_INDEX, 0)
        if (images != null) {
            totlePage = images?.size?:0
            val mAdapter =
                BigImgSamplePagerAdapter(supportFragmentManager, images)
            viewPager.setAdapter(mAdapter)
        } else {
            totlePage = images1!!.size
            val mAdapter =
                LocalBigImgSamplePagerAdapter(supportFragmentManager, images1)
            viewPager.setAdapter(mAdapter)
        }

        setTitle((currentPage + 1).toString() + "/" + totlePage)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                setTitle((currentPage + 1).toString() + "/" + totlePage)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        viewPager.setCurrentItem(currentPage)
    }
}