package com.gcyh.jiedian.find.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.zhouwei.library.CustomPopWindow;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.FindRecyclerAdapter;
import com.gcyh.jiedian.adapter.findClassiflRecycleViewAdapter;
import com.gcyh.jiedian.base.BaseFragment;
import com.gcyh.jiedian.entity.FindList;
import com.gcyh.jiedian.find.activity.FindSearchActivity;
import com.gcyh.jiedian.find.activity.MyPublishActivity;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.my.activity.MyThemeActivity;
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
 * Created by Caizhiguang on 2018/4/18.
 */

public class FindFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    private static final int MESSAGE_SUCCESS = 1024;
    @BindView(R.id.iv_titlebar_type)
    ImageView ivTitlebarType;
    @BindView(R.id.iv_titlebar_search)
    ImageView ivTitlebarSearch;
    @BindView(R.id.et_titlebar_search)
    EditText etTitlebarSearch;
    @BindView(R.id.iv_titlebar_delete)
    ImageView ivTitlebarDelete;
    @BindView(R.id.iv_titlebar_jia)
    ImageView ivTitlebarJia;
    @BindView(R.id.titlebar)
    LinearLayout titlebar;
    @BindView(R.id.rb_find_time)
    RadioButton rbFindTime;
    @BindView(R.id.rb_find_hot)
    RadioButton rbFindHot;
    @BindView(R.id.rb_find_profession)
    RadioButton rbFindProfession;
    @BindView(R.id.rb_find_process)
    RadioButton rbFindProcess;
    @BindView(R.id.rb_find_finish)
    RadioButton rbFindFinish;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.recycleViewFind)
    RecyclerView recycleViewFind;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.ll_find_null)
    LinearLayout llFindNull;
    Unbinder unbinder;

    private FindRecyclerAdapter recycleFindAdapter;

    private findClassiflRecycleViewAdapter adapter;

    private List<Integer> datas;
    private View inflate;
    private Dialog dialog;
    private RecyclerView findRecyclerview;
    private List<FindList.ResponseParamsBean> list = new ArrayList<>();

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MESSAGE_SUCCESS) {
                imageView.setVisibility(View.GONE);
            }
            return false;
        }
    });
    private ImageView imageView;
    private String token_id;
    private int pages = 1 ;
    private int row = 10 ;


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initPresenter() {
        token_id = SPUtil.getString(getActivity(), "token_id", "");
        if (NetWorkUtils.isNetworkEnable(getActivity())){
            setFindHttp();
        }else {
            llFindNull.setVisibility(View.VISIBLE);
        }

        etTitlebarSearch.setHint("石膏板吊顶....");
        ivTitlebarDelete.setVisibility(View.GONE);
        radioGroup.setOnCheckedChangeListener(this);
        rbFindTime.setChecked(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recycleViewFind.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        recycleFindAdapter = new FindRecyclerAdapter(getActivity(), list);
        recycleViewFind.setAdapter(recycleFindAdapter);

        initRefreshLayout();

    }

    @Override
    protected void initView() {

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
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_find_time:

                break;
            case R.id.rb_find_hot:

                break;
            case R.id.rb_find_profession:

                break;
            case R.id.rb_find_process:

                break;
            case R.id.rb_find_finish:

                break;
            default:
                break;
        }
    }


    @OnClick({R.id.iv_titlebar_type, R.id.iv_titlebar_search, R.id.iv_titlebar_delete, R.id.iv_titlebar_jia,R.id.ll_find_null})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_type:
                //分类
                showDiaglo();
                break;
            case R.id.iv_titlebar_search:
                //搜索
                startIntent(FindSearchActivity.class);
                break;
            case R.id.iv_titlebar_delete:
                //删除
                etTitlebarSearch.setText("");
                break;
            case R.id.iv_titlebar_jia:
                //加号
                setPopupWindow();
                break;
            case R.id.ll_find_null:
                //网络异常
                if (NetWorkUtils.isNetworkEnable(getActivity())){
                    setFindHttp();
                }else {
                    llFindNull.setVisibility(View.VISIBLE);
                }
                break;

        }
    }

    //点击加号--弹出PopupWindow
    private void setPopupWindow() {

        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_jia, null);
        final CustomPopWindow popWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .create();
        popWindow.showAsDropDown(ivTitlebarJia, -(contentView.getMeasuredWidth() - ivTitlebarJia.getMeasuredWidth() + 5), 5);
        contentView.findViewById(R.id.tv_publish_results).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(MyPublishActivity.class);
                popWindow.dissmiss();
            }
        });

        contentView.findViewById(R.id.tv_my_results).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(MyThemeActivity.class);
                popWindow.dissmiss();
            }
        });
    }

    //分类
    private void showDiaglo() {
        dialog = new Dialog(getActivity(), R.style.FindDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.find_dialog_layout, null);
        findRecyclerview = (RecyclerView) inflate.findViewById(R.id.findRecyclerview);
        TextView chongZhi = (TextView) inflate.findViewById(R.id.chongzhi);  //重置
        TextView yes = (TextView) inflate.findViewById(R.id.yes);    //确定

        chongZhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //显示RecyclerView
        findRecyclerView();
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.8
        lp.width = (int) (d.getWidth() * 1.0); // 高度设置为屏幕的1.0
        //       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框


    }

    private void findRecyclerView() {

        //2.创建一个垂直的线性布局(一个布局管理器layoutManager只能绑定一个Recyclerview)
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);

        //找到RecyclerView，并设置布局管理器
        findRecyclerview.setLayoutManager(layoutManager1);
        findRecyclerview.setHasFixedSize(true);

        findRecyclerview.addItemDecoration(new SpaceItemDecoration(15, 20));

        //3.取得数据集(此处，应根据不同的主题查询得不同的数据传入到 MyRecyclerCardviewAdapter中构建adapter)
        datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(i);
        }
        //4.创建adapter
        adapter = new findClassiflRecycleViewAdapter(datas);
        //将RecyclerView组件绑定adapter
        findRecyclerview.setAdapter(adapter);

        //5.在Adapter中添加好事件后，变可以在这里注册事件实现监听了
        adapter.setOnItemClickListener(new findClassiflRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int positon) {

            }
        });


    }

    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {
        //点赞动画
        if (eventCode == EventBusCode.ANIMATION_ZAN) {
            int[] animtionZen = bundle.getIntArray("AnimtionZan");
            int width = bundle.getInt("Width");
            int height = bundle.getInt("Height");
            showView(animtionZen, width, height);
        }
    }

    private void showView(int[] locations, int width, int height) {

        FrameLayout decorView = (FrameLayout) getActivity().getWindow().getDecorView();
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) decorView.getLayoutParams();
        imageView = new ImageView(getActivity());
        decorView.addView(imageView);

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        lp.leftMargin = locations[0] - 200 + width / 2;
        lp.topMargin = locations[1] - 200 + height / 2;
        lp.width = 400;
        lp.height = 400;

        imageView.requestLayout();

        Glide.with(this)
                .load(R.mipmap.zan)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Integer, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception arg0, Integer arg1, Target<GlideDrawable> arg2, boolean arg3) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // 计算动画时长
//                        GifDrawable drawable = (GifDrawable) resource;
//                        GifDecoder decoder = drawable.getDecoder();
//                        for (int i = 0; i < drawable.getFrameCount(); i++) {
//                            duration += decoder.getDelay(i);
//                        }
                        //发送延时消息，通知动画结束
                        handler.sendEmptyMessageDelayed(MESSAGE_SUCCESS,
                                5000);
                        return false;
                    }
                }) //仅仅加载一次gif动画
                .into(new GlideDrawableImageViewTarget(imageView, 1));


    }

    private void setFindHttp() {

        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.findlist(token_id ,pages , row)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<FindList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(getActivity(), "获取发现列表失败");
                        Log.i("====", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(FindList model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(getActivity(), model.getRntMsg());
                            list.clear();
                            List<FindList.ResponseParamsBean> responseParams = model.getResponseParams();
                            if (responseParams != null) {
                                if (responseParams.size() > 0) {
                                    list.addAll(responseParams);
                                    recycleFindAdapter.notifyDataSetChanged();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
