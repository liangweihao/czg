package com.gcyh.jiedian.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.util.EventBusCode;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;


/**
 * activity基类
 * caizhiguang
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = getClass().getName();

    private SystemBarTintManager tintManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.onCreate(savedInstanceState, TAG);


        ButterKnife.bind(this);

        this.initBack();
        this.initStatusBar();
        this.initPresenter();
        this.initView();
        this.initValue();

        //注册EventBus通信
        EventBus.getDefault().register(this);
        ActManager.getAppManager().addActivity(this);
    }

    /**
     * 程序执行入口 调用完Activity.onCreate之后马上调用
     *
     * @param savedInstanceState
     * @param tag
     */
    protected abstract void onCreate(Bundle savedInstanceState, String tag);

    protected abstract void initView();

    protected abstract void initPresenter();

    protected abstract void initValue();

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public void finish() {
        super.finish();
        ActManager.getAppManager().finishActivity(this);
    }

    public void initBack() {
      /*  View back = findViewById(R.id.btn_back);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }*/
    }

    public void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setNavigationBarTintEnabled(false);
        tintManager.setStatusBarTintResource(R.color.color_333333);
    }

    public void setStatusBarTintResource(int colorRes) {
        if (tintManager != null) {
            tintManager.setStatusBarTintResource(colorRes);
        }
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


    /**
     * 启动页面跳转
     *
     * @param cls cls
     */
    public void startIntent(Class<?> cls) {
        this.startIntent(this, cls);
    }

    /**
     * 启动页面跳转
     *
     * @param cls cls
     */
    public void startIntent(Class<?> cls, Bundle bundle) {
        this.startIntent(this, cls, bundle);
    }

    /**
     * 启动页面跳转
     *
     * @param context 上下文对象
     * @param cls     cls
     */
    public void startIntent(Context context, Class<?> cls) {
        this.startIntent(context, cls, null);
    }

    /**
     * 启动页面跳转
     *
     * @param context 上下文对象
     * @param cls     cls
     * @param bundle  数据集
     */
    public void startIntent(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        context.startActivity(intent);
    }

    public void startIntentAndFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    /**
     * 启动页面跳转 附带返回
     *
     * @param cls         cls
     * @param requestCode 请求code
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 启动页面跳转 附带返回
     *
     * @param cls         cls
     * @param bundle      数据集
     * @param requestCode 请求code
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * eventBus可以重写，用来处理接收消息，
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(Object event) {
        if (event instanceof Bundle) {
            int eventCode = ((Bundle) event).getInt(EventBusCode.EVENT_BUS_CODE);
            onEventMainThread(eventCode, (Bundle) event);
        }
    }

    /**
     * eventBus可以重写，用来处理接收消息，
     *
     * @param eventCode
     */
    public void onEventMainThread(int eventCode, Bundle bundle) {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销EventBus
        EventBus.getDefault().unregister(this);
    }

    /**
     * 设置字体不随系统字体改变
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                //hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN){
            BaseActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
