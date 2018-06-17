package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.CommentDetailsList;
import com.gcyh.jiedian.find.activity.FindDetailsActivity;

import java.util.List;

/**
 * Created by Caizhiguang on 2018/6/10.
 */

public class FindDetailsRecycleViewAdapter1 extends BaseQuickAdapter<CommentDetailsList, BaseViewHolder> {


    public FindDetailsRecycleViewAdapter1(@Nullable List<CommentDetailsList> data) {
        super(data);

        setMultiTypeDelegate(new MultiTypeDelegate<CommentDetailsList>() {
            @Override
            protected int getItemType(CommentDetailsList entity) {
                //根据你的实体类来判断布局类型
                return entity.type;
            }
        });
        getMultiTypeDelegate().registerItemType(CommentDetailsList.FIRST_TYPE, R.layout.activity_find_details_item1)
                .registerItemType(CommentDetailsList.SECOND_TYPE, R.layout.activity_find_details_item2);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentDetailsList item) {
        switch (helper.getItemViewType()) {
            case CommentDetailsList.FIRST_TYPE:

                break;
            case CommentDetailsList.SECOND_TYPE:

                break;
        }
    }


}
