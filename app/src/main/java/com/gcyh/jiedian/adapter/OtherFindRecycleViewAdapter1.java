package com.gcyh.jiedian.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.OtherFindList;

import java.util.List;

/**
 * Created by Administrator on 2018/6/14.
 */

public class OtherFindRecycleViewAdapter1 extends BaseQuickAdapter<OtherFindList.ResponseParamsBean.UserPostFabulousDTOtListBean, BaseViewHolder> {


    public OtherFindRecycleViewAdapter1(int activity_other_find_item, List<OtherFindList.ResponseParamsBean.UserPostFabulousDTOtListBean> list) {
        super(activity_other_find_item , list);
    }

    @Override
    protected void convert(BaseViewHolder helper, OtherFindList.ResponseParamsBean.UserPostFabulousDTOtListBean item) {
//          helper.setText(R.id.tv_other_find_item_name,item.getUserpost().)

    }
}
