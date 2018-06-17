package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.TabLayoutFragmentAdapter;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.CollectList;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.my.fragment.MyCollectDataLibraryFragment;
import com.gcyh.jiedian.my.fragment.MyCollectNoteLibraryFragment;
import com.gcyh.jiedian.my.fragment.MyCollectProjectLibraryFragment;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyCollectActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;
    @BindView(R.id.libraryXxTablayout)
    XTabLayout libraryXxTablayout;
    @BindView(R.id.libraryViewPager)
    ViewPager libraryViewPager;

    List<Fragment> fragments = new ArrayList<>();
    private int position;
    private boolean isType = true;
    private List<CollectList.ResponseParamsBean.ProjectDataListDTOBean> projectDataList;
    private List<CollectList.ResponseParamsBean.NodeDataListDTOBean> nodeDataList;
    private List<CollectList.ResponseParamsBean.DataBaseListBean> dataDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_collect);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("我的收藏");
        ivToolbarRight.setVisibility(View.VISIBLE);
        String token_id = SPUtil.getString(this, "token_id", "");
        if (NetWorkUtils.isNetworkEnable(this)){
            setCollectListHttp(token_id) ;
        }else {
            setData();
        }
        //初始化
        int type = SPUtil.getInt(MyCollectActivity.this, "LIBRARY_COLLECT_NOTE_CODE", 1);
        if (type == 1) {
            //列表
            ivToolbarRight.setImageResource(R.mipmap.type_list);
            isType = true ;
        } else {
            //宫格
            ivToolbarRight.setImageResource(R.mipmap.type_box);
            isType = false ;
        }

    }
    //获取收藏列表数据
    private void setCollectListHttp(String token_id) {
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.collectlist(token_id)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<CollectList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MyCollectActivity.this, "获取失败");
                    }

                    @Override
                    public void onNext(CollectList model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MyCollectActivity.this, model.getRntMsg());
                            projectDataList = model.getResponseParams().getProjectDataListDTO();
                            nodeDataList = model.getResponseParams().getNodeDataListDTO();
                            dataDataList = model.getResponseParams().getDataBaseList();
                            setData() ;
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MyCollectActivity.this, model.getRntMsg());
                        }

                    }
                });

    }

    private void setData() {

        List<String> titles = new ArrayList<>();
        titles.add("节点库");
        titles.add("项目库");
        titles.add("常用资料库");

        for (int i = 0; i < titles.size(); i++) {
            if (i == 0) {
                MyCollectNoteLibraryFragment fragment1 = new MyCollectNoteLibraryFragment() ;
                Bundle bundle = new Bundle() ;
                bundle.putSerializable("note", (Serializable)nodeDataList);
                fragment1.setArguments(bundle);
                fragments.add(fragment1);
            } else if (i == 1) {
                MyCollectProjectLibraryFragment fragment2 = new MyCollectProjectLibraryFragment() ;
                Bundle bundle = new Bundle() ;
                bundle.putSerializable("project", (Serializable)projectDataList);
                fragment2.setArguments(bundle);
                fragments.add(fragment2);
            } else if (i == 2) {
                MyCollectDataLibraryFragment fragment3 = new MyCollectDataLibraryFragment() ;
                Bundle bundle = new Bundle() ;
                bundle.putSerializable("data", (Serializable)dataDataList);
                fragment3.setArguments(bundle);
                fragments.add(fragment3);
            }
        }
        TabLayoutFragmentAdapter adatper = new TabLayoutFragmentAdapter(getSupportFragmentManager(), fragments, titles);

        libraryViewPager.setAdapter(adatper);
        libraryViewPager.setOffscreenPageLimit(2);
        //将TabLayout和ViewPager关联起来。
        libraryXxTablayout.setupWithViewPager(libraryViewPager);

        libraryViewPager.setCurrentItem(0);

        libraryXxTablayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                position = tab.getPosition();
                if (tab.getPosition() == 0) {
                    libraryViewPager.setCurrentItem(0);
                    int type = SPUtil.getInt(MyCollectActivity.this, "LIBRARY_COLLECT_NOTE_CODE", 1);
                    if (type == 1) {
                        //列表
                        ivToolbarRight.setImageResource(R.mipmap.type_list);
                        isType = true ;
                    } else {
                        //宫格
                        ivToolbarRight.setImageResource(R.mipmap.type_box);
                        isType = false ;
                    }
                } else if (tab.getPosition() == 1) {
                    libraryViewPager.setCurrentItem(1);
                    int type = SPUtil.getInt(MyCollectActivity.this, "LIBRARY_COLLECT_PROJECT_CODE", 1);
                    if (type == 1) {
                        //列表
                        ivToolbarRight.setImageResource(R.mipmap.type_list);
                        isType = true ;
                    } else {
                        //宫格
                        ivToolbarRight.setImageResource(R.mipmap.type_big_pic);
                        isType = false;
                    }
                } else if (tab.getPosition() == 2) {
                    libraryViewPager.setCurrentItem(2);
                    int type = SPUtil.getInt(MyCollectActivity.this, "LIBRARY_DATA_CODE", 1);
                    if (type == 1) {
                        //列表
                        ivToolbarRight.setImageResource(R.mipmap.type_list);
                        isType = true ;
                    } else {
                        //宫格
                        ivToolbarRight.setImageResource(R.mipmap.type_box);
                        isType = false ;
                    }
                }
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });

    }


    @Override
    protected void initView() {



    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {



    }

    @OnClick({R.id.btn_back, R.id.iv_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_toolbar_right:
                if (isType) {
                    //列表
                    if (position == 1) {
                        ivToolbarRight.setImageResource(R.mipmap.type_big_pic);
                        isType = false;
                    } else {
                        ivToolbarRight.setImageResource(R.mipmap.type_box);
                        isType = false;
                    }

                } else {
                    //宫格
                    ivToolbarRight.setImageResource(R.mipmap.type_list);
                    isType = true;
                }

                if (position == 0) {
                    //我的收藏的节点库
                    EventBusUtil.postEvent(EventBusCode.CODE_MY_NOTE);
                } else if (position == 1) {
                    //我的收藏的项目库
                    EventBusUtil.postEvent(EventBusCode.CODE_MY_PROJECT);
                } else if (position == 2) {
                    //我的收藏的常用数据库
                    EventBusUtil.postEvent(EventBusCode.CODE_MY_DATA);
                }

                break;
        }
    }
}
