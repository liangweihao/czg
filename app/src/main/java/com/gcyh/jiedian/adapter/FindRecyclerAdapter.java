package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.FindList;
import com.gcyh.jiedian.find.activity.FindDetailsActivity;
import com.gcyh.jiedian.find.activity.OtherFindActivity;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.widget.CircleImageView;
import com.gcyh.jiedian.widget.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/4/18.
 */

public class FindRecyclerAdapter extends RecyclerView.Adapter<FindRecyclerAdapter.MyViewHolder> {

    private List<FindList.ResponseParamsBean> list;
    private Context context;
    private List<String> data = new ArrayList<String>();

    public FindRecyclerAdapter(FragmentActivity activity, List<FindList.ResponseParamsBean> mDatas) {
        this.context = activity;
        this.list = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_find_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        //数据源
        data.clear();
        initData();
        final FindList.ResponseParamsBean.UserInfoBean userInfo = list.get(position).getUserInfo();
        final FindList.ResponseParamsBean.UserPostFabulousDTOBean.UserpostBean userpost = list.get(position).getUserPostFabulousDTO().getUserpost();
        //头像
        if (!TextUtils.isEmpty(userInfo.getPhoto())){
            Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE+userInfo.getPhone()+".png").asBitmap().error(R.mipmap.my_head).into(holder.ivFindItemHead);
        }
        //昵称
        holder.tvFindItemName.setText(userInfo.getNameNick());
        //时间
        holder.tvFindItemTime.setText(userpost.getCreateDate().substring(0 , 10));
        //详情
        holder.tvFindItemContent.setText(userpost.getPostDetail());
        //点赞数量
        holder.ivFindItemZan.setText("(点赞"+userpost.getLikeNumber()+")");
        //评论数量
        holder.tvFindItemComment.setText("(评论"+userpost.getCommentNumber()+"）");

        //类型列表
        LinearLayoutManager typeLayoutManager = new LinearLayoutManager(context);
        //设置为垂直布局，这也默认的
        typeLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置布局管理器
        holder.recycleViewFindItemType.setLayoutManager(typeLayoutManager);
        //适配器
        FindItemTypeRecyclerAdapter typeAdapter = new FindItemTypeRecyclerAdapter(context, data);
        holder.recycleViewFindItemType.setAdapter(typeAdapter);


        //图片列表
        if (!TextUtils.isEmpty(userpost.getImage())){

        }
        GridLayoutManager piclayoutManager = new GridLayoutManager(context, 4);
        holder.recycleViewFindItemPic.setLayoutManager(piclayoutManager);
        FindItemPicRecyclerAdapter picAdapter = new FindItemPicRecyclerAdapter(context, data);
        holder.recycleViewFindItemPic.setAdapter(picAdapter);

        //条目点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FindDetailsActivity.class);
                context.startActivity(intent);
            }
        });
        //头像点击事件
        holder.ivFindItemHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherFindActivity.class);
                intent.putExtra("userCode" , userInfo.getUserCode()+"") ;
                intent.putExtra("nameNick" , userInfo.getNameNick()) ;
                context.startActivity(intent);
            }
        });
        //是否点赞
        if (list.get(position).getUserPostFabulousDTO().getIsFabulous() == 1){
            holder.cbFindItemZan.setChecked(true);
        }else {
            holder.cbFindItemZan.setChecked(false);
        }

        //点赞--点击事件
        holder.llFindItemZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = holder.cbFindItemZan.isChecked();
                if (checked){
                    holder.cbFindItemZan.setChecked(false);
                    //取消赞
                    Bundle bundle1 = new Bundle() ;
                    bundle1.putString("postid" , list.get(position).getUserPostFabulousDTO().getUserpost().getId()+"");  //条目id
                    EventBusUtil.postEvent(EventBusCode.DELETE_ZAN , bundle1);
                    holder.ivFindItemZan.setText("(点赞"+userpost.getLikeNumber()+")");
                }else {
                    holder.cbFindItemZan.setChecked(true);
                    //开启动画---点赞
                    int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                    holder.cbFindItemZan.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("AnimtionZan", startLocation);
                    bundle.putInt("Width" , holder.cbFindItemZan.getMeasuredWidth());
                    bundle.putInt("Height" , holder.cbFindItemZan.getMeasuredHeight());
                    EventBusUtil.postEvent(EventBusCode.ANIMATION_ZAN,bundle);
                    //点赞
                    Bundle bundle2 = new Bundle() ;
                    bundle2.putString("postid" , list.get(position).getUserPostFabulousDTO().getUserpost().getId()+"");  //条目id
                    EventBusUtil.postEvent(EventBusCode.ZAN , bundle2);
                    holder.ivFindItemZan.setText("(点赞"+userpost.getLikeNumber()+1+")");
                }
            }
        });
        //评论
        holder.llFindItemComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void initData() {
        data.add("http://pics.sc.chinaz.com/files/pic/pic9/201511/apic16083.jpg");
        data.add("http://pics.sc.chinaz.com/files/pic/pic9/201403/apic235.jpg");
        data.add("http://7xi8d6.com1.z0.glb.clouddn.com/20171219115747_tH0TN5_Screenshot.jpeg");
        data.add("http://7xi8d6.com1.z0.glb.clouddn.com/20180109085038_4A7atU_rakukoo_9_1_2018_8_50_25_276.jpeg");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_find_item_head)
        CircleImageView ivFindItemHead;
        @BindView(R.id.tv_find_item_name)
        TextView tvFindItemName;
        @BindView(R.id.tv_find_item_time)
        TextView tvFindItemTime;
        @BindView(R.id.recycleView_find_item_type)
        RecyclerView recycleViewFindItemType;
        @BindView(R.id.tv_find_item_content)
        TextView tvFindItemContent;
        @BindView(R.id.recycleView_find_item_pic)
        RecyclerView recycleViewFindItemPic;
        @BindView(R.id.cb_find_item_zan)
        CheckBox cbFindItemZan;
        @BindView(R.id.iv_find_item_zan)
        TextView ivFindItemZan;
        @BindView(R.id.ll_find_item_zan)
        LinearLayout llFindItemZan;
        @BindView(R.id.tv_find_item_comment)
        TextView tvFindItemComment;
        @BindView(R.id.ll_find_item_comment)
        LinearLayout llFindItemComment;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
