package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyRankActivity extends BaseActivity {


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
    @BindView(R.id.iv_my_rank_pic)
    CircleImageView ivMyRankPic;
    @BindView(R.id.tv_my_rank_content)
    TextView tvMyRankContent;
    @BindView(R.id.tv_my_rank_number)
    TextView tvMyRankNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_rank);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("我的等级");
    }

    @Override
    protected void initView() {
        Glide.with(this).load("http://pics.sc.chinaz.com/files/pic/pic9/201409/apic6478.jpg").asBitmap().into(ivMyRankPic) ;

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
