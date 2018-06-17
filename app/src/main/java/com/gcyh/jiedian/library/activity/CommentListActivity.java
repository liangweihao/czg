package com.gcyh.jiedian.library.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.CommentListAdapter;
import com.gcyh.jiedian.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentListActivity extends BaseActivity {

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
    @BindView(R.id.recycleViewCommentList)
    RecyclerView recycleViewCommentList;
    @BindView(R.id.et_comment_list_content)
    EditText etCommentListContent;
    @BindView(R.id.tv_comment_list_send)
    TextView tvCommentListSend;
    private List<String> Datas;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("评论列表");
    }

    @Override
    protected void initView() {
        initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //水平滑动
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        recycleViewCommentList.setLayoutManager(layoutManager);
        //适配器
        CommentListAdapter adapter = new CommentListAdapter(this, Datas);
        recycleViewCommentList.setAdapter(adapter);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    private void initData() {

        Datas = new ArrayList<String>();
        for (int i = 0; i < 25; i++) {
            Datas.add("世界你好" + i);
        }

    }

    @OnClick({R.id.btn_back, R.id.tv_comment_list_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_comment_list_send:
                //发送
                break;
        }
    }
}
