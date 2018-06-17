package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.productionAR.activity.ProductionARDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/4/25.
 */

public class ProductionARRecyclerAdapter extends RecyclerView.Adapter<ProductionARRecyclerAdapter.MyViewHolder> {

    private List<String> list;
    private Context context;

    public ProductionARRecyclerAdapter(FragmentActivity activity, List<String> mDatas) {
        this.context = activity;
        this.list = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_production_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Glide.with(context).load("").placeholder(R.mipmap.ar_bg).error(R.mipmap.ar_bg).into(holder.ivProductionPic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ProductionARDetailsActivity.class) ;
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_production_pic)
        ImageView ivProductionPic;
        @BindView(R.id.cv_production_pic)
        CardView cvProductionPic;
        @BindView(R.id.tv_production_item_title)
        TextView tvProductionItemTitle;
        @BindView(R.id.tv_production_item_date)
        TextView tvProductionItemDate;
        @BindView(R.id.ll_production_item)
        LinearLayout llProductionItem;
        @BindView(R.id.tv_production_item_title_english)
        TextView tvProductionItemTitleEnglish;
        @BindView(R.id.tv_production_item_content)
        TextView tvProductionItemContent;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
