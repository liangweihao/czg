package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.find.activity.OtherFindActivity;
import com.gcyh.jiedian.library.activity.CommentListActivity;
import com.gcyh.jiedian.widget.CircleImageView;
import com.gcyh.jiedian.widget.GlideCircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/5/16.
 */

public class CommentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int ITEM_ONE = 1;
    private static final int ITEM_TWO = 2;
    private Context context ;
    private List<String> list ;

    public CommentListAdapter(CommentListActivity commentListActivity, List<String> datas) {
        this.context = commentListActivity ;
        this.list = datas ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_ONE) {
            Item1ViewHolder itemholder = new Item1ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_find_details_item1, parent, false));
            return itemholder;
        } else if (viewType == ITEM_TWO) {
            Item2ViewHolder itemholder = new Item2ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_find_details_item2, parent, false));
            return itemholder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Item1ViewHolder) {
            //条目一布局
            Glide.with(context).load("http://pics.sc.chinaz.com/files/pic/pic9/201509/apic14979.jpg").asBitmap().into(((Item1ViewHolder) holder).ivFindDetailsItem1Head);
            ((Item1ViewHolder) holder).ivFindDetailsItem1Head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OtherFindActivity.class);
                    context.startActivity(intent);
                }
            });
        }
        if (holder instanceof Item2ViewHolder) {
            //条目二布局
            Glide.with(context).load("http://pics.sc.chinaz.com/files/pic/pic9/201510/apic15884.jpg").asBitmap().into(((Item2ViewHolder) holder).ivFindDetailsItem2Head);
            ((Item2ViewHolder) holder).ivFindDetailsItem2Head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OtherFindActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0  || position == 10){
            return ITEM_ONE ;
        }
        return ITEM_TWO;
    }

    //条目一布局
    public class Item1ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_find_details_item1_head)
        CircleImageView ivFindDetailsItem1Head;
        @BindView(R.id.tv_find_details_item1_name)
        TextView tvFindDetailsItem1Name;
        @BindView(R.id.tv_find_details_item1_time)
        TextView tvFindDetailsItem1Time;
        @BindView(R.id.tv_find_details_item1_content)
        TextView tvFindDetailsItem1Content;
        @BindView(R.id.tv_find_details_item1_reply)
        TextView tvFindDetailsItem1Reply;

        public Item1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //条目二布局
    public class Item2ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_find_details_item2_head)
        CircleImageView ivFindDetailsItem2Head;
        @BindView(R.id.tv_find_details_item2_name)
        TextView tvFindDetailsItem2Name;
        @BindView(R.id.tv_find_details_item1_time)
        TextView tvFindDetailsItem1Time;
        @BindView(R.id.tv_find_details_item2_call_name)
        TextView tvFindDetailsItem2CallName;
        @BindView(R.id.tv_find_details_item2_call_content)
        TextView tvFindDetailsItem2CallContent;
        @BindView(R.id.tv_find_details_item2_return)
        TextView tvFindDetailsItem2Return;
        @BindView(R.id.tv_find_details_item2_time)
        TextView tvFindDetailsItem2Time;

        public Item2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
