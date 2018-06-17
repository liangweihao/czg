package com.gcyh.jiedian.library.fragment.projectlibrary;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.ProjectLibraryRecyclerAdapter;
import com.gcyh.jiedian.base.BaseFragment;
import com.gcyh.jiedian.entity.LibraryProjectList;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.EventBusCode;
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
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Caizhiguang on 2018/4/19.
 */

public class ProjectLibraryFragment extends BaseFragment {
    @BindView(R.id.recycleViewProjectLibrary)
    RecyclerView recycleViewProjectLibrary;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.iv_project_null)
    ImageView ivProjectNull;
    @BindView(R.id.tv_project_null)
    TextView tvProjectNull;
    @BindView(R.id.ll_projectlibrary_null)
    LinearLayout llProjectlibraryNull;

    public  static  List<LibraryProjectList.ResponseParamsBean> list = new ArrayList<>() ;
    private ProjectLibraryRecyclerAdapter adapter;
    private String token_id;
    private int pages = 1 ;
    private int row = 10 ;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_projectlibrary;
    }

    @Override
    protected void initPresenter() {

        token_id = SPUtil.getString(getActivity(), "token_id", "");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recycleViewProjectLibrary.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        initRefreshLayout();

    }

    private void projectLibraryHttp() {

        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.libraryprojectlist(token_id ,pages ,row)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<LibraryProjectList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(getActivity(), "项目库获取失败");
                    }

                    @Override
                    public void onNext(LibraryProjectList model) {
                        if (model.getRntCode().equals("OK")) {
                            list.clear();
                            ToastUtil.show(getActivity(), model.getRntMsg());
                            List<LibraryProjectList.ResponseParamsBean> responseParams = model.getResponseParams();
                            if (responseParams.size() > 0){
                                list.addAll(responseParams) ;
                                adapter.notifyDataSetChanged();
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
        int type = SPUtil.getInt(getActivity(), "LIBRARY_PROJECT_CODE", 2);
        if (type == 1){
            recycleViewProjectLibrary.setBackgroundResource(R.color.white);
        }else {
            recycleViewProjectLibrary.setBackgroundResource(R.color.color_f9f9f9);
        }
        // type   1 :左右  2：上下结构
        adapter = new ProjectLibraryRecyclerAdapter(getActivity(), list, type);
        recycleViewProjectLibrary.setAdapter(adapter);
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

    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {
        //切换布局类型
        if (eventCode == EventBusCode.CODE_LIBRARY_PROJECT) {
            int type = SPUtil.getInt(getActivity(), "LIBRARY_PROJECT_CODE", 1);
            if (type == 1) {
                SPUtil.setInt(getActivity(), "LIBRARY_PROJECT_CODE", 2);
                initView();
            } else if (type == 2) {
                SPUtil.setInt(getActivity(), "LIBRARY_PROJECT_CODE", 1);
                initView();
            }

        }else if (eventCode == EventBusCode.LIBRARY_PROJECT_UPDATE){
            //收藏状态更改
            if (NetWorkUtils.isNetworkEnable(getActivity())) {
                projectLibraryHttp();
            }else {
                llProjectlibraryNull.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.ll_projectlibrary_null})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_projectlibrary_null:
                if (NetWorkUtils.isNetworkEnable(getActivity())) {
                    projectLibraryHttp();
                }else {
                    llProjectlibraryNull.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
       if (isVisibleToUser){
           if (NetWorkUtils.isNetworkEnable(getActivity())) {
               projectLibraryHttp();
               Log.i("====", "initPresenter: "+token_id);
           } else {
               llProjectlibraryNull.setVisibility(View.VISIBLE);
           }
       }
    }
}
