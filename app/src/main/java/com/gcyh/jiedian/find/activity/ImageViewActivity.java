package com.gcyh.jiedian.find.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.ImageViewPagerAdapter;
import com.gcyh.jiedian.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageViewActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.txt_toolbar_right)
    TextView txtToolbarRight;
    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("图片详情");
    }

    @Override
    protected void initView() {
        ArrayList<String> list = getIntent().getStringArrayListExtra("typepic");
        int position = getIntent().getIntExtra("position", 0);


        //适配器
        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(ImageViewActivity.this, list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
