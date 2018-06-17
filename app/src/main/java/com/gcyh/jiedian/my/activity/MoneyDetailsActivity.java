package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoneyDetailsActivity extends BaseActivity {


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
    @BindView(R.id.tv_money_details_title)
    TextView tvMoneyDetailsTitle;
    @BindView(R.id.tv_money_details_number)
    TextView tvMoneyDetailsNumber;
    @BindView(R.id.tv_money_details_type)
    TextView tvMoneyDetailsType;
    @BindView(R.id.tv_money_details_type_contet)
    TextView tvMoneyDetailsTypeContet;
    @BindView(R.id.ll_money_details_type)
    LinearLayout llMoneyDetailsType;
    @BindView(R.id.tv_money_details_ziyuan_type)
    TextView tvMoneyDetailsZiyuanType;
    @BindView(R.id.tv_money_details_ziyuan_type_content)
    TextView tvMoneyDetailsZiyuanTypeContent;
    @BindView(R.id.ll_money_details_ziyuan_type)
    LinearLayout llMoneyDetailsZiyuanType;
    @BindView(R.id.tv_money_details_buy_time)
    TextView tvMoneyDetailsBuyTime;
    @BindView(R.id.tv_money_details_buy_time_number)
    TextView tvMoneyDetailsBuyTimeNumber;
    @BindView(R.id.ll_money_details_buy_tiem)
    LinearLayout llMoneyDetailsBuyTiem;
    @BindView(R.id.tv_money_details_bianhao)
    TextView tvMoneyDetailsBianhao;
    @BindView(R.id.tv_money_details_bianhao_number)
    TextView tvMoneyDetailsBianhaoNumber;
    @BindView(R.id.ll_money_details_bianhao)
    LinearLayout llMoneyDetailsBianhao;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_money_details);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("线币明细");
    }

    @Override
    protected void initView() {

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
