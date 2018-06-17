package com.gcyh.jiedian.entrance;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.Login;
import com.gcyh.jiedian.entity.SendCode;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.TelNumMatch;
import com.gcyh.jiedian.util.ToastUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_forget_password)
    TextView tvLoginForgetPassword;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.ll_login_weixin)
    LinearLayout llLoginWeixin;
    @BindView(R.id.ll_login_qq)
    LinearLayout llLoginQq;

    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @Override
    public void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.color_00000000);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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


    @OnClick({R.id.btn_login, R.id.tv_login_forget_password, R.id.tv_login_register, R.id.ll_login_weixin, R.id.ll_login_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //登录
                String phone = etLoginPhone.getText().toString();
                String password = etLoginPassword.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show(this, "手机号不能为空");
                } else if (TextUtils.isEmpty(password)) {
                    ToastUtil.show(this, "密码不能为空");
                } else if (!TelNumMatch.isValidPhoneNumber(phone)) {
                    ToastUtil.show(this, "手机号码不存在");
                } else if (!NetWorkUtils.isNetworkEnable(this)) {

                } else {
                    loginHttp(phone, password);
                }
                break;
            case R.id.tv_login_forget_password:
                //忘记密码
                startIntent(FindPasswordActivity.class);

                break;
            case R.id.tv_login_register:
                //注册
                startIntent(RegisterActivity.class);
                break;
            case R.id.ll_login_weixin:
                //微信登录
                break;
            case R.id.ll_login_qq:
                //QQ登录
                break;
        }
    }

    private void loginHttp(final String phone, String password) {
//        LoadDialog.show(this, "正在获取数据");
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.login(phone, password)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Login>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(LoginActivity.this, "获取失败");
                    }

                    @Override
                    public void onNext(Login model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(LoginActivity.this, model.getRntMsg());
                            SPUtil.setString(LoginActivity.this, "token_id", model.getResponseParams()); //保存token
                            SPUtil.setString(LoginActivity.this, "phone", phone);  //保存手机号
                            startIntentAndFinish(MainActivity.class);

                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(LoginActivity.this, model.getRntMsg());
                        }

                    }
                });

    }


}

