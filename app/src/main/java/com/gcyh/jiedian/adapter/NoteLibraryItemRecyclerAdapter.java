package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.LibraryNoteList;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.library.activity.NoteLibraryDetailsActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.VerticalProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/5/7.
 */

public class NoteLibraryItemRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_ONE = 1;
    private static final int ITEM_TWO = 2;
    private Context context;
    private List<LibraryNoteList.ResponseParamsBean> list;
    private int type;
    private Bundle bundle;
    private int location;


    public NoteLibraryItemRecyclerAdapter(FragmentActivity activity, List<LibraryNoteList.ResponseParamsBean> mDatas, int type) {
        this.context = activity;
        this.list = mDatas;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_ONE) {
            Item1ViewHolder item1holder = new Item1ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_notelibrary_item1, parent, false));
            return item1holder;
        } else if (viewType == ITEM_TWO) {
            Item2ViewHolder item2holder = new Item2ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_notelibrary_item2, parent, false));
            return item2holder;
        }

        return null;

    }

    public void setProcess(int position, Bundle bundle) {
        this.location = position;
        this.bundle = bundle;
        notifyItemChanged(position);
        Log.i("====", "setProcess: ======"+type);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final LibraryNoteList.ResponseParamsBean.NodeDataBean dataBean = list.get(position).getNodeData();

        if (holder instanceof Item1ViewHolder) {
            if (location == position) {
                ((Item1ViewHolder) holder).verticalProgressBar1.setProgress(bundle.getInt("progress"));
            }
            //如果已下载--隐藏进度条
            final String name = list.get(position).getNodeData().getARImageName();
            String arImage = SPUtil.getString(context, name, "");
            if (!TextUtils.isEmpty(arImage)) {
                ((Item1ViewHolder) holder).verticalProgressBar1.setVisibility(View.INVISIBLE);
            } else {
                ((Item1ViewHolder) holder).verticalProgressBar1.setVisibility(View.VISIBLE);
            }

            //图片
            if (!TextUtils.isEmpty(dataBean.getImageName())) {
                Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE + dataBean.getImageName() + ".png").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.jiedian_item).into(((Item1ViewHolder) holder).ivNotelibraryItem1Pic);
            }
            Glide.with(context).load(R.mipmap.library_note_fire).placeholder(R.mipmap.library_note_fire).error(R.mipmap.library_note_fire).into(((Item1ViewHolder) holder).ivNotelibraryItem1Fire);
            ((Item1ViewHolder) holder).tvNotelibraryItem1Title.setText(dataBean.getTitle());   //标题
            ((Item1ViewHolder) holder).tvNotelibraryItem1Price.setText(dataBean.getMoney() + "");  //线币数
            ((Item1ViewHolder) holder).tvNotelibraryItem1Number.setText(dataBean.getPeople() + "人购买     " + dataBean.getRate() + "%好评");  //购买人数和评价率
            if (list.get(position).getUserCollection() == 1) {
                //有红色心---已收藏
                ((Item1ViewHolder) holder).ivNotelibraryItem1Collect.setVisibility(View.VISIBLE);   //收藏
            } else {
                //无红色心---未收藏
                ((Item1ViewHolder) holder).ivNotelibraryItem1Collect.setVisibility(View.INVISIBLE);  //未收藏
            }
            //经验值
            if (dataBean.getExp() != 0) {
                //有经验值
                ((Item1ViewHolder) holder).ivNotelibraryItem1Give.setVisibility(View.VISIBLE);
                ((Item1ViewHolder) holder).tvNotelibraryItem1Exp.setText(dataBean.getExp() + "经验值");
            } else {
                //无经验值
                ((Item1ViewHolder) holder).ivNotelibraryItem1Give.setVisibility(View.INVISIBLE);
                ((Item1ViewHolder) holder).tvNotelibraryItem1Exp.setVisibility(View.INVISIBLE);
            }
            //收藏点击事件
            ((Item1ViewHolder) holder).ivNotelibraryItem1Collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //取消收藏
                    ((Item1ViewHolder) holder).ivNotelibraryItem1Collect.setVisibility(View.INVISIBLE);
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

                    String arImage = SPUtil.getString(context, name, "");  //判断是否下载
                    if (!TextUtils.isEmpty(arImage)) {
                        //跳转到详情
                        Intent intent = new Intent(context, NoteLibraryDetailsActivity.class);
                        intent.putExtra("id" , dataBean.getId()) ;  //条目id
                        intent.putExtra("collect" , list.get(position).getUserCollection()) ;  // 是否收藏
                        intent.putExtra("title" , list.get(position).getNodeData().getTitle()) ;  // 标题
                        intent.putExtra("shopcar" , list.get(position).getShopCard()) ;  // 是否加入购物车
                        context.startActivity(intent);
                    } else {
                        //下载动画启动
                        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                        ((Item1ViewHolder) holder).ivNotelibraryItem1Pic.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                        Bundle bundle1 = new Bundle();
                        bundle1.putIntArray("LIBRARY_DOWNLOAD_ANIMATION", startLocation);
                        EventBusUtil.postEvent(EventBusCode.LIBRARY_DOWNLOAD_ANIMATION, bundle1);
                        //开始下载AR模型
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("url", dataBean.getARImageName());
                        bundle2.putInt("position", position);
                        EventBusUtil.postEvent(EventBusCode.LIBRARY_DOWNLOAD_MODEL, bundle2);
                        Log.i("====", "onClick:====" + name);

                    }

                }
            });

        }
        if (holder instanceof Item2ViewHolder) {
            if (location == position ) {
                ((Item2ViewHolder) holder).verticalProgressBar2.setProgress(bundle.getInt("progress"));
            }
            //如果已下载--隐藏进度条
            final String name = dataBean.getARImageName();
            String arImage = SPUtil.getString(context, name, "");
            if (!TextUtils.isEmpty(arImage) ) {
                ((Item2ViewHolder) holder).verticalProgressBar2.setVisibility(View.INVISIBLE);
            } else {
                ((Item2ViewHolder) holder).verticalProgressBar2.setVisibility(View.VISIBLE);
            }
            //图片
            if (!TextUtils.isEmpty(dataBean.getImageName())) {
                Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE + dataBean.getImageName() + ".png").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.jiedian_item).into(((Item2ViewHolder) holder).ivNotelibraryItem2Pic);
            }
            Glide.with(context).load(R.mipmap.library_note_fire).placeholder(R.mipmap.library_note_fire).error(R.mipmap.library_note_fire).into(((Item2ViewHolder) holder).ivNotelibraryItem2Fire);
            ((Item2ViewHolder) holder).tvNotelibraryItem2Title.setText(dataBean.getTitle());   //标题
            ((Item2ViewHolder) holder).tvNotelibraryItem2Price.setText(dataBean.getMoney() + "");  //线币数
            ((Item2ViewHolder) holder).tvNotelibraryItem2Number.setText(dataBean.getPeople() + "人购买     " + dataBean.getRate() + "%好评");  //购买人数和评价率
            if (list.get(position).getUserCollection() == 1) {
                //有红色心---已收藏
                ((Item2ViewHolder) holder).ivNotelibraryItem2Collect.setVisibility(View.VISIBLE);  //收藏
            } else {
                //无红色心---未收藏
                ((Item2ViewHolder) holder).ivNotelibraryItem2Collect.setVisibility(View.INVISIBLE);  //未收藏
            }
            //收藏点击事件
            ((Item2ViewHolder) holder).ivNotelibraryItem2Collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //取消收藏
                    ((Item2ViewHolder) holder).ivNotelibraryItem2Collect.setVisibility(View.INVISIBLE);
                    Bundle bundle = new Bundle() ;
                    bundle.putInt("type" , 2);
                    bundle.putInt("id" , dataBean.getId());
                    EventBusUtil.postEvent(EventBusCode.DELETE_COLLECT , bundle);

                }
            });
            //经验值
            if (dataBean.getExp() != 0) {
                //有经验值
                ((Item2ViewHolder) holder).ivNotelibraryItem2Give.setVisibility(View.VISIBLE);
                ((Item2ViewHolder) holder).tvNotelibraryItem2Exp.setText(dataBean.getExp() + "经验值");
            } else {
                //无经验值
                ((Item2ViewHolder) holder).ivNotelibraryItem2Give.setVisibility(View.INVISIBLE);
                ((Item2ViewHolder) holder).tvNotelibraryItem2Exp.setVisibility(View.INVISIBLE);
            }

            //条目点击事件
            ((Item2ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String arImage = SPUtil.getString(context, name, "");
                    if (!TextUtils.isEmpty(arImage)) {
                        //跳转到详情
                        Intent intent = new Intent(context, NoteLibraryDetailsActivity.class);
                        intent.putExtra("id" , dataBean.getId()) ;  //条目id
                        intent.putExtra("collect" , list.get(position).getUserCollection()) ;  // 是否收藏
                        intent.putExtra("title" , list.get(position).getNodeData().getTitle()) ;  // 标题
                        intent.putExtra("shopcar" , list.get(position).getShopCard()) ;  // 是否加入购物车
                        context.startActivity(intent);
                    } else {
                        //下载动画启动
                        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                        ((Item2ViewHolder) holder).ivNotelibraryItem2Pic.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                        Bundle bundle1 = new Bundle();
                        bundle1.putIntArray("LIBRARY_DOWNLOAD_ANIMATION", startLocation);
                        EventBusUtil.postEvent(EventBusCode.LIBRARY_DOWNLOAD_ANIMATION, bundle1);
                        //开始下载AR模型
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("url", dataBean.getARImageName());
                        bundle2.putInt("position", position);
                        EventBusUtil.postEvent(EventBusCode.LIBRARY_DOWNLOAD_MODEL, bundle2);

                    }
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
        @BindView(R.id.verticalProgressBar1)
        VerticalProgressBar verticalProgressBar1;
        @BindView(R.id.iv_notelibrary_item1_pic)
        ImageView ivNotelibraryItem1Pic;
        @BindView(R.id.iv_notelibrary_item1_fire)
        ImageView ivNotelibraryItem1Fire;
        @BindView(R.id.tv_notelibrary_item1_title)
        TextView tvNotelibraryItem1Title;
        @BindView(R.id.tv_notelibrary_item1_price)
        TextView tvNotelibraryItem1Price;
        @BindView(R.id.iv_notelibrary_item1_give)
        ImageView ivNotelibraryItem1Give;
        @BindView(R.id.tv_notelibrary_item1_exp)
        TextView tvNotelibraryItem1Exp;
        @BindView(R.id.tv_notelibrary_item1_number)
        TextView tvNotelibraryItem1Number;
        @BindView(R.id.iv_notelibrary_item1_collect)
        ImageView ivNotelibraryItem1Collect;

        public Item1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //条目二布局
    public class Item2ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.verticalProgressBar2)
        VerticalProgressBar verticalProgressBar2;
        @BindView(R.id.iv_notelibrary_item2_fire)
        ImageView ivNotelibraryItem2Fire;
        @BindView(R.id.iv_notelibrary_item2_pic)
        ImageView ivNotelibraryItem2Pic;
        @BindView(R.id.tv_notelibrary_item2_title)
        TextView tvNotelibraryItem2Title;
        @BindView(R.id.tv_notelibrary_item2_price)
        TextView tvNotelibraryItem2Price;
        @BindView(R.id.iv_notelibrary_item2_give)
        ImageView ivNotelibraryItem2Give;
        @BindView(R.id.tv_notelibrary_item2_exp)
        TextView tvNotelibraryItem2Exp;
        @BindView(R.id.tv_notelibrary_item2_number)
        TextView tvNotelibraryItem2Number;
        @BindView(R.id.iv_notelibrary_item2_collect)
        ImageView ivNotelibraryItem2Collect;

        public Item2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
