package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.widget.CircleImageView;
import com.gcyh.jiedian.widget.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/4/25.
 */

public class MyZanRecycleViewAdapter extends RecyclerView.Adapter<MyZanRecycleViewAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;
    private List<String> mDatas = new ArrayList<String>();

    public MyZanRecycleViewAdapter(FragmentActivity activity, List<String> mDatas) {
        this.context = activity;
        this.list = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_my_zan_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //头像
        Glide.with(context).load("http://7xi8d6.com1.z0.glb.clouddn.com/20171228085004_5yEHju_Screenshot.jpeg").asBitmap().into(holder.ivMyZanItemHead);

        mDatas.clear();
        initData();

        //类型列表
        LinearLayoutManager typeLayoutManager = new LinearLayoutManager(context);
        //设置为垂直布局，这也默认的
        typeLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置布局管理器
        holder.recycleViewMyZanItemType.setLayoutManager(typeLayoutManager);
        //适配器
        FindItemTypeRecyclerAdapter typeAdapter = new FindItemTypeRecyclerAdapter(context, mDatas);
        holder.recycleViewMyZanItemType.setAdapter(typeAdapter);


        //图片列表
        GridLayoutManager piclayoutManager = new GridLayoutManager(context, 4);
//        piclayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        holder.recycleViewMyZanItemPic.setLayoutManager(piclayoutManager);
        FindItemPicRecyclerAdapter picAdapter = new FindItemPicRecyclerAdapter(context, mDatas);
        holder.recycleViewMyZanItemPic.setAdapter(picAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_my_zan_item_head)
        CircleImageView ivMyZanItemHead;
        @BindView(R.id.tv_my_zan_item_name)
        TextView tvMyZanItemName;
        @BindView(R.id.tv_my_zan_item_time)
        TextView tvMyZanItemTime;
        @BindView(R.id.recycleView_my_zan_item_type)
        RecyclerView recycleViewMyZanItemType;
        @BindView(R.id.tv_my_zan_item_content)
        TextView tvMyZanItemContent;
        @BindView(R.id.recycleView_my_zan_item_pic)
        RecyclerView recycleViewMyZanItemPic;
        @BindView(R.id.cb_my_zan_item_zan)
        CheckBox cbMyZanItemZan;
        @BindView(R.id.iv_my_zan_item_zan)
        TextView ivMyZanItemZan;
        @BindView(R.id.ll_my_zan_item_zan)
        LinearLayout llMyZanItemZan;
        @BindView(R.id.tv_my_zan_item_comment)
        TextView tvMyZanItemComment;
        @BindView(R.id.ll_my_zan_item_comment)
        LinearLayout llMyZanItemComment;



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void initData() {

        for (int i = 0; i < 4; i++) {
            mDatas.add("http://pics.sc.chinaz.com/files/pic/pic9/201511/apic16083.jpg");
        }
    }
}
