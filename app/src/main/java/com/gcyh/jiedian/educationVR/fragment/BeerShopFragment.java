package com.gcyh.jiedian.educationVR.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.BeerShopFragmentAdapter;
import com.gcyh.jiedian.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Caizhiguang on 2018/4/28.
 */

public class BeerShopFragment extends BaseFragment {
    @BindView(R.id.recycleView_beer_shop)
    RecyclerView recycleViewBeerShop;
    Unbinder unbinder;

    private List<String> mDatas;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_beer_shop;
    }

    @Override
    protected void initPresenter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //水平滑动
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        recycleViewBeerShop.setLayoutManager(layoutManager);
    }

    @Override
    protected void initView() {
        initData(1);
        BeerShopFragmentAdapter adapter = new BeerShopFragmentAdapter(getActivity(), mDatas);
        recycleViewBeerShop.setAdapter(adapter);
    }

    @Override
    protected void initValue() {

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

    private int value = 0;

    private void initData(int value) {

        mDatas = new ArrayList<String>();
        for (int i = 0; i < value; i++) {
            mDatas.add("世界你好" + i);
        }

    }
}
