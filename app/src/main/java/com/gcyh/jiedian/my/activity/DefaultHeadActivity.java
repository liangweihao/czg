package com.gcyh.jiedian.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.DefaultHeadAdapter;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DefaultHeadActivity extends BaseActivity {


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
    @BindView(R.id.gridView)
    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_default_head);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("默认头像");
    }

    @Override
    protected void initView() {
        //定义图标数组


        DefaultHeadAdapter adapter = new DefaultHeadAdapter(this) ;

        //添加元素给gridview
        gridview.setAdapter(adapter);
        // 设置Gallery的背景
//        gridview.setBackgroundResource(R.drawable.bg0);
        //事件监听
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ToastUtil.show(DefaultHeadActivity.this , "你选择了" + (position + 1) + " 号图片");
                SPUtil.setInt(DefaultHeadActivity.this , "Default_Head_Select" , position);
                Bundle bundle = new Bundle() ;
                bundle.putInt("DEFAULT_HEAD" , position);
                EventBusUtil.postEvent(EventBusCode.DEFAULT_HEAD,bundle);
                finish();

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
}
