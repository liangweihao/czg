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
import com.gcyh.jiedian.entity.MyThemeList;
import com.gcyh.jiedian.find.activity.FindDetailsActivity;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.my.activity.MyThemeActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.widget.CircleImageView;
import com.gcyh.jiedian.widget.GlideCircleTransform;
import com.gcyh.jiedian.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/4/25.
 */

public class MyThemeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MyThemeList.ResponseParamsBean.UserPostFabulousDTOtListBean> list;
    private MyThemeList.ResponseParamsBean.UserInfoDTOBean userInfos;
    private static final int ITEM_HEAD = 1;
    private static final int ITEM_ONE = 2;

    private List<String> data = new ArrayList<String>();
    ;

    public MyThemeRecyclerAdapter(MyThemeActivity myThemeActivity, List<MyThemeList.ResponseParamsBean.UserPostFabulousDTOtListBean> mDatas, MyThemeList.ResponseParamsBean.UserInfoDTOBean userInfo) {
        this.context = myThemeActivity;
        this.list = mDatas;
        this.userInfos = userInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_HEAD) {
            HeadViewHolder item1holder = new HeadViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_my_theme_head, parent, false));
            return item1holder;
        } else if (viewType == ITEM_ONE) {
            ItemViewHolder item2holder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_my_theme_item, parent, false));
            return item2holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyThemeList.ResponseParamsBean.UserInfoDTOBean.UserInfoBean userInfo = userInfos.getUserInfo();

        if (holder instanceof HeadViewHolder) {

            //头像
            Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE+userInfo.getPhoto()+".png").asBitmap().error(R.mipmap.my_head).into(((HeadViewHolder) holder).ivMyThemeHead);
           //背景图片
            if (!TextUtils.isEmpty(userInfo.getBackImg())&&userInfo.getBackImg().contains("image/")){
                Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE+userInfo.getBackImg()+".png").asBitmap().error(R.mipmap.my_theme_bg).into(((HeadViewHolder) holder).ivMyThemePic);
            }else {
                Glide.with(context).load(userInfo.getBackImg()).asBitmap().error(R.mipmap.my_theme_bg).into(((HeadViewHolder) holder).ivMyThemePic);
            }
            //更换主题背景图
            ((HeadViewHolder) holder).ivMyThemePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBusUtil.postEvent(EventBusCode.CHARGE_THEME_PIC);
                }
            });
            //昵称
            ((HeadViewHolder) holder).tvMyThemeName.setText(userInfo.getNameNick());
            // ABS
            if (userInfos.getAVIP() == 1){
                ((HeadViewHolder) holder).ivMyA.setVisibility(View.VISIBLE);
            }else {
                ((HeadViewHolder) holder).ivMyA.setVisibility(View.GONE);
            }
            if (userInfos.getBVIP() == 1){
                ((HeadViewHolder) holder).ivMyB.setVisibility(View.VISIBLE);
            }else {
                ((HeadViewHolder) holder).ivMyB.setVisibility(View.GONE);
            }
            if (userInfos.getSVIP() == 1){
                ((HeadViewHolder) holder).ivMyS.setVisibility(View.VISIBLE);
            }else {
                ((HeadViewHolder) holder).ivMyS.setVisibility(View.GONE);
            }
            //等级
            setGrade(userInfos.getGrade() , ((HeadViewHolder) holder).ivMyThemeRank);
            //星星数
            ((HeadViewHolder) holder).ratingBar.setStar(userInfos.getStar());

        }

        if (holder instanceof ItemViewHolder) {
          int  positionLocation = position-1 ;
            //头像
            Glide.with(context).load("http://7xi8d6.com1.z0.glb.clouddn.com/20171219224721_wFH5PL_Screenshot.jpeg").asBitmap().into(((ItemViewHolder) holder).ivMyThemeItemHead);

            //数据源
            data.clear();
            initData();

            //类型列表
            LinearLayoutManager typeLayoutManager = new LinearLayoutManager(context);
            //设置为垂直布局，这也默认的
            typeLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
            //设置布局管理器
            ((ItemViewHolder) holder).recycleViewMyThemeItemType.setLayoutManager(typeLayoutManager);
            //适配器
            FindItemTypeRecyclerAdapter typeAdapter = new FindItemTypeRecyclerAdapter(context, data);
            ((ItemViewHolder) holder).recycleViewMyThemeItemType.setAdapter(typeAdapter);


            //图片列表
            GridLayoutManager piclayoutManager = new GridLayoutManager(context, 4);
            ((ItemViewHolder) holder).recycleViewMyThemeItemPic.setLayoutManager(piclayoutManager);
            FindItemPicRecyclerAdapter picAdapter = new FindItemPicRecyclerAdapter(context, data);
            ((ItemViewHolder) holder).recycleViewMyThemeItemPic.setAdapter(picAdapter);

            ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FindDetailsActivity.class);
                    context.startActivity(intent);
                }
            });


        }

    }

    @Override
    public int getItemCount() {
        return list.size() +1 ;
    }

    //头布局
    public class HeadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_my_theme_pic)
        ImageView ivMyThemePic;
        @BindView(R.id.iv_my_theme_head)
        CircleImageView ivMyThemeHead;
        @BindView(R.id.tv_my_theme_name)
        TextView tvMyThemeName;
        @BindView(R.id.iv_my_A)
        ImageView ivMyA;
        @BindView(R.id.iv_my_B)
        ImageView ivMyB;
        @BindView(R.id.iv_my_S)
        ImageView ivMyS;
        @BindView(R.id.iv_my_theme_rank)
        ImageView ivMyThemeRank;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //条目布局
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_my_theme_item_head)
        CircleImageView ivMyThemeItemHead;
        @BindView(R.id.tv_my_theme_item_name)
        TextView tvMyThemeItemName;
        @BindView(R.id.tv_my_theme_item_time)
        TextView tvMyThemeItemTime;
        @BindView(R.id.recycleView_my_theme_item_type)
        RecyclerView recycleViewMyThemeItemType;
        @BindView(R.id.tv_my_theme_item_content)
        TextView tvMyThemeItemContent;
        @BindView(R.id.recycleView_my_theme_item_pic)
        RecyclerView recycleViewMyThemeItemPic;
        @BindView(R.id.cb_my_theme_item_zan_number)
        CheckBox cbMyThemeItemZanNumber;
        @BindView(R.id.tv_my_theme_item_comment_number)
        TextView tvMyThemeItemCommentNumber;
        @BindView(R.id.ll_my_theme_item_comment)
        LinearLayout llMyThemeItemComment;

        public ItemViewHolder(View itemView) {
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


    private void initData() {
        data.add("http://pics.sc.chinaz.com/files/pic/pic9/201509/apic14702.jpg");
        data.add("http://pics.sc.chinaz.com/files/pic/pic9/201310/apic1621.jpg");
        data.add("http://7xi8d6.com1.z0.glb.clouddn.com/20171219115747_tH0TN5_Screenshot.jpeg");
        data.add("http://pics.sc.chinaz.com/files/pic/pic9/201408/apic5414.jpg");
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
