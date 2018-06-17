package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.ActManager;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entrance.LoginActivity;
import com.gcyh.jiedian.util.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.ll_setting_help)
    LinearLayout llSettingHelp;
    @BindView(R.id.ll_setting_feed_back)
    LinearLayout llSettingFeedBack;
    @BindView(R.id.ll_setting_cache)
    LinearLayout llSettingCache;
    @BindView(R.id.ll_setting_version)
    LinearLayout llSettingVersion;
    @BindView(R.id.ll_setting_about)
    LinearLayout llSettingAbout;
    @BindView(R.id.btn_setting_switch)
    TextView btnSettingSwitch;
    @BindView(R.id.btn_setting_exit_login)
    TextView btnSettingExitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("设置");

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    @OnClick({R.id.btn_back, R.id.ll_setting_help, R.id.ll_setting_feed_back, R.id.ll_setting_cache, R.id.ll_setting_version, R.id.ll_setting_about, R.id.btn_setting_switch, R.id.btn_setting_exit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_setting_help:
                //帮助及其功能介绍
                break;
            case R.id.ll_setting_feed_back:
                //意见反馈
                startIntent(FeedBackActivity.class);
                break;
            case R.id.ll_setting_cache:
                //清理缓存
                break;
            case R.id.ll_setting_version:
                //检查版本
                break;
            case R.id.ll_setting_about:
                //关于我们
                break;
            case R.id.btn_setting_switch:
                //切换账号
                startIntent(LoginActivity.class);
                break;
            case R.id.btn_setting_exit_login:
                //退出登录
                SPUtil.remove(this , "token_id");
                ActManager.getAppManager().AppExit(SettingActivity.this);
                startIntent(LoginActivity.class);
                break;
        }
    }
}
