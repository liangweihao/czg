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
import com.gcyh.jiedian.my.fragment.MyOrderNoteLibraryFragment;
import com.gcyh.jiedian.my.fragment.MyOrderProjectLibraryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.myOrderXTablayout)
    XTabLayout myOrderXTablayout;
    List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.myOrderViewPager)
    ViewPager myOrderViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("我的订单");
    }

    @Override
    protected void initView() {
        List<String> titles = new ArrayList<>();
        titles.add("节点库");
        titles.add("项目库");

        for (int i = 0; i < titles.size(); i++) {
            if (i == 0) {
                fragments.add(new MyOrderNoteLibraryFragment());
            } else if (i == 1) {
                fragments.add(new MyOrderProjectLibraryFragment());
            }
        }
        TabLayoutFragmentAdapter adatper = new TabLayoutFragmentAdapter(getSupportFragmentManager() , fragments, titles);

        myOrderViewPager.setAdapter(adatper);
        myOrderViewPager.setOffscreenPageLimit(1);
        //将TabLayout和ViewPager关联起来。
        myOrderXTablayout.setupWithViewPager(myOrderViewPager);
        myOrderViewPager.setCurrentItem(0);
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
