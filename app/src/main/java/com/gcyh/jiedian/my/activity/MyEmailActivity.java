package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyEmailActivity extends BaseActivity {


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
    @BindView(R.id.et_my_e_mail_name)
    EditText etMyEMailName;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_email);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("邮箱地址");
        txtToolbarRight.setVisibility(View.VISIBLE);
        txtToolbarRight.setText("完成");
        etMyEMailName.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        String email = getIntent().getStringExtra("email");
        etMyEMailName.setText(email);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    @OnClick({R.id.btn_back, R.id.txt_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.txt_toolbar_right:
                //完成
                Bundle bundle = new Bundle() ;
                bundle.putString("information_email" , etMyEMailName.getText().toString());
                EventBusUtil.postEvent(EventBusCode.INFORMATION_EMAIL , bundle);
                finish();
                break;
        }
    }
}
