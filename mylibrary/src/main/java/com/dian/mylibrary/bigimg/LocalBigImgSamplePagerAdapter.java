package com.dian.mylibrary.bigimg;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020-04-28 17:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-04-28 17:27
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LocalBigImgSamplePagerAdapter extends FragmentStatePagerAdapter {

    private List<Integer> imageUrls;

    public LocalBigImgSamplePagerAdapter(FragmentManager fm, List<Integer> imageUrls) {
        super(fm);
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls!= null? imageUrls.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return ViewImagesFragment.newInstance(imageUrls.get(position));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        final ViewImagesFragment fragment = (ViewImagesFragment) object;
        // As the item gets destroyed we try and cancel any existing work.
        fragment.cancelWork();
        super.destroyItem(container, position, object);
    }
}
