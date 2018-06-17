package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.my.activity.DefaultHeadActivity;
import com.gcyh.jiedian.util.SPUtil;

/**
 * Created by Caizhiguang on 2018/5/18.
 */

public class DefaultHeadAdapter extends BaseAdapter {

    int[] imageRes = {
            R.mipmap.material_man,
            R.mipmap.material_women,
            R.mipmap.teacher_man,
            R.mipmap.teacher_women,
            R.mipmap.stylist_man,
            R.mipmap.stylist_women,
            R.mipmap.worker_man,
            R.mipmap.worker_women,
            R.mipmap.student_man,
            R.mipmap.student_women,
        };

    private Context context;

    public DefaultHeadAdapter(DefaultHeadActivity defaultHeadActivity) {
        this.context = defaultHeadActivity;
    }

    @Override
    public int getCount() {
        return imageRes.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //通过布局填充器LayoutInflater填充网格单元格内的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.default_head_item, null);
        //使用findViewById分别找到单元格内布局的图片以及文字
        ImageView pic = (ImageView) inflate.findViewById(R.id.iv_default_head_pic);  // 图片
        ImageView selectPic = (ImageView) inflate.findViewById(R.id.iv_default_head_select);  //是否选中图片
        //引用数组内元素设置布局内图片以及文字的内容
        pic.setImageResource(imageRes[position]);
        int selectPosition = SPUtil.getInt(context, "Default_Head_Select", -1);
        if (position == selectPosition){
            selectPic.setVisibility(View.VISIBLE);
        }else {
            selectPic.setVisibility(View.GONE );
        }
        //返回值一定为单元格整体布局v
        return inflate;

    }
}
