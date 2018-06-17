package com.gcyh.jiedian.entrance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.DefaultLogin;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LaunchActivity extends AppCompatActivity {

    @BindView(R.id.tv_launch)
    TextView tvLaunch;
    @BindView(R.id.ll_launch)
    LinearLayout llLaunch;

    private boolean isFirst;
    private String token_id;

    private Handler handler = new Handler();
    private MyCountdownTimer countdowntimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);
        llLaunch.setBackgroundResource(R.mipmap.ar_bg);
        isFirst = SPUtil.getBoolean(this, "isFirst", true);
        token_id = SPUtil.getString(this, "token_id", "");

        countdowntimer = new MyCountdownTimer(3000, 1000);
        countdowntimer.start();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                if (isFirst){
                    //第一次登陆
                    Intent intent = new Intent(LaunchActivity.this , SplashActivity.class) ;
                    startActivity(intent);
                    finish();
                }else {
                    //不是第一次登录
                    if (!TextUtils.isEmpty(token_id)){
                        //已登录 -- 判断token是否过期---进入主界面
                        if (NetWorkUtils.isNetworkEnable(LaunchActivity.this)){
                            verifyTokenHttp(token_id) ;
                        }else {
                            Intent intent = new Intent(LaunchActivity.this , MainActivity.class) ;
                            startActivity(intent);
                            finish();
                        }

                    }else {
                        //未登录--跳到登录页面
                        Intent intent = new Intent(LaunchActivity.this , LoginActivity.class) ;
                        startActivity(intent);
                        finish();
                    }
                }

            }
        }, 3000);

    }

    @OnClick(R.id.tv_launch)
    public void onViewClicked() {
        countdowntimer.onFinish();
        if (isFirst){
            //第一次登陆
            Intent intent = new Intent(LaunchActivity.this , SplashActivity.class) ;
            startActivity(intent);
        }else {
            //不是第一次登录
            if (!TextUtils.isEmpty(token_id)){
                //已登录 -- 判断token是否过期---进入主界面
                if (NetWorkUtils.isNetworkEnable(this)){
                    verifyTokenHttp(token_id) ;
                }else {
                    Intent intent = new Intent(LaunchActivity.this , MainActivity.class) ;
                    startActivity(intent);
                    finish();
                }
            }else {
                //未登录--跳到登录页面
                Intent intent = new Intent(LaunchActivity.this , LoginActivity.class) ;
                startActivity(intent);
            }
        }

    }

    //判断token_id  是否过期
    private void verifyTokenHttp(String token_id){

        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.defaultlogin(token_id)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<DefaultLogin>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(LaunchActivity.this, "token获取失败");
                        Intent intent = new Intent(LaunchActivity.this , MainActivity.class) ;
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onNext(DefaultLogin model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(LaunchActivity.this, model.getRntMsg());
                            SPUtil.setString(LaunchActivity.this, "token_id", model.getResponseParams());
                            Intent intent = new Intent(LaunchActivity.this , MainActivity.class) ;
                            startActivity(intent);
                            finish();

                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(LaunchActivity.this, model.getRntMsg());
                        }
                    }
                });


    }

    protected class MyCountdownTimer extends CountDownTimer {

        public MyCountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvLaunch.setText("跳过:" + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            tvLaunch.setText("跳过");
        }

    }

    @Override
    protected void onStop() {
        if (countdowntimer != null){
            countdowntimer.cancel();
        }
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
