package com.gcyh.jiedian.my.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.MyProjectManagerAdapter;
import com.gcyh.jiedian.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProjectManagerActivity extends BaseActivity {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.recycleView_My_Project_Manager)
    RecyclerView recycleViewMyProjectManager;
    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;

    private List<String> Datas;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_project_manager);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("项目管理");
        ivToolbarRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initView() {
        initData() ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //水平滑动
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        recycleViewMyProjectManager.setLayoutManager(layoutManager);
        //适配器
        MyProjectManagerAdapter adapter = new MyProjectManagerAdapter(this, Datas) ;
        recycleViewMyProjectManager.setAdapter(adapter);
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
                //加号
                showDialog() ;
                break;
        }
    }


    public void showDialog(){

        Dialog  dialog = new Dialog(this ,R.style.FullHeightDialog);

        //2.填充布局
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.my_project_manager_dialog, null);
        //将自定义布局设置进去
        dialog.setContentView(dialogView);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        lp.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的
        lp.width = (int) (d.getWidth() * 0.9); // 高度设置为屏幕的

        dialogWindow.setAttributes(lp);

        dialog.show();
        dialog.setCancelable(false);

        initDialogListener(dialog);

    }




    private void initDialogListener(final Dialog dialog) {
        final Button diagloAdd = (Button) dialog.findViewById(R.id.btn_my_project_manager_add);
        final EditText  diagloNumber = (EditText) dialog.findViewById(R.id.et_my_project_manager_number);
        final TextView diagloError = (TextView) dialog.findViewById(R.id.tv_my_project_manager_error);
        final TextView  diagloContent = (TextView) dialog.findViewById(R.id.tv_my_project_manager_content);
        ImageView  diagloDelete = (ImageView) dialog.findViewById(R.id.iv_tv_my_project_manager_delete);
        final LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.ll_my_project_manager);

        diagloAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        diagloDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        diagloNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //如果编号错误
//                diagloError.setVisibility(View.VISIBLE);
                //如果编号正确
                ll.setBackgroundResource(R.mipmap.my_project_manager_bg_yes);
                diagloContent.setVisibility(View.VISIBLE);
            }
        });


    }

    private void initData() {

        Datas = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            Datas.add("世界你好" + i);
        }

    }

}
