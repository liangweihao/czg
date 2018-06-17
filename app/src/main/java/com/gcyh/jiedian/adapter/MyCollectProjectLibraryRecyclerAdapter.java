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
import com.gcyh.jiedian.entity.CollectList;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.library.activity.ProjectLibraryDetailsActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/11.
 */

public class MyCollectProjectLibraryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_ITEM1 = 1;  //左右结构布局
    private static final int TYPE_ITEM2 = 2;  //上下结构布局
    private List<CollectList.ResponseParamsBean.ProjectDataListDTOBean> list;
    private Context context;
    private int type;   //判断  条目类型

    public MyCollectProjectLibraryRecyclerAdapter(FragmentActivity activity, List<CollectList.ResponseParamsBean.ProjectDataListDTOBean> projectLis, int type) {
        this.context = activity;
        this.list = projectLis;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM1) {
            Item1ViewHolder item1holder = new Item1ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_projectlibrary_item1, parent, false));
            return item1holder;
        }

        if (viewType == TYPE_ITEM2) {
            Item2ViewHolder item2holder = new Item2ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_projectlibrary_item2, parent, false));
            return item2holder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final CollectList.ResponseParamsBean.ProjectDataListDTOBean.ProjectDataBean dataBean = list.get(position).getProjectData();
        //item---->左右结构
        if (holder instanceof Item1ViewHolder) {
            //图片
            if (!TextUtils.isEmpty(dataBean.getImage())){
                Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE + dataBean.getImage() + ".png").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.ar_bg).into(((Item1ViewHolder) holder).ivProjectlibraryItem1Pic);
            }
            //火
            Glide.with(context).load(R.mipmap.library_note_fire).placeholder(R.mipmap.library_note_fire).error(R.mipmap.library_note_fire).into(((Item1ViewHolder) holder).ivProjectlibraryItem1Fire);
            //名称
            ((Item1ViewHolder) holder).tvProjectlibraryItem1Title.setText(dataBean.getName());
            //简介
            ((Item1ViewHolder) holder).tvProjectlibraryItem1Content.setText("项目简介："+dataBean.getProjectDetail());
            //线币数
            ((Item1ViewHolder) holder).tvProjectlibraryItem1Price.setText(dataBean.getMoney()+"");
            //经验值
            if (dataBean.getExp() != 0){
                //有经验值
                ((Item1ViewHolder) holder).ivProjectlibraryItem1Give.setVisibility(View.VISIBLE);
                ((Item1ViewHolder) holder).tvProjectlibraryItem1Exp.setText(dataBean.getExp()+"经验值");
            }else {
                //无经验值
                ((Item1ViewHolder) holder).ivProjectlibraryItem1Give.setVisibility(View.INVISIBLE);
                ((Item1ViewHolder) holder).tvProjectlibraryItem1Exp.setVisibility(View.INVISIBLE);
            }
            ((Item1ViewHolder) holder).tvProjectlibraryItem1Number.setText(dataBean.getPeople()+"人购买     "+dataBean.getRate()+"%好评");  //购买人数和评价率
            //收藏点击事件
            ((Item1ViewHolder) holder).ivProjectlibraryItem1Collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //取消收藏
                    ((Item1ViewHolder) holder).ivProjectlibraryItem1Collect.setVisibility(View.INVISIBLE);
                    Bundle bundle = new Bundle() ;
                    bundle.putInt("type" , 2);
                    bundle.putInt("id" , dataBean.getId());
                    EventBusUtil.postEvent(EventBusCode.DELETE_COLLECT , bundle);
                }
            });
            //条目点击事件
            ((Item1ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , ProjectLibraryDetailsActivity.class) ;
                    intent.putExtra("position" , position) ;
                    context.startActivity(intent);
                }
            });
        }

        //item2---->上下结构
        if (holder instanceof Item2ViewHolder) {
            //图片
            if (!TextUtils.isEmpty(dataBean.getImage())){
                Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE + dataBean.getImage() + ".png").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.ar_bg).into(((Item2ViewHolder) holder).ivProjectlibraryItem2Pic);
            }
            //火
            Glide.with(context).load(R.mipmap.library_note_fire).placeholder(R.mipmap.library_note_fire).error(R.mipmap.library_note_fire).into(((Item2ViewHolder) holder).ivProjectlibraryItem2Fire);

            //名称
            ((Item2ViewHolder) holder).tvProjectlibraryItem2Title.setText(dataBean.getName());
            //简介
            ((Item2ViewHolder) holder).tvProjectlibraryItem2Content.setText("项目简介："+dataBean.getProjectDetail());
            //线币数
            ((Item2ViewHolder) holder).tvProjectlibraryItem2Price.setText(dataBean.getMoney()+"");
            //经验值
            if (dataBean.getExp() != 0){
                //有经验值
                ((Item2ViewHolder) holder).ivProjectlibraryItem2Give.setVisibility(View.VISIBLE);
                ((Item2ViewHolder) holder).tvProjectlibraryItem2Exp.setText(dataBean.getExp()+"经验值");
            }else {
                //无经验值
                ((Item2ViewHolder) holder).ivProjectlibraryItem2Give.setVisibility(View.INVISIBLE);
                ((Item2ViewHolder) holder).tvProjectlibraryItem2Exp.setVisibility(View.INVISIBLE);
            }
            ((Item2ViewHolder) holder).tvProjectlibraryItem2Number.setText(dataBean.getPeople()+"人购买     "+dataBean.getRate()+"%好评");  //购买人数和评价率

            //收藏点击事件
            ((Item2ViewHolder) holder).ivProjectlibraryItem2Collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //取消收藏
                    ((Item2ViewHolder) holder).ivProjectlibraryItem2Collect.setVisibility(View.INVISIBLE);
                    Bundle bundle = new Bundle() ;
                    bundle.putInt("type" , 2);
                    bundle.putInt("id" , dataBean.getId());
                    EventBusUtil.postEvent(EventBusCode.DELETE_COLLECT , bundle);
                }
            });
            //条目点击事件
            ((Item2ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , ProjectLibraryDetailsActivity.class) ;
                    intent.putExtra("position" , position) ;
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Item1ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_projectlibrary_item1_pic)
        ImageView ivProjectlibraryItem1Pic;
        @BindView(R.id.iv_projectlibrary_item1_fire)
        ImageView ivProjectlibraryItem1Fire;
        @BindView(R.id.tv_projectlibrary_item1_title)
        TextView tvProjectlibraryItem1Title;
        @BindView(R.id.tv_projectlibrary_item1_content)
        TextView tvProjectlibraryItem1Content;
        @BindView(R.id.tv_projectlibrary_item1_price)
        TextView tvProjectlibraryItem1Price;
        @BindView(R.id.iv_projectlibrary_item1_give)
        ImageView ivProjectlibraryItem1Give;
        @BindView(R.id.tv_projectlibrary_item1_exp)
        TextView tvProjectlibraryItem1Exp;
        @BindView(R.id.tv_projectlibrary_item1_number)
        TextView tvProjectlibraryItem1Number;
        @BindView(R.id.iv_projectlibrary_item1_collect)
        ImageView ivProjectlibraryItem1Collect;

        public Item1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class Item2ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_projectlibrary_item2_fire)
        ImageView ivProjectlibraryItem2Fire;
        @BindView(R.id.iv_projectlibrary_item2_pic)
        ImageView ivProjectlibraryItem2Pic;
        @BindView(R.id.tv_projectlibrary_item2_title)
        TextView tvProjectlibraryItem2Title;
        @BindView(R.id.tv_projectlibrary_item2_content)
        TextView tvProjectlibraryItem2Content;
        @BindView(R.id.tv_projectlibrary_item2_price)
        TextView tvProjectlibraryItem2Price;
        @BindView(R.id.iv_projectlibrary_item2_give)
        ImageView ivProjectlibraryItem2Give;
        @BindView(R.id.tv_projectlibrary_item2_exp)
        TextView tvProjectlibraryItem2Exp;
        @BindView(R.id.tv_projectlibrary_item2_number)
        TextView tvProjectlibraryItem2Number;
        @BindView(R.id.iv_projectlibrary_item2_collect)
        ImageView ivProjectlibraryItem2Collect;

        public Item2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (type == 1) {
            return TYPE_ITEM1;// 左右结构
        } else if (type == 2) {
            return TYPE_ITEM2;// 上下结构
        }
        return -1;
    }
}
