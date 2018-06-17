package com.gcyh.jiedian.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.MyCollectDataLibraryItemRecyclerAdapter;
import com.gcyh.jiedian.base.BaseFragment;
import com.gcyh.jiedian.entity.CollectList;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.SpaceItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Caizhiguang on 2018/4/26.
 */

public class MyCollectDataLibraryFragment extends BaseFragment {

    @BindView(R.id.recycleViewDataLibrary)
    RecyclerView recycleViewDataLibrary;
    Unbinder unbinder;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.ll_collectdatalibrary_null)
    LinearLayout llCollectdatalibraryNull;


    private GridLayoutManager manager;
    private String token_id;
    private List<CollectList.ResponseParamsBean.DataBaseListBean> list;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_collect_data_library;
    }

    @Override
    protected void initPresenter() {
        list = (List<CollectList.ResponseParamsBean.DataBaseListBean>) getArguments().getSerializable("data");
        if (NetWorkUtils.isNetworkEnable(getActivity())) {
            if (list != null) {
                if (list.size() > 0) {

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
                    MyCollectDataLibraryItemRecyclerAdapter adapter = new MyCollectDataLibraryItemRecyclerAdapter(getActivity(), list, type);
                    recycleViewDataLibrary.setAdapter(adapter);

                } else {
                    //数据库--空状态
                    llCollectdatalibraryNull.setVisibility(View.VISIBLE);
                }
            }
        } else {
            //网络异常
            llCollectdatalibraryNull.setVisibility(View.VISIBLE);
        }


    }


    @Override
    protected void initView() {


    }

    @Override
    protected void initValue() {

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


    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {
        if (eventCode == EventBusCode.CODE_MY_DATA) {
            int type = SPUtil.getInt(getActivity(), "LIBRARY_COLLECT_DATA_CODE", 1);
            if (type == 1) {
                changeShowItemCount(2);
            } else if (type == 2) {
                changeShowItemCount(1);
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
        SPUtil.setInt(getActivity(), "LIBRARY_COLLECT_DATA_CODE", count);
        manager.setSpanCount(count);
        MyCollectDataLibraryItemRecyclerAdapter adapter = new MyCollectDataLibraryItemRecyclerAdapter(getActivity(), list, count);
        recycleViewDataLibrary.setAdapter(adapter);

    }

    @OnClick(R.id.ll_collectdatalibrary_null)
    public void onViewClicked() {
        initPresenter() ;
    }
}
