package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.OtherFindList;
import com.gcyh.jiedian.find.activity.FindDetailsActivity;
import com.gcyh.jiedian.find.activity.OtherFindActivity;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.widget.CircleImageView;
import com.gcyh.jiedian.widget.GlideCircleTransform;
import com.gcyh.jiedian.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/5/7.
 */

public class OtherFindRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_HEAD = 0;
    private static final int ITEM_ONE = 1;
    private Context context;
    private List<OtherFindList.ResponseParamsBean.UserPostFabulousDTOtListBean> list;
    private OtherFindList.ResponseParamsBean.UserInfoDTOBean userInfo ;
    private List<String> data = new ArrayList<String>();
    ;

    public OtherFindRecycleViewAdapter(OtherFindActivity otherFindActivity, OtherFindList.ResponseParamsBean.UserInfoDTOBean userInfo, List<OtherFindList.ResponseParamsBean.UserPostFabulousDTOtListBean> mDatas) {
        this.context = otherFindActivity;
        this.userInfo = userInfo ;
        this.list = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_HEAD) {
            HeadViewHolder headItemholder = new HeadViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_other_find_head, parent, false));
            return headItemholder;
        } else if (viewType == ITEM_ONE) {
            Item1ViewHolder itemholder = new Item1ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_other_find_item, parent, false));
            return itemholder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //数据源
        data.clear();
        initData();

        if (holder instanceof HeadViewHolder) {
            //头布局
            //头像
            if (!TextUtils.isEmpty(userInfo.getUserInfo().getPhoto())){
                Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE+userInfo.getUserInfo().getPhoto()+".png").asBitmap().error(R.mipmap.my_head).into(((HeadViewHolder) holder).ivOtherFindHead);
            }
            //背景图


            //点击关注
            ((HeadViewHolder) holder).ivOtherFindNotice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else if (holder instanceof Item1ViewHolder) {
            Glide.with(context).load("http://pics.sc.chinaz.com/files/pic/pic9/201511/apic16692.jpg").asBitmap().into(((Item1ViewHolder) holder).ivOtherFindItemHead);

            //类型列表
            LinearLayoutManager typeLayoutManager = new LinearLayoutManager(context);
            //设置为垂直布局，这也默认的
            typeLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            //设置布局管理器
            ((Item1ViewHolder) holder).recycleViewOtherFindItemType.setLayoutManager(typeLayoutManager);
            //适配器
            FindItemTypeRecyclerAdapter typeAdapter = new FindItemTypeRecyclerAdapter(context, data);
            ((Item1ViewHolder) holder).recycleViewOtherFindItemType.setAdapter(typeAdapter);


            //图片列表
            GridLayoutManager piclayoutManager = new GridLayoutManager(context, 4);
            ((Item1ViewHolder) holder).recycleViewOtherFindItemPic.setLayoutManager(piclayoutManager);
            FindItemPicRecyclerAdapter picAdapter = new FindItemPicRecyclerAdapter(context, data);
            ((Item1ViewHolder) holder).recycleViewOtherFindItemPic.setAdapter(picAdapter);

            ((Item1ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FindDetailsActivity.class);
                    context.startActivity(intent);
                }
            });

        }

    }

    private void initData() {
        data.add("http://pics.sc.chinaz.com/files/pic/pic9/201511/apic16083.jpg");
        data.add("http://pics.sc.chinaz.com/files/pic/pic9/201403/apic235.jpg");
        data.add("http://7xi8d6.com1.z0.glb.clouddn.com/20171219115747_tH0TN5_Screenshot.jpeg");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //头布局
    public class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_other_find_pic)
        ImageView ivOtherFindPic;
        @BindView(R.id.iv_other_find_head)
        CircleImageView ivOtherFindHead;
        @BindView(R.id.tv_other_find_name)
        TextView tvOtherFindName;
        @BindView(R.id.iv_other_find_A)
        ImageView ivOtherFindA;
        @BindView(R.id.iv_other_find_B)
        ImageView ivOtherFindB;
        @BindView(R.id.iv_other_find_S)
        ImageView ivOtherFindS;
        @BindView(R.id.iv_other_find_rank)
        ImageView ivOtherFindRank;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.iv_other_find_notice)
        ImageView ivOtherFindNotice;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //条目一布局
    public class Item1ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_other_find_item_head)
        CircleImageView ivOtherFindItemHead;
        @BindView(R.id.tv_other_find_item_name)
        TextView tvOtherFindItemName;
        @BindView(R.id.tv_other_find_item_time)
        TextView tvOtherFindItemTime;
        @BindView(R.id.recycleView_other_find_item_type)
        RecyclerView recycleViewOtherFindItemType;
        @BindView(R.id.tv_other_find_item_content)
        TextView tvOtherFindItemContent;
        @BindView(R.id.recycleView_other_find_item_pic)
        RecyclerView recycleViewOtherFindItemPic;
        @BindView(R.id.cb_other_find_item_zan_number)
        CheckBox cbOtherFindItemZanNumber;
        @BindView(R.id.tv_other_find_item_comment_number)
        TextView tvOtherFindItemCommentNumber;
        @BindView(R.id.tv_other_find_item_report)
        TextView tvOtherFindItemReport;
        @BindView(R.id.ll_other_find_item_comment)
        LinearLayout llOtherFindItemComment;

        public Item1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_HEAD;
        }
        return ITEM_ONE;
    }

    static class ViewHolder {
        @BindView(R.id.iv_other_find_item_head)
        ImageView ivOtherFindItemHead;
        @BindView(R.id.tv_other_find_item_name)
        TextView tvOtherFindItemName;
        @BindView(R.id.tv_other_find_item_time)
        TextView tvOtherFindItemTime;
        @BindView(R.id.recycleView_other_find_item_type)
        RecyclerView recycleViewOtherFindItemType;
        @BindView(R.id.tv_other_find_item_content)
        TextView tvOtherFindItemContent;
        @BindView(R.id.recycleView_other_find_item_pic)
        RecyclerView recycleViewOtherFindItemPic;
        @BindView(R.id.cb_other_find_item_zan_number)
        CheckBox cbOtherFindItemZanNumber;
        @BindView(R.id.tv_other_find_item_comment_number)
        TextView tvOtherFindItemCommentNumber;
        @BindView(R.id.tv_other_find_item_report)
        TextView tvOtherFindItemReport;
        @BindView(R.id.ll_other_find_item_comment)
        LinearLayout llOtherFindItemComment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
