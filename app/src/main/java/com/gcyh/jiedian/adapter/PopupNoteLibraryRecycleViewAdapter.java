package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/23.
 */

public class PopupNoteLibraryRecycleViewAdapter extends RecyclerView.Adapter<PopupNoteLibraryRecycleViewAdapter.MyViewHolder> {

    private String[] list;
    private Context context;
    private String type ;
    private boolean defaults = true ;

    public PopupNoteLibraryRecycleViewAdapter(FragmentActivity activity, String[] mDatas, String type) {
        this.context = activity;
        this.list = mDatas;
        this.type = type ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.classifl_content_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        //按下状态监听
        holder.tvContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP://松开事件发生后执行代码的区域
                        defaults = true ;
                        break;
                    case MotionEvent.ACTION_DOWN://按住事件发生后执行代码的区域
                        defaults = false ;
                        notifyItemChanged(0);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        holder.tvContent.setText(list[position]);

        if (position == 0 && defaults){
            holder.tvContent.setChecked(true);
        }else {
            holder.tvContent.setChecked(false);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", position);
                if (type.equals("materials")){
                    EventBusUtil.postEvent(EventBusCode.CODE_DISMESS_POPW_MATERIALS, bundle);
                }else if (type.equals("part")){
                    EventBusUtil.postEvent(EventBusCode.CODE_DISMESS_POPW_PART, bundle);
                }else if (type.equals("space")){
                    EventBusUtil.postEvent(EventBusCode.CODE_DISMESS_POPW_SPACE, bundle);
                }else if (type.equals("hot")){
                    EventBusUtil.postEvent(EventBusCode.CODE_DISMESS_POPW_HOT, bundle);
                }else if (type.equals("process")){
                    EventBusUtil.postEvent(EventBusCode.CODE_DISMESS_POPW_PROCESS, bundle);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_content)
        CheckBox tvContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
