package com.gcyh.jiedian.entrance;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.Register;
import com.gcyh.jiedian.entity.SendCode;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.CountDownUtil;
import com.gcyh.jiedian.util.DialogUtil;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.TelNumMatch;
import com.gcyh.jiedian.util.ToastUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.ev_register_work)
    EditText evRegisterWork;
    @BindView(R.id.iv_register_work)
    ImageView ivRegisterWork;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_code)
    EditText etRegisterCode;
    @BindView(R.id.tv_register_send_code)
    TextView tvRegisterSendCode;
    @BindView(R.id.et_register_unicoding)
    EditText etRegisterUnicoding;
    @BindView(R.id.et_register_password)
    EditText etRegisterPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.cb_register_agree)
    CheckBox cbRegisterAgree;
    @BindView(R.id.tv_register_privacy)
    TextView tvRegisterPrivacy;
    @BindView(R.id.tv_register_login)
    TextView tvRegisterLogin;
    @BindView(R.id.et_register_repeat_password)
    EditText etRegisterRepeatPassword;
    private SystemBarTintManager tintManager;

    String[] strings = new String[]{"建筑设计师", "学生", "教师", "材料商", "施工人员", "其他"};

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {

        setContentView(R.layout.activity_register);
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

    @OnClick({R.id.tv_register_send_code, R.id.btn_register, R.id.tv_register_privacy, R.id.tv_register_login,R.id.ll_register_work})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_send_code:
                //发送验证码
                String phone1 = etRegisterPhone.getText().toString();
                if (TextUtils.isEmpty(phone1)){
                    ToastUtil.show(RegisterActivity.this, "号码不能为空");
                }else if (!TelNumMatch.isValidPhoneNumber(phone1)){
                    ToastUtil.show(RegisterActivity.this, "手机号码不存在");
                }else if (!NetWorkUtils.isNetworkEnable(this)){
                    //网络判断
                } else {
                    sendCodeHttp(phone1);
                }

                break;
            case R.id.btn_register:
                //立即注册
                String password = etRegisterPassword.getText().toString();
                String repeatPassword = etRegisterRepeatPassword.getText().toString();
                String phone2 = etRegisterPhone.getText().toString();
                if (TextUtils.isEmpty(etRegisterName.getText().toString())) {
                    ToastUtil.show(this, "昵称不能为空");
                } else if (TextUtils.isEmpty(evRegisterWork.getText().toString())) {
                    ToastUtil.show(this, "职业不能为空");
                } else if (TextUtils.isEmpty(etRegisterPhone.getText().toString())) {
                    ToastUtil.show(this, "手机号不能为空");
                } else if (TextUtils.isEmpty(etRegisterCode.getText().toString())) {
                    ToastUtil.show(this, "验证码不能为空");
                } else if (TextUtils.isEmpty(etRegisterPassword.getText().toString())) {
                    ToastUtil.show(this, "密码不能为空");
                } else if (TextUtils.isEmpty(etRegisterRepeatPassword.getText().toString())) {
                    ToastUtil.show(this, "确认密码不能为空");
                }else if (!cbRegisterAgree.isChecked()){
                    ToastUtil.show(this, "请勾选隐私条款");
                }else if (!password.equals(repeatPassword)){
                    ToastUtil.show(this, "两次输入的密码不一致");
                }else if (!TelNumMatch.isValidPhoneNumber(phone2)){
                    ToastUtil.show(this, "手机号码不存在");
                }else if (!NetWorkUtils.isNetworkEnable(this)){
                    //网络判断
                } else {
                    //注册
                    registerHttp() ;

                }
                break;
            case R.id.tv_register_privacy:
                //隐私条款
                break;
            case R.id.ll_register_work:
                //职业
                DialogUtil.showListDialog(this, "list", strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        evRegisterWork.setText(strings[i]);
                    }
                });
                break;
            case R.id.tv_register_login:
                //立即登录
                startIntent(LoginActivity.class);
                break;
        }
    }

    private void countDown() {

        new CountDownUtil(tvRegisterSendCode)
                .setCountDownMillis(60_000L)//倒计时60000ms
                .setCountDownColor(R.color.white, R.color.color_a3a3a3)//不同状态字体颜色
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone = etRegisterPhone.getText().toString();
                        sendCodeHttp(phone);
                    }
                })
                .start();

    }

    private void sendCodeHttp(String phone) {
//        LoadDialog.show(this, "正在获取数据");
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.sendcode(phone, 1)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<SendCode>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(RegisterActivity.this, "获取验证码失败");
                    }

                    @Override
                    public void onNext(SendCode model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(RegisterActivity.this, model.getRntMsg());
                            //发送验证码成功 ，开启倒计时
                            countDown();
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(RegisterActivity.this, model.getRntMsg());
                        }

                    }
                });

    }

    private void registerHttp(){
        String name = etRegisterName.getText().toString();
        String work = evRegisterWork.getText().toString();
        String phone = etRegisterPhone.getText().toString();
        String code = etRegisterCode.getText().toString();
//        String unicoding = etRegisterUnicoding.getText().toString();  //直通码（暂时不加）
        String password = etRegisterPassword.getText().toString();

        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.register(name, work,phone,code,password)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Register>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(RegisterActivity.this, "网络错误");
                    }

                    @Override
                    public void onNext(Register model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(RegisterActivity.this, model.getRntMsg());
                            startIntentAndFinish(LoginActivity.class);
                        } else if (model.getRntCode().equals("ERROR")) {
                            ToastUtil.show(RegisterActivity.this, model.getRntMsg());
                        }

                    }
                });
    }

}
