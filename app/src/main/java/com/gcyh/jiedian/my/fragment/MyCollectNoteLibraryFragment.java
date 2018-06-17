package com.gcyh.jiedian.my.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.MyCollectNoteLibraryItemRecyclerAdapter;
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

public class MyCollectNoteLibraryFragment extends BaseFragment {


    @BindView(R.id.recycleViewNoteLibrary)
    RecyclerView recycleViewNoteLibrary;
    @BindView(R.id.iv_collectnotelibrary_null)
    ImageView ivCollectnotelibraryNull;
    @BindView(R.id.tv_collectnotelibrary_null)
    TextView tvCollectnotelibraryNull;
    @BindView(R.id.ll_collectnotelibrary_null)
    LinearLayout llCollectnotelibraryNull;
    @BindView(R.id.view)
    View view;
    Unbinder unbinder;

    private GridLayoutManager manager;
    private MyCollectNoteLibraryItemRecyclerAdapter adapter;
    private List<CollectList.ResponseParamsBean.NodeDataListDTOBean> list;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_collect_note_library;
    }

    @Override
    protected void initPresenter() {

        list = (List<CollectList.ResponseParamsBean.NodeDataListDTOBean>) getArguments().getSerializable("note");
        if (NetWorkUtils.isNetworkEnable(getActivity())) {
            if (list != null) {
                if (list.size() > 0) {

                    int type = SPUtil.getInt(getActivity(), "LIBRARY_COLLECT_NOTE_CODE", 1);
                    if (type == 1) {
                        recycleViewNoteLibrary.setBackgroundResource(R.color.white);
                        view.setVisibility(View.VISIBLE);
                    } else {
                        recycleViewNoteLibrary.setBackgroundResource(R.color.color_f9f9f9);
                        view.setVisibility(View.GONE);
                    }
                    manager = new GridLayoutManager(getActivity(), type);
                    recycleViewNoteLibrary.setLayoutManager(manager);
                    recycleViewNoteLibrary.addItemDecoration(new SpaceItemDecoration(15, 20));
                    recycleViewNoteLibrary.setHasFixedSize(true);
                    adapter = new MyCollectNoteLibraryItemRecyclerAdapter(getActivity(), list, type);
                    recycleViewNoteLibrary.setAdapter(adapter);
                } else {
                    //节点库没有数据---空状态
                    llCollectnotelibraryNull.setVisibility(View.VISIBLE);
                }
            }
        } else {
            //网络异常
            llCollectnotelibraryNull.setVisibility(View.VISIBLE);
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
        if (eventCode == EventBusCode.CODE_MY_NOTE) {
            int type = SPUtil.getInt(getActivity(), "LIBRARY_COLLECT_NOTE_CODE", 1);
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
            recycleViewNoteLibrary.setBackgroundResource(R.color.white);
            view.setVisibility(View.VISIBLE);
        } else {
            recycleViewNoteLibrary.setBackgroundResource(R.color.color_f9f9f9);
            view.setVisibility(View.GONE);
        }
        SPUtil.setInt(getActivity(), "LIBRARY_COLLECT_NOTE_CODE", count);
        manager.setSpanCount(count);
        MyCollectNoteLibraryItemRecyclerAdapter adapter = new MyCollectNoteLibraryItemRecyclerAdapter(getActivity(), list, count);
        recycleViewNoteLibrary.setAdapter(adapter);

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

    @OnClick(R.id.ll_collectnotelibrary_null)
    public void onViewClicked() {
        initPresenter();
    }
}
