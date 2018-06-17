package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.LibraryProjectList;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.library.activity.NoteLibraryDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/5/2.
 */

public class BeerShopFragmentItemAdapter extends RecyclerView.Adapter<BeerShopFragmentItemAdapter.MyViewHolder> {

    private Context context;
    private List<LibraryProjectList.ResponseParamsBean.NodeDataListBean> list;

    public BeerShopFragmentItemAdapter(Context context, List<LibraryProjectList.ResponseParamsBean.NodeDataListBean> datas) {
        this.context = context;
        this.list = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_beer_shop_item_recycleview_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LibraryProjectList.ResponseParamsBean.NodeDataListBean dataBean = list.get(position);
        if (!TextUtils.isEmpty(dataBean.getImageName())){
            Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE + dataBean.getImageName() + ".png").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.ar_bg).into(holder.ivBeerShopItemRecycleViewItem);
        }
        holder.tvName.setText(dataBean.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , NoteLibraryDetailsActivity.class) ;
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_beer_shop_item_recycleView_item)
        ImageView ivBeerShopItemRecycleViewItem;
        @BindView(R.id.tv_name)
        TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
