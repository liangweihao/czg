package com.gcyh.jiedian.find.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.widget.CircleImageView;

/**
 * Created by Administrator on 2018/6/10.
 */

public class HeaderLayout extends LinearLayout {


    private TextView tvFindDetailsHeadCommentCount;

    public HeaderLayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_find_details_head, this);
        tvFindDetailsHeadCommentCount = (TextView) view.findViewById(R.id.tv_find_details_head_comment_count);
    }

    //量取view此时Y轴的距离
    public int getDistanceY() {
        int[] location = new int[2];
        tvFindDetailsHeadCommentCount.getLocationOnScreen(location);
        int y = location[1];
        return y;
    }

}
