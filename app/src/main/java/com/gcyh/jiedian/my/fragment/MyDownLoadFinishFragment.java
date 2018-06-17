package com.gcyh.jiedian.my.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.MyDownLoadFinishRecycleViewAdapter;
import com.gcyh.jiedian.base.BaseFragment;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Caizhiguang on 2018/5/17.
 */

public class MyDownLoadFinishFragment extends BaseFragment {
    @BindView(R.id.swipeMenuRecyclerView_my_download_finish)
    SwipeMenuRecyclerView swipeMenuRecyclerViewMyDownloadFinish;
    Unbinder unbinder;
    @BindView(R.id.ll_my_finish_download_null)
    LinearLayout llMyFinishDownloadNull;
    @BindView(R.id.iv_my_finish_download_null)
    ImageView ivMyFinishDownloadNull;
    @BindView(R.id.tv_my_finish_download_null)
    TextView tvMyFinishDownloadNull;
    private List<Map<String, String>> list = new ArrayList<>();
    private MyDownLoadFinishRecycleViewAdapter adapter;


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_download_finish;
    }

    @Override
    protected void initPresenter() {
        list.clear();
        List<Map<String, String>> data = SPUtil.getInfo(getActivity(), "finish_download");
        list.addAll(data) ;
        if (NetWorkUtils.isNetworkEnable(getActivity())) {
            if (list != null) {
                if (list.size() > 0) {
                    swipeMenuRecyclerViewMyDownloadFinish.setLayoutManager(new LinearLayoutManager(getActivity()));
                    swipeMenuRecyclerViewMyDownloadFinish.addItemDecoration(new DefaultItemDecoration(Color.red(R.color.color_ececec)));
                    //  设置侧滑
                    swipeMenuRecyclerViewMyDownloadFinish.setSwipeMenuCreator(mSwipeMenuCreator);
                    swipeMenuRecyclerViewMyDownloadFinish.setSwipeMenuItemClickListener(mMenuItemClickListener);

                    //适配器
                    adapter = new MyDownLoadFinishRecycleViewAdapter(getActivity(), list);
                    swipeMenuRecyclerViewMyDownloadFinish.setAdapter(adapter);
                } else {
                    llMyFinishDownloadNull.setVisibility(View.VISIBLE);
                }
            }
        } else {
            llMyFinishDownloadNull.setVisibility(View.VISIBLE);
            ivMyFinishDownloadNull.setImageResource(R.mipmap.internet_null);
            tvMyFinishDownloadNull.setText("网络不给力，点击重新加载");
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
                    //获取下载模型的名称
                    String nameUrl = list.get(adapterPosition).get("imageUrl");
                    String substring = nameUrl.substring(6, nameUrl.length());
                    //移除下载模型的参数---让他处于未下载状态
                    SPUtil.remove(getActivity(), "unity/" + substring);
                    list.remove(adapterPosition);  // 移除集合
                    adapter.notifyDataSetChanged();  //刷新适配器
                    //将集合中更新集合
                    SPUtil.saveInfo(getActivity() , "finish_download" , list);

                    //删除下载的模型文件
                    deleteFile(Environment.getExternalStorageDirectory() + "/Library/" + substring + ".dm");
                    //通知节点库刷新数据
                    EventBusUtil.postEvent(EventBusCode.DOWNLOAD_FINISH_NOTE_UPDATE);

                    break;
            }
        }
    };

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.i("====", "deleteFile:" + " 删除单个文件" + fileName + "成功！");
                return true;
            } else {
                Log.i("====", "deleteFile:" + " 删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            Log.i("====", "deleteFile:" + " 删除单个文件" + fileName + "不存在！");
            return false;
        }
    }

    @OnClick(R.id.ll_my_finish_download_null)
    public void onViewClicked() {

        initPresenter();

    }
}
