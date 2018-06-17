package com.gcyh.jiedian.adapter;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;

import java.util.List;

/**
 * Created by Caizhiguang on 2018/4/20.
 */
public class findClassiflRecycleViewAdapter extends RecyclerView.Adapter<ViewHolder>{

    public static enum ITEM_TYPE {
        ITEM_TYPE_Theme,
        ITEM_TYPE_Video
    }
    //数据集
    public List<Integer> mdatas;
    private TextView theme;
    private TextView content;

    public findClassiflRecycleViewAdapter(List<Integer> datas){
        super();
        this.mdatas = datas;
    }


    public static interface  OnItemClickListener{
        void onItemClick(View view, int positon);
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE.ITEM_TYPE_Theme.ordinal()){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classifl_theme_item,parent,false);

            return new ThemeVideoHolder(view);

        }else if(viewType == ITEM_TYPE.ITEM_TYPE_Video.ordinal()){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classifl_content_item,parent,false);
            return new VideoViewHolder(view);

        }
          return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (holder instanceof ThemeVideoHolder){

            theme.setText("商品");

        }else if (holder instanceof VideoViewHolder){

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle() ;
                    bundle.putInt(EventBusCode.EVENT_BUS_CODE , position);
                    EventBusUtil.postEvent(EventBusCode.CODE_FIND_DIAGLO , bundle);
                }
            });
        }

    }

    public int getItemViewType(int position){

        return position % 13 == 0 ? ITEM_TYPE.ITEM_TYPE_Theme.ordinal() : ITEM_TYPE.ITEM_TYPE_Video.ordinal();
    }


    @Override
    public int getItemCount() {
        return mdatas.size();
    }


    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == ITEM_TYPE.ITEM_TYPE_Theme.ordinal()
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }


    public class ThemeVideoHolder extends ViewHolder{

        public ThemeVideoHolder(View itemView) {
            super(itemView);
            theme = (TextView) itemView.findViewById(R.id.tv_theme);
        }
    }

    public class VideoViewHolder extends ViewHolder {

        public VideoViewHolder(View itemView) {
            super(itemView);

            content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}

