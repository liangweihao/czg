package com.gcyh.jiedian.find.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.FindDetailsRecycleViewAdapter;
import com.gcyh.jiedian.adapter.FindDetailsRecycleViewAdapter1;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.CommentDetailsList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindDetailsActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.recycleView_Find_details)
    RecyclerView recycleViewFindDetails;
    @BindView(R.id.tv_find_details_head_comment_count)
    TextView tvFindDetailsHeadCommentCount;

    private List<String> mDatas;
    private List<CommentDetailsList> list = new ArrayList<>();
    private CommentDetailsList model;
    private HeaderLayout mHeaderView;
    int imageY = 0;
    private FindDetailsRecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_find_details);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("动态详情");
    }

    @Override
    protected void initView() {

        initData(10);

        for (int i = 0; i < 30; i++) {
            model = new CommentDetailsList();
            //这里随机给某一条目设置不同布局类型
            if (i == 0 || i == 8 || i == 16) {
                model.setType(CommentDetailsList.FIRST_TYPE);
            } else {
                model.setType(CommentDetailsList.SECOND_TYPE);
            }
            list.add(model);
        }


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recycleViewFindDetails.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new FindDetailsRecycleViewAdapter(this, mDatas);
        mHeaderView = new HeaderLayout(this);
//        FindDetailsRecycleViewAdapter1 adapter1 = new FindDetailsRecycleViewAdapter1(list);
//        adapter1.addHeaderView(mHeaderView);
        recycleViewFindDetails.setAdapter(adapter);

        recycleViewFindDetails.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] location = new int[2];
                tvFindDetailsHeadCommentCount.getLocationOnScreen(location);
                int y = location[1];
                imageY = y;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (adapter == null) return;
                int getTop = adapter.getDistanceY();
                if (getTop <= imageY) {
                    tvFindDetailsHeadCommentCount.setVisibility(View.VISIBLE);
                } else {
                    tvFindDetailsHeadCommentCount.setY(0);
                    tvFindDetailsHeadCommentCount.setVisibility(View.GONE);
                }
            }
        });

        initRefreshLayout();

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

    private void initData(int value) {

        mDatas = new ArrayList<String>();
        for (int i = 0; i < value; i++) {
            mDatas.add("世界你好" + i);
        }

    }

    private void initRefreshLayout() {

//        mSmartRefreshLayout.setEnableRefresh(false);
//
//        //加载更多
//        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        mSmartRefreshLayout.finishLoadmore();
//                    }
//                }, 5000);
//
//            }
//        });

    }

}
