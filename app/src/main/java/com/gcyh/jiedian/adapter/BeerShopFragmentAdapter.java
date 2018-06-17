package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.util.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/5/2.
 */

public class BeerShopFragmentAdapter extends RecyclerView.Adapter<BeerShopFragmentAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;
    private List<String> Datas;

    public BeerShopFragmentAdapter(FragmentActivity fragmentManager, List<String> mDatas) {
        this.context = fragmentManager;
        this.list = mDatas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_beer_shop_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        initData(10) ;
        Glide.with(context).load("http://pics.sc.chinaz.com/files/pic/pic9/201512/apic16964.jpg").asBitmap().into(holder.ivBeerShop);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        //水平滑动
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置布局管理器
        holder.recycleViewBeerShopItem.setLayoutManager(layoutManager);
        holder.recycleViewBeerShopItem .addItemDecoration(new SpaceItemDecoration(25, 0));
        //适配器
//        BeerShopFragmentItemAdapter adapter = new BeerShopFragmentItemAdapter(context , Datas) ;
//        holder.recycleViewBeerShopItem.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_beer_shop)
        ImageView ivBeerShop;
        @BindView(R.id.recycleView_beer_shop_item)
        RecyclerView recycleViewBeerShopItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private int value = 0;

    private void initData(int value) {

        Datas = new ArrayList<String>();
        for (int i = 0; i < value; i++) {
            Datas.add("世界你好" + i);
        }

    }

}
