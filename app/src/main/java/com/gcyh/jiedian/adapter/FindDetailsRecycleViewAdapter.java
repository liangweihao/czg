package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.gcyh.jiedian.find.activity.FindDetailsActivity;
import com.gcyh.jiedian.find.activity.OtherFindActivity;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.widget.CircleImageView;
import com.gcyh.jiedian.widget.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/4/27.
 */

public class FindDetailsRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_HEAD = 0;
    private static final int ITEM_ONE = 1;
    private static final int ITEM_TWO = 2;
    private Context context;
    private List<String> list;
    private List<String> data = new ArrayList<String>();
    private TextView tvFindDetailsHeadCommentCount;
    ;

    public FindDetailsRecycleViewAdapter(FindDetailsActivity findDetailsActivity, List<String> mDatas) {
        this.context = findDetailsActivity;
        this.list = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_HEAD) {
            HeadViewHolder itemholder = new HeadViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_find_details_head, parent, false));
            tvFindDetailsHeadCommentCount = itemholder.tvFindDetailsHeadCommentCount;
            return itemholder;
        } else if (viewType == ITEM_ONE) {
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

        if (holder instanceof HeadViewHolder) {
            //数据源
            data.clear();
            initData();
            //头布局

            //头像
            Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE+""+".png").asBitmap().error(R.mipmap.my_head).into(((HeadViewHolder) holder).ivFindDetailsHead);
            //昵称
            ((HeadViewHolder) holder).tvFindDetailsHeadName.setText("");
            //时间
            ((HeadViewHolder) holder).tvFindDetailsHeadTime.setText("");
            //内容
            ((HeadViewHolder) holder).tvFindDetailsHeadContent.setText("");
            //点赞数量
            ((HeadViewHolder) holder).cbFindDetailsHeadZanNumber.setText("");
            //评论数量
            ((HeadViewHolder) holder).tvFindDetailsHeadCommentNumber.setText("");
            //我的主题--删除   其他主题--举报
             if (true){
                 //我的主题
                 ((HeadViewHolder) holder).tvFindDetailsHeadReport.setText("删除");
             }else {
                 //其他的主题
                 ((HeadViewHolder) holder).tvFindDetailsHeadReport.setText("举报");
             }
            //类型列表
            LinearLayoutManager typeLayoutManager = new LinearLayoutManager(context);
            //设置为垂直布局，这也默认的
            typeLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            //设置布局管理器
            ((HeadViewHolder) holder).recycleViewFindDetailsHeadType.setLayoutManager(typeLayoutManager);
            //适配器
            FindItemTypeRecyclerAdapter typeAdapter = new FindItemTypeRecyclerAdapter(context, data);
            ((HeadViewHolder) holder).recycleViewFindDetailsHeadType.setAdapter(typeAdapter);


            //图片列表
            GridLayoutManager piclayoutManager = new GridLayoutManager(context, 4);
            ((HeadViewHolder) holder).recycleViewFindDetailsHeadPic.setLayoutManager(piclayoutManager);
            FindItemPicRecyclerAdapter picAdapter = new FindItemPicRecyclerAdapter(context, data);
            ((HeadViewHolder) holder).recycleViewFindDetailsHeadPic.setAdapter(picAdapter);
            //点击图片

            //点击头像--跳转到主题
            ((HeadViewHolder) holder).ivFindDetailsHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OtherFindActivity.class);
                    context.startActivity(intent);
                }
            });
        }
        if (holder instanceof Item1ViewHolder) {
            //条目一布局
            Glide.with(context).load("http://pics.sc.chinaz.com/files/pic/pic9/201509/apic14979.jpg").asBitmap().into(((Item1ViewHolder) holder).ivFindDetailsItem1Head);
            //点击头像--跳转到主题
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
            //点击头像--跳转到主题
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

        if (position == 0) {
            return ITEM_HEAD;  //头布局
        } else if (position == 1) {
            return ITEM_ONE;   //条目一
        } else if (position == 2) {
            return ITEM_TWO;   //条目二
        }
        return 2;
    }

    //头布局
    public class HeadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_find_details_head)
        CircleImageView ivFindDetailsHead;
        @BindView(R.id.tv_find_details_head_name)
        TextView tvFindDetailsHeadName;
        @BindView(R.id.recycleView_find_details_head_type)
        RecyclerView recycleViewFindDetailsHeadType;
        @BindView(R.id.tv_find_details_head_time)
        TextView tvFindDetailsHeadTime;
        @BindView(R.id.tv_find_details_head_content)
        TextView tvFindDetailsHeadContent;
        @BindView(R.id.recycleView_find_details_head_pic)
        RecyclerView recycleViewFindDetailsHeadPic;
        @BindView(R.id.cb_find_details_head_zan_number)
        CheckBox cbFindDetailsHeadZanNumber;
        @BindView(R.id.tv_find_details_head_comment_number)
        TextView tvFindDetailsHeadCommentNumber;
        @BindView(R.id.tv_find_details_head_report)
        TextView tvFindDetailsHeadReport;
        @BindView(R.id.ll_ofind_details_head_comment)
        LinearLayout llOfindDetailsHeadComment;
        @BindView(R.id.view_string)
        View viewString;
        @BindView(R.id.tv_find_details_head_comment_count)
        TextView tvFindDetailsHeadCommentCount;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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

    private void initData() {
        data.add("http://pics.sc.chinaz.com/files/pic/pic9/201511/apic16083.jpg");
        data.add("http://7xi8d6.com1.z0.glb.clouddn.com/20171219115747_tH0TN5_Screenshot.jpeg");
        data.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180109085038_4A7atU_rakukoo_9_1_2018_8_50_25_276.jpeg");
    }

    //量取view此时Y轴的距离
    public int getDistanceY() {
        int[] location = new int[2];
        tvFindDetailsHeadCommentCount.getLocationOnScreen(location);
        int y = location[1];
        return y;
    }
}
