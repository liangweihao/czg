package com.gcyh.jiedian.library.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.BeerShopFragmentItemAdapter;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.LibraryProjectList;
import com.gcyh.jiedian.library.fragment.projectlibrary.ProjectLibraryFragment;
import com.gcyh.jiedian.my.activity.MyDownLoadActivity;
import com.gcyh.jiedian.productionAR.activity.ProductionARDetailsActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.util.SpaceItemDecoration;
import com.gcyh.jiedian.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectLibraryDetailsActivity extends BaseActivity {


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
    @BindView(R.id.iv_projectlibrary_details_pic)
    ImageView ivProjectlibraryDetailsPic;
    @BindView(R.id.tv_projectlibrary_details_fangansheji)
    TextView tvProjectlibraryDetailsFangansheji;
    @BindView(R.id.tv_projectlibrary_details_time)
    TextView tvProjectlibraryDetailsTime;
    @BindView(R.id.tv_projectlibrary_details_shigongtushenhua)
    TextView tvProjectlibraryDetailsShigongtushenhua;
    @BindView(R.id.tv_projectlibrary_details_shigongdanwei)
    TextView tvProjectlibraryDetailsShigongdanwei;
    @BindView(R.id.tv_projectlibrary_details_project_introduce)
    TextView tvProjectlibraryDetailsProjectIntroduce;
    @BindView(R.id.recycleViewProjectLibraryDetails)
    RecyclerView recycleViewProjectLibraryDetails;
    @BindView(R.id.tv_projectlibrary_details_number)
    TextView tvProjectlibraryDetailsNumber;
    @BindView(R.id.iv_projectlibrary_details_collect_pic)
    ImageView ivProjectlibraryDetailsCollectPic;
    @BindView(R.id.tv_projectlibrary_details_collect_content)
    TextView tvProjectlibraryDetailsCollectContent;
    @BindView(R.id.ll_projectlibrary_details_collect)
    LinearLayout llProjectlibraryDetailsCollect;
    @BindView(R.id.ll_projectlibrary_details_add_shop_car)
    LinearLayout llProjectlibraryDetailsAddShopCar;
    @BindView(R.id.tv_projectlibrary_details_details_pay)
    TextView tvProjectlibraryDetailsDetailsPay;

    private List<LibraryProjectList.ResponseParamsBean.NodeDataListBean> nodeDataList;
    private List<LibraryProjectList.ResponseParamsBean> projectLibraryDetails;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_project_library_details);
        ButterKnife.bind(this);

        ivToolbarRight.setVisibility(View.VISIBLE);
        ivToolbarRight.setImageResource(R.mipmap.library_download);
        projectLibraryDetails = ProjectLibraryFragment.list;
        position = getIntent().getIntExtra("position", 0);
        //标题
        tvToolbarTitle.setText(projectLibraryDetails.get(position).getProjectData().getName());
        //图片
        if (!TextUtils.isEmpty(projectLibraryDetails.get(position).getProjectData().getImage())){
            Glide.with(this).load(projectLibraryDetails.get(position).getProjectData().getImage()).placeholder(R.mipmap.ar_bg).error(R.mipmap.ar_bg).into(ivProjectlibraryDetailsPic);
        }
        //设计公司
        tvProjectlibraryDetailsFangansheji.setText("方案设计公司:");
        //深化公司
        tvProjectlibraryDetailsShigongtushenhua.setText("施工图深化公司:");
        //施工单位
        tvProjectlibraryDetailsShigongdanwei.setText("施工单位:");
        //时间
        tvProjectlibraryDetailsTime.setText("");
        //项目介绍
        tvProjectlibraryDetailsProjectIntroduce.setText(projectLibraryDetails.get(position).getProjectData().getProjectDetail());
        //节点展示
        nodeDataList = projectLibraryDetails.get(position).getNodeDataList();

        if (projectLibraryDetails.get(position).getCollection() == 1){
            //有红色心---已收藏
            ivProjectlibraryDetailsCollectPic.setImageResource(R.mipmap.project_library_heart_select);  //收藏
            tvProjectlibraryDetailsCollectContent.setText("已收藏");
        }else if (projectLibraryDetails.get(position).getCollection() == 0){
            //无红色心---未收藏
            ivProjectlibraryDetailsCollectPic.setImageResource(R.mipmap.project_library_heart_normal); //未收藏
            tvProjectlibraryDetailsCollectContent.setText("收藏");
        }
        //线币数
        tvProjectlibraryDetailsNumber.setText(projectLibraryDetails.get(position).getProjectData().getMoney()+"");


    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //水平滑动
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置布局管理器
        recycleViewProjectLibraryDetails.setLayoutManager(layoutManager);
        recycleViewProjectLibraryDetails.addItemDecoration(new SpaceItemDecoration(25, 0));
        //适配器
        BeerShopFragmentItemAdapter adapter = new BeerShopFragmentItemAdapter(this, nodeDataList) ;
        recycleViewProjectLibraryDetails.setAdapter(adapter);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    @OnClick({R.id.btn_back,R.id.iv_toolbar_right, R.id.ll_projectlibrary_details_collect, R.id.ll_projectlibrary_details_add_shop_car, R.id.tv_projectlibrary_details_details_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_toolbar_right:
                startIntent(MyDownLoadActivity.class);
                break;
            case R.id.ll_projectlibrary_details_collect:
                if (projectLibraryDetails.get(position).getCollection() == 1){
                   //无红色心---未收藏
                    ivProjectlibraryDetailsCollectPic.setImageResource(R.mipmap.project_library_heart_normal);  //未收藏
                    tvProjectlibraryDetailsCollectContent.setText("收藏");
                    Bundle bundle = new Bundle() ;
                    bundle.putInt("type" , 1);
                    bundle.putInt("id" , projectLibraryDetails.get(position).getProjectData().getId());
                    EventBusUtil.postEvent(EventBusCode.DELETE_COLLECT , bundle);
                }else if (projectLibraryDetails.get(position).getCollection() == 0){
                    //有红色心---已收藏
                    ivProjectlibraryDetailsCollectPic.setImageResource(R.mipmap.project_library_heart_select); //已收藏
                    tvProjectlibraryDetailsCollectContent.setText("已收藏");
                    Bundle bundle = new Bundle() ;
                    bundle.putInt("type" , 1);
                    bundle.putInt("id" , projectLibraryDetails.get(position).getProjectData().getId());
                    EventBusUtil.postEvent(EventBusCode.ADD_COLLECT , bundle);
                }
                break;
            case R.id.ll_projectlibrary_details_add_shop_car:
                //是否加入购物车
                if (projectLibraryDetails.get(position).getShopCar() == 1){
                    //已加入购物车
                    ToastUtil.show(this , "已加入购物车");
                }else {
                    Bundle bundle = new Bundle() ;
                    bundle.putInt("id" , projectLibraryDetails.get(position).getProjectData().getId());
                    bundle.putInt("type" , 1);
                    EventBusUtil.postEvent(EventBusCode.ADD_SHOP_CAR , bundle);
                }

                break;
            case R.id.tv_projectlibrary_details_details_pay:
                break;
        }
    }



}
