package com.gcyh.jiedian.educationVR.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.androidkun.xtablayout.XTabLayout;
import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.TabLayoutFragmentAdapter;
import com.gcyh.jiedian.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Caizhiguang on 2018/4/28.
 */

public class EducationFragment extends BaseFragment {

    @BindView(R.id.iv_education_slideshow)
    ImageView ivEducationSlideshow;
    @BindView(R.id.education_xTablayout)
    XTabLayout educationXTablayout;
    @BindView(R.id.educationViewPager)
    ViewPager educationViewPager;
    @BindView(R.id.iv_titlebar_type)
    ImageView ivTitlebarType;
    @BindView(R.id.iv_titlebar_search)
    ImageView ivTitlebarSearch;
    @BindView(R.id.et_titlebar_search)
    EditText etTitlebarSearch;
    @BindView(R.id.iv_titlebar_delete)
    ImageView ivTitlebarDelete;
    @BindView(R.id.iv_titlebar_jia)
    ImageView ivTitlebarJia;
    @BindView(R.id.titlebar)
    LinearLayout titlebar;
    Unbinder unbinder;

    List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.rl_titlebar)
    RelativeLayout rlTitlebar;


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_education;
    }

    @Override
    protected void initPresenter() {
        ivTitlebarType.setVisibility(View.GONE);
        ivTitlebarJia.setVisibility(View.GONE);
        etTitlebarSearch.setHint("天津假日酒店....");

        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(15, 8, 15, 8);
        rlTitlebar.setLayoutParams(layout);
    }

    @Override
    protected void initView() {

        List<String> titles = new ArrayList<>();
        titles.add("酒店");
        titles.add("餐厅");
        titles.add("商场");

        for (int i = 0; i < titles.size(); i++) {
            if (i == 0) {
                fragments.add(new BeerShopFragment());
            } else if (i == 1) {
                fragments.add(new RestaurantFragment());
            } else if (i == 2) {
                fragments.add(new ShopFragment());
            }
        }
        TabLayoutFragmentAdapter adatper = new TabLayoutFragmentAdapter(getChildFragmentManager(), fragments, titles);

        educationViewPager.setAdapter(adatper);
        educationViewPager.setOffscreenPageLimit(3);
        //将TabLayout和ViewPager关联起来。
        educationXTablayout.setupWithViewPager(educationViewPager);
        educationViewPager.setCurrentItem(0);

    }

    @Override
    protected void initValue() {
        Glide.with(getActivity()).load("http://pics.sc.chinaz.com/files/pic/pic9/201403/apic269.jpg").asBitmap().into(ivEducationSlideshow);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
