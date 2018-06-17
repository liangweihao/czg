package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.LibraryDataListDetails;
import com.gcyh.jiedian.library.activity.DataLibraryDetailsListActivity;
import com.gcyh.jiedian.library.activity.DataLibraryDetailsListDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/5/31.
 */

public class DataLibraryDetailsListAdapter extends RecyclerView.Adapter<DataLibraryDetailsListAdapter.MyViewHolder> {

    private Context context;
    private List<LibraryDataListDetails.ResponseParamsBean> list;

    public DataLibraryDetailsListAdapter(DataLibraryDetailsListActivity dataLibraryDetailsListActivity, List<LibraryDataListDetails.ResponseParamsBean> mDatas) {
        this.context = dataLibraryDetailsListActivity;
        this.list = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_data_library_details_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvDataDetailsListContent.setText(list.get(position).getChapter().getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , DataLibraryDetailsListDetailsActivity.class) ;
                intent.putExtra("position" , position) ;
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_data_details_list_content)
        TextView tvDataDetailsListContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
