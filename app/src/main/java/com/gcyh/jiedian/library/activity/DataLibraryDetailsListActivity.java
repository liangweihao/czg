package com.gcyh.jiedian.library.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.DataLibraryDetailsListAdapter;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.LibraryDataListDetails;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataLibraryDetailsListActivity extends BaseActivity {


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
    @BindView(R.id.recycleViewDataLibraryDetailsList)
    RecyclerView recycleViewDataLibraryDetailsList;
    @BindView(R.id.ll_datalibrarydetails_null)
    LinearLayout llDatalibrarydetailsNull;

    public static List<LibraryDataListDetails.ResponseParamsBean> list = new ArrayList<>();
    private DataLibraryDetailsListAdapter adapter;
    private int collect;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_data_library_details_list);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("建筑条例");
        ivToolbarRight.setVisibility(View.VISIBLE);
        id = getIntent().getIntExtra("id", 0);
        collect = getIntent().getIntExtra("collect", 0);
        if (collect == 1) {
            //已收藏
            ivToolbarRight.setImageResource(R.mipmap.data_library_collect_select);
        } else {
            //未收藏
            ivToolbarRight.setImageResource(R.mipmap.data_library_collect_normal);
        }
        if (NetWorkUtils.isNetworkEnable(this)) {
            libraryDataListDetailsHttp(id);
        } else {
            llDatalibrarydetailsNull.setVisibility(View.VISIBLE);
        }
    }

    private void libraryDataListDetailsHttp(int id) {
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.librarydatalistdetails(id)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<LibraryDataListDetails>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(DataLibraryDetailsListActivity.this, "常用资料库获取失败");
                        Log.i("====", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(LibraryDataListDetails model) {
                        if (model.getRntCode().equals("OK")) {
                            list.clear();
                            llDatalibrarydetailsNull.setVisibility(View.GONE);
                            ToastUtil.show(DataLibraryDetailsListActivity.this, model.getRntMsg());
                            List<LibraryDataListDetails.ResponseParamsBean> responseParams = model.getResponseParams();
                            list.addAll(responseParams);
                            adapter.notifyDataSetChanged();
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(DataLibraryDetailsListActivity.this, model.getRntMsg());
                        }

                    }
                });
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recycleViewDataLibraryDetailsList.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new DataLibraryDetailsListAdapter(this, list);
        recycleViewDataLibraryDetailsList.setAdapter(adapter);

    }


    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    @OnClick({R.id.btn_back, R.id.iv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_toolbar_right:
                if (collect == 1) {
                    //取消收藏
                    ivToolbarRight.setImageResource(R.mipmap.data_library_collect_normal);
                    Bundle bundle = new Bundle() ;
                    bundle.putInt("type" , 3);
                    bundle.putInt("id" , id);
                    EventBusUtil.postEvent(EventBusCode.DELETE_COLLECT , bundle);
                } else {
                    //添加收藏
                    ivToolbarRight.setImageResource(R.mipmap.data_library_collect_select);
                    Bundle bundle = new Bundle() ;
                    bundle.putInt("type" , 3);
                    bundle.putInt("id" , id);
                    EventBusUtil.postEvent(EventBusCode.ADD_COLLECT , bundle);
                }
                break;
        }
    }
}
