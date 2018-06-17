package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.util.SPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyNameActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.txt_toolbar_right)
    TextView txtToolbarRight;
    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;
    @BindView(R.id.et_my_name_name)
    EditText etMyNameName;
    @BindView(R.id.iv_my_name_delete)
    ImageView ivMyNameDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_name);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("修改昵称");
        txtToolbarRight.setText("完成");
        txtToolbarRight.setVisibility(View.VISIBLE);
        String name = getIntent().getStringExtra("name");
        etMyNameName.setText(name);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    @OnClick({R.id.btn_back, R.id.iv_my_name_delete, R.id.txt_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_my_name_delete:
                //清空
                etMyNameName.setText("");
                break;
            case R.id.txt_toolbar_right:
                //完成
                Bundle bundle = new Bundle();
                bundle.putString("information_name", etMyNameName.getText().toString());
                EventBusUtil.postEvent(EventBusCode.INFORMATION_NAME, bundle);
                finish();
                break;
        }
    }
}
