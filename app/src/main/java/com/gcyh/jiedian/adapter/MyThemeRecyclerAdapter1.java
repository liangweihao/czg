package com.gcyh.jiedian.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gcyh.jiedian.entity.MyThemeList;

import java.util.List;

/**
 * Created by Caizhiguang on 2018/6/15.
 */

public class MyThemeRecyclerAdapter1  extends BaseQuickAdapter<MyThemeList.ResponseParamsBean.UserPostFabulousDTOtListBean , BaseViewHolder> {

    public MyThemeRecyclerAdapter1(int layoutId, List<MyThemeList.ResponseParamsBean.UserPostFabulousDTOtListBean> list) {
        super(layoutId , list);

    }

    @Override
    protected void convert(BaseViewHolder helper, MyThemeList.ResponseParamsBean.UserPostFabulousDTOtListBean item) {
        //背景


    }
}
