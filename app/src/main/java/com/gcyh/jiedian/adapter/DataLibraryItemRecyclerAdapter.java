package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.LibraryDataList;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.library.activity.DataLibraryDetailsListActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/5/8.
 */

public class DataLibraryItemRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM_ONE = 1;
    private static final int ITEM_TWO = 2;
    private Context context;
    private List<LibraryDataList.ResponseParamsBean> list;
    private int type;

    public DataLibraryItemRecyclerAdapter(FragmentActivity activity, List<LibraryDataList.ResponseParamsBean> mDatas, int type) {
        this.context = activity;
        this.list = mDatas;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_ONE) {
            Item1ViewHolder item1holder = new Item1ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_datalibrary_item1, parent, false));
            return item1holder;
        } else if (viewType == ITEM_TWO) {
            Item2ViewHolder item2holder = new Item2ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_datalibrary_item2, parent, false));
            return item2holder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final LibraryDataList.ResponseParamsBean.DatabaseBean dataBean = list.get(position).getDatabase();
        if (holder instanceof Item1ViewHolder) {
            //是否收藏
            int collection = list.get(position).getCollection();
            if (collection == 1){
                //已收藏
                ((Item1ViewHolder) holder).ivDatalibraryItem1Collect.setVisibility(View.VISIBLE);
            }else {
                ((Item1ViewHolder) holder).ivDatalibraryItem1Collect.setVisibility(View.INVISIBLE);
            }
            //收藏点击事件
            ((Item1ViewHolder) holder).ivDatalibraryItem1Collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //取消收藏
                    ((Item1ViewHolder) holder).ivDatalibraryItem1Collect.setVisibility(View.INVISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type" , 3);
                    bundle.putInt("id" , dataBean.getId());
                    EventBusUtil.postEvent(EventBusCode.DELETE_COLLECT,bundle);
                }
            });
            // 图片
            if (!TextUtils.isEmpty(dataBean.getImage()))
            Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE + dataBean.getImage()+".png").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.library_data_item).into(((Item1ViewHolder) holder).ivDatalibraryItem1Pic);
             //条目点击事件
            ((Item1ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DataLibraryDetailsListActivity.class);
                    intent.putExtra("id" , list.get(position).getDatabase().getId()) ;
                    intent.putExtra("collect" , list.get(position).getCollection()) ;
                    context.startActivity(intent);
                }
            });
            //标题
            ((Item1ViewHolder) holder).tvDatalibraryItem1Title.setText(dataBean.getTitle());
            //英文
            ((Item1ViewHolder) holder).tvDatalibraryItem1TitleEnglish.setText(dataBean.getTitleEnglish());
            //内容
            ((Item1ViewHolder) holder).tvDatalibraryItem1Content.setText("简介："+dataBean.getDataDetail());
            //时间
            String createTime = dataBean.getCreateTime();
            String time = createTime.substring(0, 10);
            ((Item1ViewHolder) holder).tvDatalibraryItem1Data.setText(time);
        }

        if (holder instanceof Item2ViewHolder) {
            //是否收藏
            int collection = list.get(position).getCollection();
            if (collection == 1){
                //已收藏
                ((Item2ViewHolder) holder).ivDatalibraryItem2Collect.setVisibility(View.VISIBLE);
            }else {
                ((Item2ViewHolder) holder).ivDatalibraryItem2Collect.setVisibility(View.INVISIBLE);
            }
            //收藏点击事件
            ((Item2ViewHolder) holder).ivDatalibraryItem2Collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //取消收藏
                    ((Item2ViewHolder) holder).ivDatalibraryItem2Collect.setVisibility(View.INVISIBLE);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type" , 3);
                    bundle.putInt("id" , dataBean.getId());
                    EventBusUtil.postEvent(EventBusCode.DELETE_COLLECT,bundle);
                }
            });
            // 图片
            if (!TextUtils.isEmpty(dataBean.getImage())){
                Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE + dataBean.getImage()+".png").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.library_data_item).into(((Item2ViewHolder) holder).ivDatalibraryItem2Pic);
            }
            //标题
            ((Item2ViewHolder) holder).tvDatalibraryItem2Title.setText(dataBean.getTitle());
            //内容
            ((Item2ViewHolder) holder).tvDatalibraryItem2Content.setText(dataBean.getDataDetail());
            //条目点击事件
            ((Item2ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DataLibraryDetailsListActivity.class);
                    intent.putExtra("id" , dataBean.getId()) ;
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

        if (type == 1) {
            return ITEM_ONE;   //条目一  一列
        } else if (type == 2) {
            return ITEM_TWO;   //条目二   二列

        }
        return -1;

    }

    //条目一布局
    public class Item1ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_datalibrary_item1_pic)
        ImageView ivDatalibraryItem1Pic;
        @BindView(R.id.tv_datalibrary_item1_title)
        TextView tvDatalibraryItem1Title;
        @BindView(R.id.iv_datalibrary_item1_collect)
        ImageView ivDatalibraryItem1Collect;
        @BindView(R.id.tv_datalibrary_item1_title_english)
        TextView tvDatalibraryItem1TitleEnglish;
        @BindView(R.id.tv_datalibrary_item1_content)
        TextView tvDatalibraryItem1Content;
        @BindView(R.id.tv_datalibrary_item1_data)
        TextView tvDatalibraryItem1Data;

        public Item1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //条目二布局
    public class Item2ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_datalibrary_item2_pic)
        ImageView ivDatalibraryItem2Pic;
        @BindView(R.id.tv_datalibrary_item2_title)
        TextView tvDatalibraryItem2Title;
        @BindView(R.id.iv_datalibrary_item2_collect)
        ImageView ivDatalibraryItem2Collect;
        @BindView(R.id.tv_datalibrary_item2_content)
        TextView tvDatalibraryItem2Content;

        public Item2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
