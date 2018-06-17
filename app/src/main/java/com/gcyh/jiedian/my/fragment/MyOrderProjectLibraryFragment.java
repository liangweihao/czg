package com.gcyh.jiedian.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.MyOrderNoteRecycleViewAdapter;
import com.gcyh.jiedian.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Caizhiguang on 2018/5/3.
 */

public class MyOrderProjectLibraryFragment extends BaseFragment {
    @BindView(R.id.recycleView_my_order_project)
    RecyclerView recycleViewMyOrderProject;
    @BindView(R.id.ll_my_order_project_null)
    LinearLayout llMyOrderProjectNull;
    Unbinder unbinder;
    private List<String> mDatas;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_order_project;
    }

    @Override
    protected void initPresenter() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recycleViewMyOrderProject.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        initData(20);
        //适配器
        MyOrderNoteRecycleViewAdapter adapter = new MyOrderNoteRecycleViewAdapter(getActivity() , mDatas);
        recycleViewMyOrderProject.setAdapter(adapter);

    }

    @Override
    protected void initView() {

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

    private void initData(int value) {

        mDatas = new ArrayList<String>();
        for (int i = 0; i < value; i++) {
            mDatas.add("世界你好" + i);
        }

    }
}
