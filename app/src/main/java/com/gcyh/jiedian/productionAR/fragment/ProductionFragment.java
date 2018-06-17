package com.gcyh.jiedian.productionAR.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.ProductionARRecyclerAdapter;
import com.gcyh.jiedian.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Caizhiguang on 2018/4/18.
 */

public class ProductionFragment extends BaseFragment {

    @BindView(R.id.recycleViewAR)
    RecyclerView recycleViewAR;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
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
    @BindView(R.id.rl_titlebar)
    RelativeLayout rlTitlebar;
    Unbinder unbinder;


    private List<String> mDatas;
    private ProductionARRecyclerAdapter recycleARAdapter;


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_production;
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recycleViewAR.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        initRefreshLayout();


    }

    @Override
    protected void initView() {

    }


    @Override
    protected void initValue() {
        initData();
        recycleARAdapter = new ProductionARRecyclerAdapter(getActivity(), mDatas);
        recycleViewAR.setAdapter(recycleARAdapter);
    }

    private void initData() {

        mDatas = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            mDatas.add("http://pics.sc.chinaz.com/files/pic/pic9/201305/xpic11142.jpg");
        }

    }

    private void initRefreshLayout() {

        //  刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mSmartRefreshLayout.finishRefresh();
//                        mSmartRefreshLayout.finishLoadmore();
                    }
                }, 5000);

            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
//                        mSmartRefreshLayout.finishRefresh();
                        mSmartRefreshLayout.finishLoadmore();
                    }
                }, 5000);

            }
        });

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

    @OnClick({R.id.iv_titlebar_search, R.id.iv_titlebar_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_search:
                //搜索
                break;
            case R.id.iv_titlebar_delete:
                etTitlebarSearch.setText("");
                break;
        }
    }


}



