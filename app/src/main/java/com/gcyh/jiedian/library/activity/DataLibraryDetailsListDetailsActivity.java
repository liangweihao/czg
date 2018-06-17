package com.gcyh.jiedian.library.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.LibraryDataListDetails;
import com.gcyh.jiedian.http.UrlAll;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataLibraryDetailsListDetailsActivity extends BaseActivity {

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
    @BindView(R.id.ll_main)
    LinearLayout llMain;

    private int nextPosition = 0;
    private List<LibraryDataListDetails.ResponseParamsBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_data_library_details_list_details);
        ButterKnife.bind(this);
        txtToolbarRight.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("资料详情");
        txtToolbarRight.setText("下一页");
        txtToolbarRight.setTextColor(Color.parseColor("#ffffff"));
        int position = getIntent().getIntExtra("position", 0);
        list = DataLibraryDetailsListActivity.list;
        nextPosition = position;
        setData(nextPosition);
        if (position == list.size()-1){
            txtToolbarRight.setClickable(false);
        }else {
            txtToolbarRight.setClickable(true);
        }

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

    @OnClick({R.id.btn_back, R.id.txt_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.txt_toolbar_right:
                //下一页
                ++nextPosition;
                llMain.removeAllViews();
                setData(nextPosition);
                if (nextPosition == list.size() - 1) {
                    //最后一页
                    txtToolbarRight.setClickable(false);
                } else {
                    txtToolbarRight.setClickable(true);
                }
                break;
        }
    }

    private void setData(int position) {
        LibraryDataListDetails.ResponseParamsBean responseParamsBean = list.get(position);
        List<LibraryDataListDetails.ResponseParamsBean.ContentsListBean> contentsList = responseParamsBean.getContentsList();
        for (int i = 0; i < contentsList.size(); i++) {
            int type = contentsList.get(i).getType();
            if (type == 1) {
                // 大标题
                setTextBigTitle(contentsList.get(i).getContent());
            }
            if (type == 2) {
                // 小标题
                setTextSmallTitle(contentsList.get(i).getContent());
            }
            if (type == 3) {
                setTextCommentContext(contentsList.get(i).getContent());
            }
            if (type == 4) {
                setTextKeyContext(contentsList.get(i).getContent());
            }
            if (type == 5) {
                setTextPicTitle(contentsList.get(i).getContent());
            }
            if (type == 6) {
                setImage(contentsList.get(i).getContent());
            }
            if (type == 7) {
                setTextPicContext(contentsList.get(i).getContent());
            }
        }
    }
    // 大标题--1
    private void setTextBigTitle(String textBigTitle) {
        TextView textView = new TextView(this);
        textView.setText(textBigTitle);
        textView.setTextColor(this.getResources().getColor(R.color.color_de171e));
        textView.setTextSize(17);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 15, 0, 0);//4个参数按顺序分别是左上右下
        textView.setLayoutParams(layoutParams);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setGravity(Gravity.CENTER);
        llMain.addView(textView);
    }
    // 小标题--2
    private void setTextSmallTitle(String textSmallTitle) {
        TextView textView = new TextView(this);
        textView.setText(textSmallTitle);
        textView.setTextColor(this.getResources().getColor(R.color.color_de171e));
        textView.setTextSize(16);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 15, 0, 0);//4个参数按顺序分别是左上右下
        textView.setLayoutParams(layoutParams);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setGravity(Gravity.CENTER);
        llMain.addView(textView);
    }
    // 内容  ---  普通颜色内容---3
    private void setTextCommentContext(String textCommentContext) {
        TextView textView = new TextView(this);
        textView.setText(textCommentContext);
//        textView.setTextColor(this.getResources().getColor(R.color.color_de171e));
        textView.setTextSize(15);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 15, 15, 0);//4个参数按顺序分别是左上右下
        textView.setLayoutParams(layoutParams);
        llMain.addView(textView);
    }
    // 内容  ---  加重颜色内容---4
    private void setTextKeyContext(String textKeyContext) {
        TextView textView = new TextView(this);
        textView.setText(textKeyContext);
        textView.setTextColor(this.getResources().getColor(R.color.color_333333));
        textView.setTextSize(15);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 15, 15, 0);//4个参数按顺序分别是左上右下
        textView.setLayoutParams(layoutParams);
        llMain.addView(textView);
    }
    //表---标题---5
    private void setTextPicTitle(String textPicTitle) {
        TextView textView = new TextView(this);
        textView.setText(textPicTitle);
        textView.setTextColor(this.getResources().getColor(R.color.color_333333));
        textView.setTextSize(15);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 15, 15, 0);//4个参数按顺序分别是左上右下
        textView.setLayoutParams(layoutParams);
        llMain.addView(textView);
    }
    //图片 --6
    private void setImage(final String image) {
        ImageView imageView = new ImageView(this);
        if (!TextUtils.isEmpty(image)){
            Glide.with(this).load(UrlAll.DOWN_LOAD_IMAGE+"image/"+image+".png").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.jiedian_item).into(imageView);
        }
        Log.i("=====", "setImage: ====="+UrlAll.DOWN_LOAD_IMAGE+"image/"+image+".png");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 15, 15, 0);//4个参数按顺序分别是左上右下
        imageView.setLayoutParams(layoutParams);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataLibraryDetailsListDetailsActivity.this , PhoteViewActivity.class) ;
                intent.putExtra("url" , image) ;
                startActivity(intent);
            }
        });
        llMain.addView(imageView);
    }

    //表---标题---7
    private void setTextPicContext(String textPicContext) {
        TextView textView = new TextView(this);
        textView.setText(textPicContext);
//        textView.setTextColor(this.getResources().getColor(R.color.color_333333));
        textView.setTextSize(15);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 15, 15, 0);//4个参数按顺序分别是左上右下
        textView.setLayoutParams(layoutParams);
        llMain.addView(textView);
    }


}
