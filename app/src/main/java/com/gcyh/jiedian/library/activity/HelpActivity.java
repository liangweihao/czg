package com.gcyh.jiedian.library.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity {


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
    @BindView(R.id.iv_move)
    ImageView ivMove;
    @BindView(R.id.iv_rotate)
    ImageView ivRotate;
    @BindView(R.id.iv_scale)
    ImageView ivScale;
    @BindView(R.id.iv_double_click)
    ImageView ivDoubleClick;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("手势操作说明");
    }

    @Override
    protected void initView() {

        Glide.with(this).load(R.mipmap.move).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivMove);
        Glide.with(this).load(R.mipmap.rotate).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivRotate);
        Glide.with(this).load(R.mipmap.scale).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivScale);
        Glide.with(this).load(R.mipmap.double_click).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivDoubleClick);
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
