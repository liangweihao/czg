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
import com.gcyh.jiedian.adapter.MyFansRecycleViewAdapter;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.FanList;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
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

public class MyFansActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;
    @BindView(R.id.recycleViewFans)
    RecyclerView recycleViewFans;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_my_fan_null)
    ImageView ivMyFanNull;
    @BindView(R.id.tv_my_fan_null)
    TextView tvMyFanNull;
    @BindView(R.id.ll_my_fans_null)
    LinearLayout llMyFansNull;

    private List<FanList.ResponseParamsBean> list = new ArrayList<>();
    private String token_id;
    private MyFansRecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_fans);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("我的粉丝");
    }

    @Override
    protected void initView() {

        token_id = SPUtil.getString(this, "token_id", "");
        if (NetWorkUtils.isNetworkEnable(this)) {
            setFanListHttp(token_id);
        } else {
            llMyFansNull.setVisibility(View.VISIBLE);
            ivMyFanNull.setImageResource(R.mipmap.internet_null);
            tvMyFanNull.setText("网络不给力，点击重新加载");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(MyFansActivity.this);
        //设置布局管理器
        recycleViewFans.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //适配器
        adapter = new MyFansRecycleViewAdapter(MyFansActivity.this, list);
        recycleViewFans.setAdapter(adapter);

        initRefreshLayout();
    }

    private void setFanListHttp(String token_id) {
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.fanlist(token_id)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<FanList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MyFansActivity.this, "粉丝列表失败");
                        Log.i("====", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(FanList model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MyFansActivity.this, model.getRntMsg());
                            list.clear();
                            List<FanList.ResponseParamsBean> responseParams = model.getResponseParams();
                            if (responseParams != null) {
                                if (responseParams.size() > 0) {
                                    list.addAll(responseParams);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    llMyFansNull.setVisibility(View.VISIBLE);
                                }
                            }


                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MyFansActivity.this, model.getRntMsg());
                        }

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

    @OnClick({R.id.btn_back, R.id.ll_my_fans_null})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_my_fans_null:
                if (NetWorkUtils.isNetworkEnable(this)) {
                    setFanListHttp(token_id);
                } else {
                    llMyFansNull.setVisibility(View.VISIBLE);
                    ivMyFanNull.setImageResource(R.mipmap.internet_null);
                    tvMyFanNull.setText("网络不给力，点击重新加载");
                }
                break;
        }
    }
}
