package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.gcyh.jiedian.entrance.SplashActivity;

import java.util.List;

/**
 * Created by Caizhiguang on 2018/5/14.
 */

public class SplashViewPagerAdapter  extends PagerAdapter {

    private List<View> list ;
    private Context context ;

    public SplashViewPagerAdapter(Context splashActivity, List<View> list) {
        this.list = list ;
        this.context = splashActivity ;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object ;
    }

    //加载viewpager的每个item
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=list.get(position);
        container.addView(view);
        return view;
    }
    //删除ViewPager的item
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

}
