package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.MyNoticeRecycleViewAdapter;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.NoticeList;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.LoadDialog;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyNoticeActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.recycleView_my_notice)
    RecyclerView recycleViewMyNotice;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.iv_my_notice_null)
    ImageView ivMyNoticeNull;
    @BindView(R.id.tv_my_notice_null)
    TextView tvMyNoticeNull;
    @BindView(R.id.ll_my_notice_null)
    LinearLayout llMyNoticeNull;

    private String token_id;
    private List<NoticeList.ResponseParamsBean> list = new ArrayList<>();
    private MyNoticeRecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_notice);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("我的关注");
    }

    @Override
    protected void initView() {
        token_id = SPUtil.getString(this, "token_id", "");
        if (NetWorkUtils.isNetworkEnable(this)) {
            setNoticeListHttp(token_id);
        } else {
            llMyNoticeNull.setVisibility(View.VISIBLE);
            ivMyNoticeNull.setImageResource(R.mipmap.internet_null);
            tvMyNoticeNull.setText("网络不给力，点击重新加载");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(MyNoticeActivity.this);
        //设置布局管理器
        recycleViewMyNotice.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        //适配器
        adapter = new MyNoticeRecycleViewAdapter(MyNoticeActivity.this, list);
        recycleViewMyNotice.setAdapter(adapter);

        initRefreshLayout();

    }

    private void setNoticeListHttp(String token_id) {
        LoadDialog.show(this , "正在加载");
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.noticelist(token_id)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<NoticeList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MyNoticeActivity.this, "粉丝列表失败");
                        Log.i("====", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(NoticeList model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MyNoticeActivity.this, model.getRntMsg());
                            list.clear();
                            List<NoticeList.ResponseParamsBean> responseParams = model.getResponseParams();
                            if (responseParams != null) {
                                if (responseParams.size() > 0) {
                                    list.addAll(responseParams);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    llMyNoticeNull.setVisibility(View.VISIBLE);
                                }
                            }


                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MyNoticeActivity.this, model.getRntMsg());
                        }
                        LoadDialog.dismiss();

                    }
                });
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }


    private void initRefreshLayout() {

        //  刷新
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mSmartRefreshLayout.finishRefresh();
//                        mSmartRefreshLayout.finishLoadmore();
                    }
                }, 5000);

            }
        });
        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
//                        mSmartRefreshLayout.finishRefresh();
                        mSmartRefreshLayout.finishLoadmore();
                    }
                }, 5000);

            }
        });

    }

    @OnClick({R.id.btn_back, R.id.ll_my_notice_null})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_my_notice_null:
                if (NetWorkUtils.isNetworkEnable(this)) {
                    setNoticeListHttp(token_id);
                } else {
                    llMyNoticeNull.setVisibility(View.VISIBLE);
                    ivMyNoticeNull.setImageResource(R.mipmap.internet_null);
                    tvMyNoticeNull.setText("网络不给力，点击重新加载");
                }
                break;
        }
    }
}
