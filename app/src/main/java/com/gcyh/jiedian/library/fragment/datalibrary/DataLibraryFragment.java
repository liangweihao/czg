package com.gcyh.jiedian.library.fragment.datalibrary;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.DataLibraryItemRecyclerAdapter;
import com.gcyh.jiedian.base.BaseFragment;
import com.gcyh.jiedian.entity.LibraryDataList;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.SpaceItemDecoration;
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
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Caizhiguang on 2018/4/19.
 */

public class DataLibraryFragment extends BaseFragment {


    @BindView(R.id.recycleViewDataLibraryItem)
    RecyclerView recycleViewDataLibrary;
    @BindView(R.id.smart_refresh_data)
    SmartRefreshLayout mSmartRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.tv_datalibrary_normal)
    TextView tvDatalibraryNormal;
    @BindView(R.id.iv_datalibrary_normal)
    ImageView ivDatalibraryNormal;
    @BindView(R.id.ll_datalibrary_normal)
    LinearLayout llDatalibraryNormal;
    @BindView(R.id.tv_datalibrary_data)
    TextView tvDatalibraryData;
    @BindView(R.id.iv_datalibrary_data)
    ImageView ivDatalibraryData;
    @BindView(R.id.ll_datalibrary_data)
    LinearLayout llDatalibraryData;
    @BindView(R.id.tv_datalibrary_material)
    TextView tvDatalibraryMaterial;
    @BindView(R.id.iv_datalibrary_material)
    ImageView ivDatalibraryMaterial;
    @BindView(R.id.ll_datalibrary_material)
    LinearLayout llDatalibraryMaterial;
    @BindView(R.id.tv_datalibrary_code)
    TextView tvDatalibraryCode;
    @BindView(R.id.iv_datalibrary_code)
    ImageView ivDatalibraryCode;
    @BindView(R.id.ll_datalibrary_code)
    LinearLayout llDatalibraryCode;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.ll_datalibrary_null)
    LinearLayout llDatalibraryNull;

    private GridLayoutManager manager;
    private String token_id;
    private String standard = null;
    private String data = null ;
    private String materialConsumption = null;
    private String symbolCode = null ;
    private int pages = 1 ;
    private int row = 10 ;

    private  List<LibraryDataList.ResponseParamsBean> list = new ArrayList<>() ;
    private DataLibraryItemRecyclerAdapter adapter;


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_datalibrary;
    }

    @Override
    protected void initPresenter() {
        token_id = SPUtil.getString(getActivity(), "token_id", "");

    }

    private void libraryDataListHttp(String token_id, String standard, String data, String materialConsumption, String symbolCode) {

        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.librarydatalist(token_id ,pages ,row , standard , data , materialConsumption , symbolCode)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<LibraryDataList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(getActivity(), "常用资料库获取失败");
                        Log.i("====", "onError: "+e.toString());
                    }

                    @Override
                    public void onNext(LibraryDataList model) {
                        if (model.getRntCode().equals("OK")) {
                            list.clear();
                            ToastUtil.show(getActivity(), model.getRntMsg());
                            List<LibraryDataList.ResponseParamsBean> responseParams = model.getResponseParams();
                            if (responseParams != null){
                                if (responseParams.size() > 0){
                                    list.addAll(responseParams) ;
                                    adapter.notifyDataSetChanged();
                                }
                            }


                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(getActivity(), model.getRntMsg());
                        }

                    }
                });

    }

    @Override
    protected void initView() {

        initRefreshLayout();
    }

    @Override
    protected void initValue() {


        int type = SPUtil.getInt(getActivity(), "LIBRARY_DATA_CODE", 1);
        if (type == 1) {
            recycleViewDataLibrary.setBackgroundResource(R.color.white);
            view.setVisibility(View.VISIBLE);
        } else {
            recycleViewDataLibrary.setBackgroundResource(R.color.color_f9f9f9);
            view.setVisibility(View.GONE);
        }
        manager = new GridLayoutManager(getActivity(), type);
        recycleViewDataLibrary.setLayoutManager(manager);
        recycleViewDataLibrary.addItemDecoration(new SpaceItemDecoration(15, 20));
        recycleViewDataLibrary.setHasFixedSize(true);
        adapter = new DataLibraryItemRecyclerAdapter(getActivity(), list, type);
        recycleViewDataLibrary.setAdapter(adapter);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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


    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {
        if (eventCode == EventBusCode.CODE_LIBRARY_DATA) {
            int type = SPUtil.getInt(getActivity(), "LIBRARY_DATA_CODE", 1);
            if (type == 1) {
                changeShowItemCount(2);
            } else if (type == 2) {
                changeShowItemCount(1);
            }
        }else if (eventCode == EventBusCode.LIBRARY_DATA_UPDATE){
            //更新收藏
            if (NetWorkUtils.isNetworkEnable(getActivity())) {
                libraryDataListHttp(token_id, standard, data, materialConsumption, symbolCode);
            } else {
                llDatalibraryNull.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 更改每行显示数目  1：一列   2：二列
     */
    private void changeShowItemCount(int count) {
        if (count == 1) {
            recycleViewDataLibrary.setBackgroundResource(R.color.white);
            view.setVisibility(View.VISIBLE);
        } else {
            recycleViewDataLibrary.setBackgroundResource(R.color.color_f9f9f9);
            view.setVisibility(View.GONE);
        }
        SPUtil.setInt(getActivity(), "LIBRARY_DATA_CODE", count);
        manager.setSpanCount(count);
        DataLibraryItemRecyclerAdapter adapter = new DataLibraryItemRecyclerAdapter(getActivity(), list, count);
        recycleViewDataLibrary.setAdapter(adapter);

    }

    @OnClick({R.id.ll_datalibrary_normal, R.id.ll_datalibrary_data, R.id.ll_datalibrary_material, R.id.ll_datalibrary_code,R.id.ll_datalibrary_null})
    public void onViewClicked(View view) {
        unSelect();
        switch (view.getId()) {
            case R.id.ll_datalibrary_normal:
                tvDatalibraryNormal.setTextColor(this.getResources().getColor(R.color.color_de171e));
                ivDatalibraryNormal.setImageResource(R.mipmap.triangle_select);
                if (NetWorkUtils.isNetworkEnable(getActivity())) {
                    libraryDataListHttp(token_id, standard, data, materialConsumption, symbolCode);
                } else {
                    llDatalibraryNull.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_datalibrary_data:
                tvDatalibraryData.setTextColor(this.getResources().getColor(R.color.color_de171e));
                ivDatalibraryData.setImageResource(R.mipmap.triangle_select);
                if (NetWorkUtils.isNetworkEnable(getActivity())) {
                    libraryDataListHttp(token_id, standard, data, materialConsumption, symbolCode);
                } else {
                    llDatalibraryNull.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_datalibrary_material:
                tvDatalibraryMaterial.setTextColor(this.getResources().getColor(R.color.color_de171e));
                ivDatalibraryMaterial.setImageResource(R.mipmap.triangle_select);
                if (NetWorkUtils.isNetworkEnable(getActivity())) {
                    libraryDataListHttp(token_id, standard, data, materialConsumption, symbolCode);
                } else {
                    llDatalibraryNull.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_datalibrary_code:
                tvDatalibraryCode.setTextColor(this.getResources().getColor(R.color.color_de171e));
                ivDatalibraryCode.setImageResource(R.mipmap.triangle_select);
                if (NetWorkUtils.isNetworkEnable(getActivity())) {
                    libraryDataListHttp(token_id, standard, data, materialConsumption, symbolCode);
                } else {
                    llDatalibraryNull.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_datalibrary_null:
                //网络异常
                if (NetWorkUtils.isNetworkEnable(getActivity())) {
                    libraryDataListHttp(token_id, standard, data, materialConsumption, symbolCode);
                } else {
                    llDatalibraryNull.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void unSelect() {
        tvDatalibraryNormal.setTextColor(this.getResources().getColor(R.color.color_323232));
        tvDatalibraryData.setTextColor(this.getResources().getColor(R.color.color_323232));
        tvDatalibraryMaterial.setTextColor(this.getResources().getColor(R.color.color_323232));
        tvDatalibraryCode.setTextColor(this.getResources().getColor(R.color.color_323232));
        ivDatalibraryNormal.setImageResource(R.mipmap.triangle_normal);
        ivDatalibraryData.setImageResource(R.mipmap.triangle_normal);
        ivDatalibraryMaterial.setImageResource(R.mipmap.triangle_normal);
        ivDatalibraryCode.setImageResource(R.mipmap.triangle_normal);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser){
            if (NetWorkUtils.isNetworkEnable(getActivity())) {
                libraryDataListHttp(token_id, standard, data, materialConsumption, symbolCode);
                llDatalibraryNull.setVisibility(View.GONE);
            } else {
                llDatalibraryNull.setVisibility(View.VISIBLE);
            }
        }
    }
}
