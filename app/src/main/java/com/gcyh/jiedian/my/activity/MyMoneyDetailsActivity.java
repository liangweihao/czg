package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.MyMoneyDetailsRecycleViewAdapter;
import com.gcyh.jiedian.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyMoneyDetailsActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.recycleView_money_details)
    RecyclerView recycleViewMoneyDetails;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_money_details);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("线币明细");
    }

    @Override
    protected void initView() {
        initData() ;

        LinearLayoutManager layoutManager = new LinearLayoutManager(MyMoneyDetailsActivity.this);
        //设置布局管理器
        recycleViewMoneyDetails.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        MyMoneyDetailsRecycleViewAdapter adapter = new MyMoneyDetailsRecycleViewAdapter(MyMoneyDetailsActivity.this, mDatas) ;
        recycleViewMoneyDetails.setAdapter(adapter);
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

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mDatas.add("世界你好" + i);
        }
    }

}
