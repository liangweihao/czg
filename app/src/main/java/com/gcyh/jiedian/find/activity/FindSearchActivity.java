package com.gcyh.jiedian.find.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.FindRecyclerAdapter;
import com.gcyh.jiedian.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindSearchActivity extends BaseActivity {


    @BindView(R.id.iv_titlebar_findsearch_search)
    ImageView ivTitlebarFindsearchSearch;
    @BindView(R.id.et_titlebar_findsearch_search)
    EditText etTitlebarFindsearchSearch;
    @BindView(R.id.iv_titlebar_findsearch_delete)
    ImageView ivTitlebarFindsearchDelete;
    @BindView(R.id.tv_titlebar_findsearch_back)
    TextView tvTitlebarFindsearchBack;
    @BindView(R.id.recycleViewFindSearch)
    RecyclerView recycleViewFindSearch;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_find_search);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recycleViewFindSearch.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
    }

    @Override
    protected void initPresenter() {
        initData(10);
//        FindRecyclerAdapter  recycleFindAdapter = new FindRecyclerAdapter(this, mDatas);
//        recycleViewFindSearch.setAdapter(recycleFindAdapter);
    }

    @Override
    protected void initValue() {

    }


    @OnClick({R.id.iv_titlebar_findsearch_delete, R.id.tv_titlebar_findsearch_back, R.id.iv_titlebar_findsearch_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_findsearch_delete:
                etTitlebarFindsearchSearch.setText("");
                break;
            case R.id.tv_titlebar_findsearch_back:
                finish();
                break;
            case R.id.iv_titlebar_findsearch_search:
                //搜索
                break;
        }
    }

    private void initData(int value) {

        mDatas = new ArrayList<String>();
        for (int i = 0; i < value; i++) {
            mDatas.add("世界你好" + i);
        }

    }

}
