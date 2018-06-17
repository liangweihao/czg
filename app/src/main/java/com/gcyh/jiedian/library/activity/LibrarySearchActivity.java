package com.gcyh.jiedian.library.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LibrarySearchActivity extends BaseActivity {

    @BindView(R.id.iv_titlebar_librarysearch_search)
    ImageView ivTitlebarLibrarysearchSearch;
    @BindView(R.id.et_titlebar_librarysearch_search)
    EditText etTitlebarLibrarysearchSearch;
    @BindView(R.id.iv_titlebar_librarysearch_delete)
    ImageView ivTitlebarLibrarysearchDelete;
    @BindView(R.id.tv_titlebar_librarysearch_back)
    TextView tvTitlebarLibrarysearchBack;
    @BindView(R.id.recycleViewLibrarySearch)
    RecyclerView recycleViewLibrarySearch;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_library_search);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recycleViewLibrarySearch.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
    }

    @Override
    protected void initPresenter() {
        initData(0);
    }

    @Override
    protected void initValue() {

    }

    @OnClick({R.id.iv_titlebar_librarysearch_search, R.id.iv_titlebar_librarysearch_delete, R.id.tv_titlebar_librarysearch_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_librarysearch_search:
                //搜索
                break;
            case R.id.iv_titlebar_librarysearch_delete:
                etTitlebarLibrarysearchSearch.setText("");
                break;
            case R.id.tv_titlebar_librarysearch_back:
                finish();
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
