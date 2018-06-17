package com.gcyh.jiedian.library.activity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.http.UrlAll;
import com.itheima.library.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoteViewActivity extends BaseActivity {

    @BindView(R.id.photoView)
    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_phote_view);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        String url = getIntent().getStringExtra("url");
        Glide.with(this).load(UrlAll.DOWN_LOAD_IMAGE+"image/"+url+".png").diskCacheStrategy(DiskCacheStrategy.ALL).into(photoView);
        photoView.enable();
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }
}
