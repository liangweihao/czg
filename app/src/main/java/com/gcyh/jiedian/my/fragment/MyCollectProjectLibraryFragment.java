package com.gcyh.jiedian.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.MyCollectProjectLibraryRecyclerAdapter;
import com.gcyh.jiedian.base.BaseFragment;
import com.gcyh.jiedian.entity.CollectList;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Caizhiguang on 2018/4/26.
 */

public class MyCollectProjectLibraryFragment extends BaseFragment {

    @BindView(R.id.recycleViewProjectLibrary)
    RecyclerView recycleViewProjectLibrary;
    @BindView(R.id.iv_collectprojectlibrary_null)
    ImageView ivCollectprojectlibraryNull;
    @BindView(R.id.tv_collectprojectlibrary_null)
    TextView tvCollectprojectlibraryNull;
    @BindView(R.id.ll_collectprojectlibrary_null)
    LinearLayout llCollectprojectlibraryNull;
    Unbinder unbinder;

    private MyCollectProjectLibraryRecyclerAdapter adapter;
    private List<CollectList.ResponseParamsBean.ProjectDataListDTOBean> projectLis;


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_collect_project_library;
    }

    @Override
    protected void initPresenter() {

        projectLis = (List<CollectList.ResponseParamsBean.ProjectDataListDTOBean>) getArguments().getSerializable("project");
        if (NetWorkUtils.isNetworkEnable(getActivity())) {
            if (projectLis != null) {
                if (projectLis.size() > 0) {

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    //设置布局管理器
                    recycleViewProjectLibrary.setLayoutManager(layoutManager);
                    //设置为垂直布局，这也是默认的
                    layoutManager.setOrientation(OrientationHelper.VERTICAL);

                    int type = SPUtil.getInt(getActivity(), "LIBRARY_COLLECT_PROJECT_CODE", 1);
                    if (type == 1) {
                        recycleViewProjectLibrary.setBackgroundResource(R.color.white);
                    } else {
                        recycleViewProjectLibrary.setBackgroundResource(R.color.color_f9f9f9);
                    }
                    // type   1 :左右  2：上下结构
                    adapter = new MyCollectProjectLibraryRecyclerAdapter(getActivity(), projectLis, type);
                    recycleViewProjectLibrary.setAdapter(adapter);

                } else {
                    //项目库为空---空状态
                    llCollectprojectlibraryNull.setVisibility(View.VISIBLE);
                }
            }
        } else {
            //网络异常
            llCollectprojectlibraryNull.setVisibility(View.VISIBLE);
        }


    }


    @Override
    protected void initView() {


    }

    @Override
    protected void initValue() {

    }


    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {
        if (eventCode == EventBusCode.CODE_MY_PROJECT) {
            int type = SPUtil.getInt(getActivity(), "LIBRARY_COLLECT_PROJECT_CODE", 1);
            if (type == 1) {
                SPUtil.setInt(getActivity(), "LIBRARY_COLLECT_PROJECT_CODE", 2);
                initView();
            } else if (type == 2) {
                SPUtil.setInt(getActivity(), "LIBRARY_COLLECT_PROJECT_CODE", 1);
                initView();
            }
        }
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

    @OnClick(R.id.ll_collectprojectlibrary_null)
    public void onViewClicked() {
        initPresenter();
    }

}
