package com.gcyh.jiedian.my.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.MyDownLoadRecycleViewAdapter;
import com.gcyh.jiedian.base.BaseFragment;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Caizhiguang on 2018/5/17.
 */

public class MyDownLoadingFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.swipeMenuRecyclerView_my_downloading)
    SwipeMenuRecyclerView swipeMenuRecyclerViewMyDownloading;
    @BindView(R.id.ll_my_downloading_null)
    LinearLayout llMyDownloadingNull;
    @BindView(R.id.iv_my_downloading_null)
    ImageView ivMyDownloadingNull;
    @BindView(R.id.tv_my_downloading_null)
    TextView tvMyDownloadingNull;
    private MyDownLoadRecycleViewAdapter adapter;


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_downloading;
    }

    @Override
    protected void initPresenter() {
        List<Map<String, String>> downloading = SPUtil.getInfo(getActivity(), "downloading");
        if (NetWorkUtils.isNetworkEnable(getActivity())) {
            if (downloading != null) {
                if (downloading.size() > 0) {
                    swipeMenuRecyclerViewMyDownloading.setLayoutManager(new LinearLayoutManager(getActivity()));
                    swipeMenuRecyclerViewMyDownloading.addItemDecoration(new DefaultItemDecoration(Color.red(R.color.color_ececec)));
                    //  设置侧滑
                    swipeMenuRecyclerViewMyDownloading.setSwipeMenuCreator(mSwipeMenuCreator);
                    swipeMenuRecyclerViewMyDownloading.setSwipeMenuItemClickListener(mMenuItemClickListener);

                    //适配器
                    adapter = new MyDownLoadRecycleViewAdapter(getActivity(), downloading);
                    swipeMenuRecyclerViewMyDownloading.setAdapter(adapter);
                } else {
                    llMyDownloadingNull.setVisibility(View.VISIBLE);
                }
            }
        } else {
            llMyDownloadingNull.setVisibility(View.VISIBLE);
            ivMyDownloadingNull.setImageResource(R.mipmap.internet_null);
            tvMyDownloadingNull.setText("网络不给力，点击重新加载");
        }


    }

    @Override
    public void onResume() {
        super.onResume();
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
        //正在下载进度
        if (eventCode == EventBusCode.LIBRARY_UPDATE_PROCESS_DOWNLOADING) {
            int progress = bundle.getInt("progress");
            int id = bundle.getInt("id");
            adapter.setProcess(progress, id + "");
        }
    }

    /**
     * 创建菜单
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {

            int width = getResources().getDimensionPixelSize(R.dimen.dimen_70dp);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                    .setBackground(R.color.color_de171e)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            rightMenu.addMenuItem(deleteItem); // 添加菜单到右侧。

        }
    };

    /**
     * 菜单点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            switch (menuPosition) {
                case 0:
                    //  删除
                    ToastUtil.show(getActivity(), "删除成功");
                    break;
            }
        }
    };

    @OnClick(R.id.ll_my_downloading_null)
    public void onViewClicked() {
        initPresenter() ;
    }
}
