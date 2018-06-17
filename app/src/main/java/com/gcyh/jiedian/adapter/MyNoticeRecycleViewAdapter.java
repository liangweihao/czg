package com.gcyh.jiedian.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.NoticeList;
import com.gcyh.jiedian.entrance.LoginActivity;
import com.gcyh.jiedian.find.activity.OtherFindActivity;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.my.activity.MyNoticeActivity;
import com.gcyh.jiedian.util.DialogUtil;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.widget.CircleImageView;
import com.gcyh.jiedian.widget.GlideCircleTransform;
import com.gcyh.jiedian.widget.RatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/4/25.
 */

public class MyNoticeRecycleViewAdapter extends RecyclerView.Adapter<MyNoticeRecycleViewAdapter.MyViewHolder> {

    private Context context;
    private List<NoticeList.ResponseParamsBean> list;

    public MyNoticeRecycleViewAdapter(MyNoticeActivity myNoticeActivity, List<NoticeList.ResponseParamsBean> mDatas) {
        this.context = myNoticeActivity;
        this.list = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_my_notice_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final NoticeList.ResponseParamsBean dataBean = list.get(position);
        final NoticeList.ResponseParamsBean.UserInfoBean userInfo = dataBean.getUserInfo();
        //头像
        Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE+userInfo.getPhoto()+".png").placeholder(R.mipmap.my_head).error(R.mipmap.my_head).into(holder.ivMyNoticeItemHead);
        //昵称
        holder.tvMyNoticeItemName.setText(userInfo.getNameNick());
        //A  B  S
        if (dataBean.getAVIP() == 1){
            holder.ivMyNoticeItemA.setVisibility(View.VISIBLE);
        }else {
            holder.ivMyNoticeItemA.setVisibility(View.GONE);
        }
        if (dataBean.getBVIP() == 1){
            holder.ivMyNoticeItemB.setVisibility(View.VISIBLE);
        }else {
            holder.ivMyNoticeItemB.setVisibility(View.GONE);
        }
        if (dataBean.getSVIP() == 1){
            holder.ivMyNoticeItemS.setVisibility(View.VISIBLE);
        }else {
            holder.ivMyNoticeItemS.setVisibility(View.GONE);
        }
        //等级
        setGrade(dataBean.getGrade() , holder.ivMyNoticeItemRank);
        //星星数
        holder.ratingBar.setStar(dataBean.getStar());
        //关注
        if (dataBean.getMutual() == 1){
            //互相关注
            holder.ivMyNoticeItemNotice.setImageResource(R.mipmap.each_notice);
        }else {
            holder.ivMyNoticeItemNotice.setImageResource(R.mipmap.finish_notice);
        }
        //关注点击事件
        holder.ivMyNoticeItemNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗---是否取消关注
                DialogUtil.dialogConfirmDialog2((Activity) context, new DialogUtil.YesOrNoDialogCallback() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle() ;
                        bundle.putString("code" , userInfo.getUserCode()+"");
                        EventBusUtil.postEvent(EventBusCode.DELETE_NOTICE , bundle);
                        list.remove(position) ;
                        notifyDataSetChanged();
                    }
                } ,"是否取消关注");

            }
        });
        holder.ivMyNoticeItemHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , OtherFindActivity.class) ;
                intent.putExtra("userCode" , userInfo.getUserCode()) ;
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_my_notice_item_head)
        CircleImageView ivMyNoticeItemHead;
        @BindView(R.id.tv_my_notice_item_name)
        TextView tvMyNoticeItemName;
        @BindView(R.id.iv_my_notice_item_A)
        ImageView ivMyNoticeItemA;
        @BindView(R.id.iv_my_notice_item_B)
        ImageView ivMyNoticeItemB;
        @BindView(R.id.iv_my_notice_item_S)
        ImageView ivMyNoticeItemS;
        @BindView(R.id.iv_my_notice_item_rank)
        ImageView ivMyNoticeItemRank;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.iv_my_notice_item_notice)
        ImageView ivMyNoticeItemNotice;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setGrade(int grade, ImageView ivMyGrade) {
        switch (grade) {
            case 1:
                ivMyGrade.setImageResource(R.mipmap.lv1);
                break;
            case 2:
                ivMyGrade.setImageResource(R.mipmap.lv2);
                break;
            case 3:
                ivMyGrade.setImageResource(R.mipmap.lv3);
                break;
            case 4:
                ivMyGrade.setImageResource(R.mipmap.lv4);
                break;
            case 5:
                ivMyGrade.setImageResource(R.mipmap.lv5);
                break;
            case 6:
                ivMyGrade.setImageResource(R.mipmap.lv6);
                break;
            case 7:
                ivMyGrade.setImageResource(R.mipmap.lv7);
                break;
            case 8:
                ivMyGrade.setImageResource(R.mipmap.lv8);
                break;
            case 9:
                ivMyGrade.setImageResource(R.mipmap.lv9);
                break;
            case 10:
                ivMyGrade.setImageResource(R.mipmap.lv10);
                break;
        }
    }

}
