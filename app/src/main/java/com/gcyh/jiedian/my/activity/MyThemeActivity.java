package com.gcyh.jiedian.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.MyThemeRecyclerAdapter;
import com.gcyh.jiedian.adapter.MyThemeRecyclerAdapter1;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.MyThemeList;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.LoadDialog;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;
import com.yanzhenjie.album.Album;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyThemeActivity extends BaseActivity {

    private static final int ACTIVITY_REQUEST_SELECT_PHOTO = 10241024;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.recycleView_my_theme)
    RecyclerView recycleViewMyTheme;
    @BindView(R.id.ll_library_search_null)
    LinearLayout llLibrarySearchNull;
    private List<String> mDatas;
    private int pages = 1;
    private int row = 10;
    private List<MyThemeList.ResponseParamsBean.UserPostFabulousDTOtListBean> list = new ArrayList<>();
    private MyThemeRecyclerAdapter adapter;
    private MyThemeList.ResponseParamsBean.UserInfoDTOBean userInfo = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_theme);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("我的主题");

    }

    @Override
    protected void initView() {
        initData(10);
        String token_id = SPUtil.getString(this, "token_id", "");
        if (NetWorkUtils.isNetworkEnable(this)) {
//            setMyThemeHttp(token_id, pages, row);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(MyThemeActivity.this);
        //设置布局管理器
        recycleViewMyTheme.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new MyThemeRecyclerAdapter(MyThemeActivity.this, list ,userInfo);
        recycleViewMyTheme.setAdapter(adapter);

    }

    private void setMyThemeHttp(String token_id, int pages, int row) {

        LoadDialog.show(this, "正在加载");
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.mythemelist(token_id, pages, row)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<MyThemeList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MyThemeActivity.this, "粉丝列表失败");
                        Log.i("====", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(MyThemeList model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MyThemeActivity.this, model.getRntMsg());
                            list.clear();
                            MyThemeList.ResponseParamsBean responseParams = model.getResponseParams();
                            List<MyThemeList.ResponseParamsBean.UserPostFabulousDTOtListBean> userPostList = responseParams.getUserPostFabulousDTOtList();
                            userInfo = responseParams.getUserInfoDTO();
                            if (userPostList != null) {
                                if (userPostList.size() > 0 || userInfo != null) {
                                    list.add(0 , null);
                                    list.addAll(userPostList);
                                    adapter = new MyThemeRecyclerAdapter(MyThemeActivity.this, list ,userInfo);
                                    recycleViewMyTheme.setAdapter(adapter);
                                } else {
                                    llLibrarySearchNull.setVisibility(View.VISIBLE);
                                }
                            }


                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MyThemeActivity.this, model.getRntMsg());
                        }
                        LoadDialog.dismiss();

                    }
                });

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }

    private void initData(int value) {

        mDatas = new ArrayList<String>();
        for (int i = 0; i < value; i++) {
            mDatas.add("世界你好" + i);
        }

    }

    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {
        //更换我的主题背景图片
        if (eventCode == EventBusCode.CHARGE_THEME_PIC) {
            picSelect();
        }
    }

    // 图片选择器
    private void picSelect() {
        // 1. 使用默认风格，并指定选择数量：
        // 第一个参数Activity/Fragment； 第二个request_code； 第三个允许选择照片的数量，不填可以无限选择。
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO, 9);
        // 2. 使用默认风格，不指定选择数量：
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO); // 第三个参数不填的话，可以选择无数个。
        // 3. 指定风格，并指定选择数量，如果不想限制数量传入Integer.MAX_VALUE;
        Album.startAlbum(MyThemeActivity.this, ACTIVITY_REQUEST_SELECT_PHOTO
                , 1                                                         // 指定选择数量。
                , ContextCompat.getColor(this, R.color.colorAccent)        // 指定Toolbar的颜色。
                , ContextCompat.getColor(this, R.color.albumTransparentHalf));  // 指定状态栏的颜色。
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_REQUEST_SELECT_PHOTO) {
            // 拿到用户选择的图片路径List：
            if (data != null) {
                List<String> pathList = Album.parseResult(data);
                //把图片路径传到服务器

                // 传到服务器成功之后

                // 访问网络获取图片

                // 刷新适配器

            }
        }
    }


}
