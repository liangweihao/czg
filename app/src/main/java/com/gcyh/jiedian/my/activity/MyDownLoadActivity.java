package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.my.fragment.MyDownLoadFinishFragment;
import com.gcyh.jiedian.my.fragment.MyDownLoadingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyDownLoadActivity extends BaseActivity {


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
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.tv_my_downloading)
    TextView tvMyDownloading;
    @BindView(R.id.iv_my_downloading)
    ImageView ivMyDownloading;
    @BindView(R.id.ll_my_downloading)
    LinearLayout llMyDownloading;
    @BindView(R.id.tv_my_finish_download)
    TextView tvMyFinishDownload;
    @BindView(R.id.iv_my_finish_download)
    ImageView ivMyFinishDownload;
    @BindView(R.id.ll_my_finish_download)
    LinearLayout llMyFinishDownload;

    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_down_load);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("下载管理");
        tvMyDownloading.setTextColor(this.getResources().getColor(R.color.color_de171e));
        ivMyDownloading.setBackgroundColor(this.getResources().getColor(R.color.color_de171e));
        prepareFragments();
        showFragment(1);
    }

    @OnClick({R.id.btn_back, R.id.ll_my_downloading, R.id.ll_my_finish_download})
    public void onViewClicked(View view) {
        setNormalText() ;
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_my_downloading:
                tvMyDownloading.setTextColor(this.getResources().getColor(R.color.color_de171e));
                ivMyDownloading.setBackgroundColor(this.getResources().getColor(R.color.color_de171e));
                showFragment(0);
                break;
            case R.id.ll_my_finish_download:
                tvMyFinishDownload.setTextColor(this.getResources().getColor(R.color.color_de171e));
                ivMyFinishDownload.setBackgroundColor(this.getResources().getColor(R.color.color_de171e));
                showFragment(1);
                break;
        }
    }

    private void setNormalText(){
        tvMyDownloading.setTextColor(this.getResources().getColor(R.color.color_323232));
        ivMyDownloading.setBackgroundColor(this.getResources().getColor(R.color.white));
        tvMyFinishDownload.setTextColor(this.getResources().getColor(R.color.color_323232));
        ivMyFinishDownload.setBackgroundColor(this.getResources().getColor(R.color.white));
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    /**
     * 准备所有的Fragment
     */
    private void prepareFragments() {
        fragments = new ArrayList<>();
        fragments.add(new MyDownLoadingFragment());
        fragments.add(new MyDownLoadFinishFragment());
    }

    private void showFragment(int position) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //1.先隐藏其他的
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (i == position) {
                if (fragment.isAdded()) {
                    transaction.show(fragment);
                } else {
                    //add
                    transaction.add(R.id.fl_container, fragment);
                }
            } else {
                if (fragment.isAdded()) {
                    transaction.hide(fragment);
                }
            }
        }
        //commit
        transaction.commitAllowingStateLoss();

    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
