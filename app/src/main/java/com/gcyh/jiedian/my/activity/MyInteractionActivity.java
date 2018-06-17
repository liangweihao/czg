package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.TabLayoutFragmentAdapter;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.my.fragment.MyInteractionCommentFragment;
import com.gcyh.jiedian.my.fragment.MyInteractionMyZanFragment;
import com.gcyh.jiedian.my.fragment.MyInteractionNoticeMyFragment;
import com.gcyh.jiedian.my.fragment.MyInteractionZanMyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInteractionActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.myZanxTablayout)
    XTabLayout myZanxTablayout;
    @BindView(R.id.myZanViewPager)
    ViewPager myZanViewPager;

    List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_interaction);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("我的互动");
    }

    @Override
    protected void initView() {
        List<String> titles = new ArrayList<>();
        titles.add("@我的");
        titles.add("评论");
        titles.add("我赞的");
        titles.add("赞我的");

        for (int i = 0; i < titles.size(); i++) {
            if (i == 0) {
                fragments.add(new MyInteractionNoticeMyFragment());
            } else if (i == 1) {
                fragments.add(new MyInteractionCommentFragment());
            } else if (i == 2) {
                fragments.add(new MyInteractionMyZanFragment());
            } else if (i == 3) {
                fragments.add(new MyInteractionZanMyFragment());
            }
        }
        TabLayoutFragmentAdapter adatper = new TabLayoutFragmentAdapter(getSupportFragmentManager(), fragments, titles);

        myZanViewPager.setAdapter(adatper);
        myZanViewPager.setOffscreenPageLimit(3);
        //将TabLayout和ViewPager关联起来。
        myZanxTablayout.setupWithViewPager(myZanViewPager);
        //给TabLayout设置适配器
        myZanxTablayout.setupWithViewPager(myZanViewPager);

        myZanViewPager.setCurrentItem(0);
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
