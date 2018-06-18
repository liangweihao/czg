package com.gcyh.jiedian.entrance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.SplashViewPagerAdapter;
import com.gcyh.jiedian.util.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Caizhiguang on 2018/4/18.
 */

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_splash_enter)
    TextView tvSplashEnter;
    List<View> list = new ArrayList<View>();

    Timer timer = new Timer();
    private int recLen = 5;//跳过倒计时提示5秒


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        // 实际显示图片的数据源(记录了所有图片的资源id，图片最好都放在xxhdpi下面)
        int[] imgsIcon = new int[]{R.mipmap.ar_bg, R.mipmap.ar_bg,
                R.mipmap.ar_bg};

        // 动态加载视图
        for (int i = 0; i < imgsIcon.length; i++) {
            // 将实际的图片动态添加到ImageView控件中
            ImageView imageView = new ImageView(this);
            // 设置属性
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(imgsIcon[i]);
            // 保持不形变的情况等比例拉伸图片，结果放在容器中间FIT_CENTER
            // 图片全部拉伸并且充满容器，但是可能会发生图片形变失真,FIT_XY
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            // 放入到容器当中
            list.add(imageView);
        }

        //适配器
        SplashViewPagerAdapter adapter = new SplashViewPagerAdapter(SplashActivity.this, list);
        viewPager.setAdapter(adapter);

        //ViewPager监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    tvSplashEnter.setVisibility(View.VISIBLE);
                } else {
                    tvSplashEnter.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.tv_splash_enter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_splash_enter:
                //跳转到---登录页面
                SPUtil.setBoolean(this, "isFirst", false);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();

                break;

        }
    }
}
