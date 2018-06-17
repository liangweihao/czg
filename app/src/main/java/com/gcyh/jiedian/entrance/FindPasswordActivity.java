package com.gcyh.jiedian.entrance;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.ForgetPassword;
import com.gcyh.jiedian.entity.SendCode;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.ResultModel;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.CountDownUtil;
import com.gcyh.jiedian.util.LoadDialog;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.TelNumMatch;
import com.gcyh.jiedian.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FindPasswordActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_find_password_phone)
    EditText etFindPasswordPhone;
    @BindView(R.id.et_find_password_code)
    EditText etFindPasswordCode;
    @BindView(R.id.tv_find_password_send_code)
    TextView tvFindPasswordSendCode;
    @BindView(R.id.et_find_password_new_password)
    EditText etFindPasswordNewPassword;
    @BindView(R.id.et_find_password_next_password)
    EditText etFindPasswordNextPassword;
    @BindView(R.id.btn_find_password_submit)
    Button btnFindPasswordSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("找回密码");
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

    @OnClick({R.id.btn_back, R.id.btn_find_password_submit,R.id.tv_find_password_send_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_find_password_submit:

                String phone1 = etFindPasswordPhone.getText().toString();
                String code = etFindPasswordCode.getText().toString();
                String password = etFindPasswordNewPassword.getText().toString();
                String nextpassword = etFindPasswordNextPassword.getText().toString();
                if (TextUtils.isEmpty(phone1)) {
                    ToastUtil.show(this, "手机号不能为空");
                } else if (TextUtils.isEmpty(code)) {
                    ToastUtil.show(this, "验证码不能为空");
                } else if (TextUtils.isEmpty(password)) {
                    ToastUtil.show(this, "密码不能为空");
                } else if (TextUtils.isEmpty(nextpassword)) {
                    ToastUtil.show(this, "确认密码不能为空");
                }else if (!password.equals(nextpassword)){
                    ToastUtil.show(this, "两次输入的密码不一致");
                }else if (!TelNumMatch.isValidPhoneNumber(phone1)){
                    ToastUtil.show(this, "手机号码不存在");
                }else if (!NetWorkUtils.isNetworkEnable(this)){
                    //网络判断
                } else {
                    //提交
                    submitHttp(phone1 , code , password) ;
                }

                break;
            case R.id.tv_find_password_send_code:
                //发送验证码
                String phone2 = etFindPasswordPhone.getText().toString();
                if (TextUtils.isEmpty(phone2)){
                    ToastUtil.show(FindPasswordActivity.this, "号码不能为空");
                }else if (!TelNumMatch.isValidPhoneNumber(phone2)){
                    ToastUtil.show(FindPasswordActivity.this, "手机号码不存在");
                }else if (!NetWorkUtils.isNetworkEnable(this)){
                    //网络判断
                } else {
                    sendCodeHttp(phone2) ;
                }

                break;
        }
    }

    private void submitHttp(String phone , String code, String password) {
        LoadDialog.show(this, "正在获取数据");
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.forgetpassword(phone , code , password)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<ForgetPassword>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoadDialog.dismiss();
                        ToastUtil.show(FindPasswordActivity.this, "获取数据失败");
                    }

                    @Override
                    public void onNext(ForgetPassword model) {
                        LoadDialog.dismiss();
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(FindPasswordActivity.this, model.getRntMsg());

                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(FindPasswordActivity.this, model.getRntMsg());
                        }

                    }
                });

    }

    private void countDown() {

        new CountDownUtil(tvFindPasswordSendCode)
                .setCountDownMillis(60_000L)//倒计时60000ms
                .setCountDownColor(R.color.white, R.color.color_a3a3a3)//不同状态字体颜色
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone = etFindPasswordPhone.getText().toString();
                        sendCodeHttp(phone) ;
                    }
                })
                .start();

    }

    private void sendCodeHttp(String phone) {
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.sendcode(phone , 2)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<SendCode>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(FindPasswordActivity.this, "获取数据失败");
                    }

                    @Override
                    public void onNext(SendCode model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(FindPasswordActivity.this, model.getRntMsg());
                            //发送验证码成功 ，开启倒计时
                            countDown();
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(FindPasswordActivity.this, model.getRntMsg());
                        }

                    }
                });

    }

}
