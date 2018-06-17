package com.gcyh.jiedian.library.fragment.notelibrary;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.NoteLibraryItemRecyclerAdapter;
import com.gcyh.jiedian.adapter.PopupNoteLibraryRecycleViewAdapter;
import com.gcyh.jiedian.base.BaseFragment;
import com.gcyh.jiedian.entity.LibraryNoteList;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class NoteLibraryFragment extends BaseFragment {

    @BindView(R.id.recycleViewNoteLibrary)
    RecyclerView recycleViewNoteLibrary;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.view)
    View view;
    Unbinder unbinder;
    @BindView(R.id.tv_notelibrary_materials)
    TextView tvNotelibraryMaterials;
    @BindView(R.id.iv_notelibrary_materials)
    ImageView ivNotelibraryMaterials;
    @BindView(R.id.ll_notelibrary_materials)
    LinearLayout llNotelibraryMaterials;
    @BindView(R.id.tv_notelibrary_part)
    TextView tvNotelibraryPart;
    @BindView(R.id.iv_notelibrary_part)
    ImageView ivNotelibraryPart;
    @BindView(R.id.ll_notelibrary_part)
    LinearLayout llNotelibraryPart;
    @BindView(R.id.tv_notelibrary_space)
    TextView tvNotelibrarySpace;
    @BindView(R.id.iv_notelibrary_space)
    ImageView ivNotelibrarySpace;
    @BindView(R.id.ll_notelibrary_space)
    LinearLayout llNotelibrarySpace;
    @BindView(R.id.tv_dnotelibrary_hot)
    TextView tvDnotelibraryHot;
    @BindView(R.id.iv_dnotelibrary_hot)
    ImageView ivDnotelibraryHot;
    @BindView(R.id.ll_dnotelibrary_hot)
    LinearLayout llDnotelibraryHot;
    @BindView(R.id.tv_notelibrary_new_process)
    TextView tvNotelibraryNewProcess;
    @BindView(R.id.iv_notelibrary_new_process)
    ImageView ivNotelibraryNewProcess;
    @BindView(R.id.ll_notelibrary_new_process)
    LinearLayout llNotelibraryNewProcess;
    @BindView(R.id.iv_notelibrary_null)
    ImageView ivNotelibraryNull;
    @BindView(R.id.tv_notelibrary_null)
    TextView tvNotelibraryNull;
    @BindView(R.id.ll_notelibrary_null)
    LinearLayout llNotelibraryNull;


    private PopupWindow popupWindow;
    private GridLayoutManager manager;

    public static List<LibraryNoteList.ResponseParamsBean> list = new ArrayList<>();
    private NoteLibraryItemRecyclerAdapter adapter;

    private String[] materialsList = new String[]{"全部", "石材", "砖", "地板", "环氧地坪", "地毯", "塑胶地面", "水磨石", "玻璃", "漆类", "瓷砖", "石材", "木饰类", "金属类", "GRG", "硬包", "软包", "压克力", "软膜天花", "金银箔", "矿棉板", "吸音板"};
    private String[] partList = new String[]{"全部", "地面", "墙面", "天花"};
    private String[] spaceList = new String[]{"全部", "大堂", "卧室", "走廊", "卫生间", "会议室"};
    private String[] hotList = new String[]{"周", "月", "季", "年"};
    private String[] processList = new String[]{"2016~2018", "2016以前"};

    private String materials = null;
    private String part = null;
    private String space = null;
    private String hot = null;
    private String process = null;
    private String token_id;
    private int row = 20;
    private int pages = 1;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_notelibrary;
    }

    @Override
    protected void initPresenter() {

        if (NetWorkUtils.isNetworkEnable(getActivity())) {
            libraryNoteListHttp(token_id, pages, row, materials, part, space, hot, process);

        } else {
            llNotelibraryNull.setVisibility(View.VISIBLE);
        }

        initRefreshLayout();
        token_id = SPUtil.getString(getActivity(), "token_id", "");


    }


    private void libraryNoteListHttp(String token_id, int pages, int row, String materials, String part, String space, String hot, String process) {

        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.librarynotelist(token_id, pages, row, materials, part, space, hot, process)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<LibraryNoteList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(getActivity(), "节点库获取失败");
                        Log.i("====", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(LibraryNoteList model) {
                        if (model.getRntCode().equals("OK")) {
                            list.clear();
                            llNotelibraryNull.setVisibility(View.GONE);
                            ToastUtil.show(getActivity(), model.getRntMsg());
                            List<LibraryNoteList.ResponseParamsBean> responseParams = model.getResponseParams();
                            if (responseParams.size() > 0) {
                                list.addAll(responseParams);
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

        int type = SPUtil.getInt(getActivity(), "LIBRARY_NOTE_CODE", 1);
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
        adapter = new NoteLibraryItemRecyclerAdapter(getActivity(), list, type);
        recycleViewNoteLibrary.setAdapter(adapter);
        Bundle bundle = new Bundle();
        adapter.setProcess(0, bundle);

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


    public void showPopupWindow(View v, String[] list, int layout, String type) {

        View contentView = LayoutInflater.from(getActivity()).inflate(layout, null);
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recycleViewNotelibraryDiaglo);
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        //找到RecyclerView，并设置布局管理器
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(25, 20));
        PopupNoteLibraryRecycleViewAdapter adapter = new PopupNoteLibraryRecycleViewAdapter(getActivity(), list, type);
        recyclerView.setAdapter(adapter);

        popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.color.color_80000000));
        popupWindow.setOutsideTouchable(true);
        // 设置好参数之后再show
        //    popupWindow.showAsDropDown(v);
        showAsDropDown(popupWindow, v, 0, 0);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                unSelect();
            }
        });


    }

    public static void showAsDropDown(PopupWindow pw, View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(anchor, xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }

    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {

        if (eventCode == EventBusCode.CODE_DISMESS_POPW_MATERIALS) {
            //取消popuWindow --- 材料
            int position1 = bundle.getInt("type");
            popupWindow.dismiss();
            materials = materialsList[position1];
            if (NetWorkUtils.isNetworkEnable(getActivity())) {
                libraryNoteListHttp(token_id, pages, row, materials, part, space, hot, process);
            }
        } else if (eventCode == EventBusCode.CODE_DISMESS_POPW_PART) {
            //取消popuWindow --- 部位
            int position2 = bundle.getInt("type");
            popupWindow.dismiss();
            part = partList[position2];
            if (NetWorkUtils.isNetworkEnable(getActivity())) {
                libraryNoteListHttp(token_id, pages, row, materials, part, space, hot, process);
            }
        } else if (eventCode == EventBusCode.CODE_DISMESS_POPW_SPACE) {
            //取消popuWindow --- 空间
            int position3 = bundle.getInt("type");
            popupWindow.dismiss();
            space = spaceList[position3];
            if (NetWorkUtils.isNetworkEnable(getActivity())) {
                libraryNoteListHttp(token_id, pages, row, materials, part, space, hot, process);
            }
        } else if (eventCode == EventBusCode.CODE_DISMESS_POPW_HOT) {
            //取消popuWindow --- 热度
            int position4 = bundle.getInt("type");
            popupWindow.dismiss();
            hot = hotList[position4];
            if (NetWorkUtils.isNetworkEnable(getActivity())) {
                libraryNoteListHttp(token_id, pages, row, materials, part, space, hot, process);
            }
        } else if (eventCode == EventBusCode.CODE_DISMESS_POPW_PROCESS) {
            //取消popuWindow --- 新工艺
            int position5 = bundle.getInt("type");
            popupWindow.dismiss();
            process = processList[position5];
            if (NetWorkUtils.isNetworkEnable(getActivity())) {
                libraryNoteListHttp(token_id, pages, row, materials, part, space, hot, process);
            }
        }
        if (eventCode == EventBusCode.CODE_LIBRARY_NOTE) {
            int type = SPUtil.getInt(getActivity(), "LIBRARY_NOTE_CODE", 1);
            if (type == 1) {
                changeShowItemCount(2);
            } else if (type == 2) {
                changeShowItemCount(1);
            }
        }
        if (eventCode == EventBusCode.LIBRARY_FINISH_DOWNLOAD) {
            //把数据添加到下载管理---已下载
            int position = bundle.getInt("position", 0);
            String mb = bundle.getString("Mb");
            List<Map<String, String>> finish_download = SPUtil.getInfo(getActivity(), "finish_download");
            Map<String, String> map = new HashMap<String, String>();
            map.put("imageUrl", list.get(position).getNodeData().getImageName());
            map.put("title", list.get(position).getNodeData().getTitle());
            map.put("mb", mb + "");
            finish_download.add(0, map);
            SPUtil.saveInfo(getActivity(), "finish_download", finish_download);
        }

        if (eventCode == EventBusCode.LIBRARY_UPDATE_PROCESS) {
            //更新进度条
            int position = bundle.getInt("position");
            Log.i("====", "onBindViewHolder: ===" + position);
            Log.i("====", "onBindViewHolder: ===" + bundle.getInt("progress"));
            adapter.setProcess(position, bundle);
            adapter.notifyItemChanged(position);
        }
        if (eventCode == EventBusCode.LIBRARY_NOTE_UPDATE) {
            //收藏状态更改
            libraryNoteListHttp(token_id, pages, row, materials, part, space, hot, process);
        }
        if (eventCode == EventBusCode.DOWNLOAD_FINISH_NOTE_UPDATE) {
            //刷新数据
            int type = SPUtil.getInt(getActivity(), "LIBRARY_NOTE_CODE", 1);
            changeShowItemCount(type);

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
        SPUtil.setInt(getActivity(), "LIBRARY_NOTE_CODE", count);
        manager.setSpanCount(count);
        NoteLibraryItemRecyclerAdapter adapter = new NoteLibraryItemRecyclerAdapter(getActivity(), list, count);
        recycleViewNoteLibrary.setAdapter(adapter);
        Bundle bundle = new Bundle();
        adapter.setProcess(0, bundle);

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

    @OnClick({R.id.ll_notelibrary_null, R.id.ll_notelibrary_materials, R.id.ll_notelibrary_part, R.id.ll_notelibrary_space, R.id.ll_dnotelibrary_hot, R.id.ll_notelibrary_new_process})
    public void onViewClicked(View view) {
        unSelect();
        switch (view.getId()) {
            case R.id.ll_notelibrary_null:
                //没有网络重新加载
                if (NetWorkUtils.isNetworkEnable(getActivity())) {
                    libraryNoteListHttp(token_id, pages, row, materials, part, space, hot, process);

                } else {
                    llNotelibraryNull.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.ll_notelibrary_materials:
                //材料
                tvNotelibraryMaterials.setTextColor(this.getResources().getColor(R.color.color_de171e));
                ivNotelibraryMaterials.setImageResource(R.mipmap.triangle_select);
                showPopupWindow(view, materialsList, R.layout.popup_notelibrary, "materials");

                break;
            case R.id.ll_notelibrary_part:
                //部位

                tvNotelibraryPart.setTextColor(this.getResources().getColor(R.color.color_de171e));
                ivNotelibraryPart.setImageResource(R.mipmap.triangle_select);
                showPopupWindow(view, partList, R.layout.popup_notelibrary_wrap_content, "part");
                break;
            case R.id.ll_notelibrary_space:
                //空间
                tvNotelibrarySpace.setTextColor(this.getResources().getColor(R.color.color_de171e));
                ivNotelibrarySpace.setImageResource(R.mipmap.triangle_select);
                showPopupWindow(view, spaceList, R.layout.popup_notelibrary_wrap_content, "space");
                break;
            case R.id.ll_dnotelibrary_hot:
                //热度
                tvDnotelibraryHot.setTextColor(this.getResources().getColor(R.color.color_de171e));
                ivDnotelibraryHot.setImageResource(R.mipmap.triangle_select);
                showPopupWindow(view, hotList, R.layout.popup_notelibrary_wrap_content, "hot");
                break;
            case R.id.ll_notelibrary_new_process:
                //新工艺
                tvNotelibraryNewProcess.setTextColor(this.getResources().getColor(R.color.color_de171e));
                ivNotelibraryNewProcess.setImageResource(R.mipmap.triangle_select);
                showPopupWindow(view, processList, R.layout.popup_notelibrary_wrap_content, "process");
                break;
        }
    }

    private void unSelect() {

        tvNotelibraryMaterials.setTextColor(this.getResources().getColor(R.color.color_323232));
        tvNotelibraryPart.setTextColor(this.getResources().getColor(R.color.color_323232));
        tvNotelibrarySpace.setTextColor(this.getResources().getColor(R.color.color_323232));
        tvDnotelibraryHot.setTextColor(this.getResources().getColor(R.color.color_323232));
        tvNotelibraryNewProcess.setTextColor(this.getResources().getColor(R.color.color_323232));
        ivNotelibraryMaterials.setImageResource(R.mipmap.triangle_normal);
        ivNotelibraryPart.setImageResource(R.mipmap.triangle_normal);
        ivNotelibrarySpace.setImageResource(R.mipmap.triangle_normal);
        ivDnotelibraryHot.setImageResource(R.mipmap.triangle_normal);
        ivNotelibraryNewProcess.setImageResource(R.mipmap.triangle_normal);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

    }
}
