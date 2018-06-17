package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.ShopCarAdapter;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.ChildBean;
import com.gcyh.jiedian.entity.ParentBean;
import com.gcyh.jiedian.entity.ShopCarList;
import com.gcyh.jiedian.entrance.ICartView;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.library.activity.PayMethodActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;
import com.gcyh.jiedian.widget.DelSlideExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShopCarActivity extends BaseActivity implements ICartView {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.txt_toolbar_right)
    TextView txtToolbarRight;
    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.cb_shop_car_all)
    CheckBox cbShopCarAll;
    @BindView(R.id.tv_shop_car_all_money)
    TextView tvShopCarAllMoney;
    @BindView(R.id.tv_shop_car_jiesuan)
    TextView tvShopCarJiesuan;
    @BindView(R.id.swipeMenuRecyclerView)
    DelSlideExpandableListView expandableListView;
    @BindView(R.id.ll_shop_car_null)
    LinearLayout llShopCarNull;
    @BindView(R.id.iv_shop_car_null)
    ImageView ivShopCarNull;
    @BindView(R.id.tv_shop_car_null)
    TextView tvShopCarNull;


    private String token_id;
    private List<List<ChildBean>> childList;
    private ShopCarAdapter adapter;
    private int sum = 0;//总价


    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_shop_car);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("购物车");
        token_id = SPUtil.getString(this, "token_id", "");

        if (NetWorkUtils.isNetworkEnable(this)) {
            shopCarListHttp(token_id);
        } else {
            llShopCarNull.setVisibility(View.VISIBLE);
            ivShopCarNull.setImageResource(R.mipmap.internet_null);
            tvShopCarNull.setText("网络不给力，点击重新加载");
        }
        createEvent();
    }

    private void shopCarListHttp(String token_id) {
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.shopcarlist(token_id)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<ShopCarList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(ShopCarActivity.this, "购物车获取失败");
                    }

                    @Override
                    public void onNext(ShopCarList model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(ShopCarActivity.this, model.getRntMsg());

                            List<ShopCarList.ResponseParamsBean.NodeDataDTOBean> dateBeadNote = model.getResponseParams().getNodeDataDTO();
                            List<ShopCarList.ResponseParamsBean.ProjectDataDTOBean> dateBeadProject = model.getResponseParams().getProjectDataDTO();
                            if (dateBeadNote.size() >0 || dateBeadProject.size() > 0){
                                setData(dateBeadNote, dateBeadProject);
                            }else {
                                llShopCarNull.setVisibility(View.VISIBLE);
                            }

                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(ShopCarActivity.this, model.getRntMsg());
                        }

                    }
                });
    }

    private void setData(List<ShopCarList.ResponseParamsBean.NodeDataDTOBean> dateBeadNote, List<ShopCarList.ResponseParamsBean.ProjectDataDTOBean> dateBeadProject) {

        List<ParentBean> parentList = new ArrayList<>();

        if (dateBeadNote != null) {
            if (dateBeadNote.size() > 0) {
                parentList.add(new ParentBean("节点库商品", false));
            }
        }
        if (dateBeadProject != null) {
            if (dateBeadProject.size() > 0) {
                parentList.add(new ParentBean("项目库商品", false));
            }
        }

        childList = new ArrayList<>();
        List<ChildBean> child_1 = new ArrayList<>();
        if (dateBeadNote != null) {
            if (dateBeadNote.size() > 0) {
                for (int i = 0; i < dateBeadNote.size(); i++) {
                    ShopCarList.ResponseParamsBean.NodeDataDTOBean.NodeDataBean nodeData = dateBeadNote.get(i).getNodeData();
                    child_1.add(new ChildBean(nodeData.getTitle(), 2, nodeData.getId(), false, nodeData.getImageName(), nodeData.getMoney(), dateBeadNote.get(i).getUserCollection()));
                }
                childList.add(child_1);
            }
        }

        List<ChildBean> child_2 = new ArrayList<>();
        if (dateBeadProject != null) {
            if (dateBeadProject.size() > 0) {
                for (int i = 0; i < dateBeadProject.size(); i++) {
                    ShopCarList.ResponseParamsBean.ProjectDataDTOBean.ProjectDataBean projectData = dateBeadProject.get(i).getProjectData();
                    child_2.add(new ChildBean(projectData.getName(), 1, projectData.getId(), false, projectData.getImage(), projectData.getMoney(), dateBeadProject.get(i).getCollection()));
                }
                childList.add(child_2);
            }
        }

        adapter = new ShopCarAdapter(this, parentList, childList, this);
        expandableListView.setAdapter(adapter);

        expandableListView.setGroupIndicator(null);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }

    }

    private void createEvent() {

        //设置选中监听去实现全选
        cbShopCarAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    adapter.allCheck(true);
                }
            }
        });
        //设置点击监听去实现反选
        cbShopCarAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取购物车的CheckBox的选中状态
                boolean isCheck = cbShopCarAll.isChecked();
                if (!isCheck) {
                    adapter.allCheck(false);
                }
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


    @OnClick({R.id.btn_back, R.id.tv_shop_car_jiesuan ,R.id.ll_shop_car_null})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_shop_car_jiesuan:
                //结算
                startIntent(PayMethodActivity.class);
                break;
            case R.id.ll_shop_car_null:
                //没有网络
                if (NetWorkUtils.isNetworkEnable(this)) {
                    shopCarListHttp(token_id);
                } else {
                    llShopCarNull.setVisibility(View.VISIBLE);
                    ivShopCarNull.setImageResource(R.mipmap.internet_null);
                    tvShopCarNull.setText("网络不给力，点击重新加载");
                }
                break;
        }
    }


    @Override
    public void changeCheckBtn(boolean flag) {
        cbShopCarAll.setChecked(flag);
    }

    @Override
    public void addPrice() {
        //初始化总价
        sum = 0;
        //遍历所有的子集合
        for (int i = 0; i < adapter.getGroupCount(); i++) {

            for (int j = 0; j < adapter.getChildrenCount(i); j++) {

                ChildBean child = adapter.getChild(i, j);
                //如果该对象被选中,则加上这个对象中的价钱
                if (child.isCheck) {
                    sum += child.price;
                }
            }
        }
        //得到总价,更新UI控件
        tvShopCarAllMoney.setText("总价：" + sum + "线币");
    }

    @Override
    public void delete(int groupPosition, int childPosition, int type, int id) {
        childList.get(groupPosition).remove(childPosition);
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("id", id);
        EventBusUtil.postEvent(EventBusCode.DELETE_SHOP_CAR, bundle);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void collect(int type, int id, int collect) {
        if (collect == 1) {
            //添加收藏
            Bundle bundle = new Bundle();
            bundle.putInt("type", type);
            bundle.putInt("id", id);
            EventBusUtil.postEvent(EventBusCode.ADD_COLLECT, bundle);
        } else {
            //移除收藏
            Bundle bundle = new Bundle();
            bundle.putInt("type", type);
            bundle.putInt("id", id);
            EventBusUtil.postEvent(EventBusCode.DELETE_COLLECT, bundle);
        }
        adapter.notifyDataSetChanged();
        expandableListView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }
        //计算总价
        addPrice();
    }


}
