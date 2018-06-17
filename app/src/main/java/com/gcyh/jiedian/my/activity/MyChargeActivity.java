package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyChargeActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.rb_one)
    RadioButton rbOne;
    @BindView(R.id.rb_two)
    RadioButton rbTwo;
    @BindView(R.id.rb_three)
    RadioButton rbThree;
    @BindView(R.id.rb_four)
    RadioButton rbFour;
    @BindView(R.id.rb_five)
    RadioButton rbFive;
    @BindView(R.id.rb_six)
    RadioButton rbSix;
    @BindView(R.id.rb_seven)
    RadioButton rbSeven;
    @BindView(R.id.rb_eight)
    RadioButton rbEight;
    @BindView(R.id.radioGroupCharge1)
    RadioGroup radioGroupCharge1;
    @BindView(R.id.radioGroupCharge2)
    RadioGroup radioGroupCharge2;
    @BindView(R.id.radioGroupCharge3)
    RadioGroup radioGroupCharge3;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;
    @BindView(R.id.rb_nine)
    RadioButton rbNine;


    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_charge);
        ButterKnife.bind(this);
        radioGroupCharge1.setOnCheckedChangeListener(new OnCharge1CheckedChangeListener());
        radioGroupCharge2.setOnCheckedChangeListener(new OnCharge2CheckedChangeListener());
        radioGroupCharge3.setOnCheckedChangeListener(new OnCharge3CheckedChangeListener());
        rbOne.setChecked(true);
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("我要充值");

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    @OnClick({R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }


    private class OnCharge1CheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int position) {
            switch (position) {
                case R.id.rb_one:
                    if (rbOne.isChecked()) {
                        radioGroupCharge2.clearCheck();
                        radioGroupCharge3.clearCheck();
                    }
                    break;
                case R.id.rb_two:
                    if (rbTwo.isChecked()) {
                        radioGroupCharge2.clearCheck();
                        radioGroupCharge3.clearCheck();
                    }
                    break;
                case R.id.rb_three:
                    if (rbThree.isChecked()) {
                        radioGroupCharge2.clearCheck();
                        radioGroupCharge3.clearCheck();
                    }
                    break;
            }
        }
    }

    private class OnCharge2CheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int position) {
            switch (position) {
                case R.id.rb_four:
                    if (rbFour.isChecked()) {
                        radioGroupCharge1.clearCheck();
                        radioGroupCharge3.clearCheck();
                    }
                    break;
                case R.id.rb_five:
                    if (rbFive.isChecked()) {
                        radioGroupCharge1.clearCheck();
                        radioGroupCharge3.clearCheck();
                    }
                    break;
                case R.id.rb_six:
                    if (rbSix.isChecked()) {
                        radioGroupCharge1.clearCheck();
                        radioGroupCharge3.clearCheck();
                    }
                    break;
            }
        }
    }

    private class OnCharge3CheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int position) {
            switch (position) {
                case R.id.rb_seven:
                    if (rbSeven.isChecked()) {
                        radioGroupCharge1.clearCheck();
                        radioGroupCharge2.clearCheck();
                    }
                    break;
                case R.id.rb_eight:
                    if (rbEight.isChecked()) {
                        radioGroupCharge1.clearCheck();
                        radioGroupCharge2.clearCheck();
                    }
                    break;
                case R.id.rb_nine:
                    if (rbNine.isChecked()) {
                        radioGroupCharge1.clearCheck();
                        radioGroupCharge2.clearCheck();
                    }
                    break;
            }
        }
    }


}
