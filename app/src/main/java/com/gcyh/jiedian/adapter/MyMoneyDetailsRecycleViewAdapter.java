package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.my.activity.MoneyDetailsActivity;
import com.gcyh.jiedian.my.activity.MyMoneyDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/4/24.
 */

public class MyMoneyDetailsRecycleViewAdapter extends RecyclerView.Adapter<MyMoneyDetailsRecycleViewAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;

    public MyMoneyDetailsRecycleViewAdapter(MyMoneyDetailsActivity moneyDetailsActivity, List<String> mDatas) {
        this.context = moneyDetailsActivity;
        this.list = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_money_details_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //类型
        holder.tvMoneyDetailsType.setText("");
        //时间
        holder.tvMoneyDetailsTime.setText("");
        //金额
        if (1 == 1){
            //充值---入
            holder.tvMoneyDetailsNumber.setText("个");
            holder.tvMoneyDetailsNumber.setTextColor(Color.parseColor("#de171e"));
        }else {
            //消费---出
            holder.tvMoneyDetailsNumber.setText("个");
            holder.tvMoneyDetailsNumber.setTextColor(Color.parseColor("#555555"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , MoneyDetailsActivity.class) ;
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_money_details_type)
        TextView tvMoneyDetailsType;
        @BindView(R.id.tv_money_details_time)
        TextView tvMoneyDetailsTime;
        @BindView(R.id.tv_money_details_number)
        TextView tvMoneyDetailsNumber;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
