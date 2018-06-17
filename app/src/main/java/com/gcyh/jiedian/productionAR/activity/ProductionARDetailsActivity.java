package com.gcyh.jiedian.productionAR.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductionARDetailsActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_production_ar_details_pic)
    ImageView ivProductionArDetailsPic;
    @BindView(R.id.tv_production_ar_details_fangansheji)
    TextView tvProductionArDetailsFangansheji;
    @BindView(R.id.tv_production_ar_details_time)
    TextView tvProductionArDetailsTime;
    @BindView(R.id.tv_production_ar_details_shigongtushenhua)
    TextView tvProductionArDetailsShigongtushenhua;
    @BindView(R.id.tv_production_ar_details_shigongdanwei)
    TextView tvProductionArDetailsShigongdanwei;
    @BindView(R.id.tv_production_ar_details_project_introduce)
    TextView tvProductionArDetailsProjectIntroduce;
    @BindView(R.id.iv_production_ar_details_ar)
    ImageView ivProductionArDetailsAr;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_production_ardetails);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("天津假日酒店样板房");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initPresenter() {
        Glide.with(ProductionARDetailsActivity.this).load("").placeholder(R.mipmap.ar_bg).error(R.mipmap.ar_bg).into(ivProductionArDetailsPic) ;
    }

    @Override
    protected void initValue() {

    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {

        finish();

    }
}
