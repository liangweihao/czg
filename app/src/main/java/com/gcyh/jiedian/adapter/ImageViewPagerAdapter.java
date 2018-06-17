package com.gcyh.jiedian.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.find.activity.ImageViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caizhiguang on 2018/5/24.
 */

public class ImageViewPagerAdapter extends PagerAdapter {

    private ImageViewActivity context;
    private ArrayList<String> list;

    public ImageViewPagerAdapter(ImageViewActivity imageViewActivity, ArrayList<String> list) {
        this.context = imageViewActivity;
        this.list = list;
    }

    @Override
    public int getCount() {  //获得size
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) { //销毁Item
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) { //实例化Item
        ImageView imageView = new ImageView(context);
        // 设置属性
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        Glide.with(context).load(list.get(position)).asBitmap().into(imageView);
        view.addView(imageView);

        return imageView ;
    }
}
