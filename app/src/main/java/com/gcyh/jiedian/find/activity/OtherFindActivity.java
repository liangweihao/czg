package com.gcyh.jiedian.find.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.OtherFindRecycleViewAdapter;
import com.gcyh.jiedian.adapter.OtherFindRecycleViewAdapter1;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.OtherFindList;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OtherFindActivity extends BaseActivity {


    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.recycleView_Other_Find)
    RecyclerView recycleViewOtherFind;

    private List<String> mDatas;
    private  List<OtherFindList.ResponseParamsBean.UserPostFabulousDTOtListBean> list = new ArrayList<>() ;
    private OtherFindRecycleViewAdapter adapter;
    private OtherFindList.ResponseParamsBean.UserInfoDTOBean userInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_other_find);
        ButterKnife.bind(this);
        String token_id = SPUtil.getString(this, "token_id", "");
        String userCode = getIntent().getStringExtra("userCode");
        String nameNick = getIntent().getStringExtra("nameNick");
        if (NetWorkUtils.isNetworkEnable(this)){
            setOtherFindHttp(token_id ,userCode) ;
        }
        tvToolbarTitle.setText(nameNick+"的主题");
    }

    //访问网络
    private void setOtherFindHttp(String token_id, String userCode) {
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.otherfindlist(token_id ,userCode)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<OtherFindList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(OtherFindActivity.this, "获取主题列表失败");
                        Log.i("====", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(OtherFindList model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(OtherFindActivity.this, model.getRntMsg());
                              list.clear();
                            userInfo = model.getResponseParams().getUserInfoDTO();
                            List<OtherFindList.ResponseParamsBean.UserPostFabulousDTOtListBean> userPostList = model.getResponseParams().getUserPostFabulousDTOtList();

                            if (userPostList != null || userInfo != null) {
                                list.addAll(userPostList) ;
                                adapter.notifyDataSetChanged();
                            }
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(OtherFindActivity.this, model.getRntMsg());
                        }

                    }
                });
    }

    @Override
    protected void initView() {
        initRefreshLayout();
    }

    @Override
    protected void initPresenter() {
        initData(10);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recycleViewOtherFind.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new OtherFindRecycleViewAdapter(this, userInfo , list);
//        OtherFindRecycleViewAdapter1 adapter1 = new OtherFindRecycleViewAdapter1(R.layout.activity_other_find_item , list) ;
        recycleViewOtherFind.setAdapter(adapter);
    }

    @Override
    protected void initValue() {

    }


    private void initRefreshLayout() {

        mSmartRefreshLayout.setEnableRefresh(false);

        //加载更多
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mSmartRefreshLayout.finishLoadmore();
                    }
                }, 5000);

            }
        });

    }

    private void initData(int value) {

        mDatas = new ArrayList<String>();
        for (int i = 0; i < value; i++) {
            mDatas.add("世界你好" + i);
        }

    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
