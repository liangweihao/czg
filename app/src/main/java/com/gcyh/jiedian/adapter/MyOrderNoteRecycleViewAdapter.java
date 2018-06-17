package com.gcyh.jiedian.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.util.ToastUtil;
import com.gcyh.jiedian.widget.RatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caizhiguang on 2018/5/3.
 */

public class MyOrderNoteRecycleViewAdapter extends RecyclerView.Adapter<MyOrderNoteRecycleViewAdapter.MyViewHolder>{

    private FragmentActivity context;
    private List<String> list;

    public MyOrderNoteRecycleViewAdapter(FragmentActivity activity, List<String> mDatas) {
        this.context = activity;
        this.list = mDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_my_order_note_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load("").placeholder(R.mipmap.jiedian_item).error(R.mipmap.jiedian_item).into(holder.ivMyOrderNotePic);
        if (position == 0 || position == 2 || position == 4 || position == 6 ){
            holder.tvMyOrderNoteComment.setVisibility(View.VISIBLE);
            holder.ratingBar.setVisibility(View.GONE);
        }
        //评论
        holder.ratingBar.setStar(5);
        //评论
        holder.tvMyOrderNoteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog() ;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_my_order_note_time)
        TextView tvMyOrderNoteTime;
        @BindView(R.id.tv_my_order_note_comment)
        TextView tvMyOrderNoteComment;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.iv_my_order_note_pic)
        ImageView ivMyOrderNotePic;
        @BindView(R.id.tv_my_order_note_name)
        TextView tvMyOrderNoteName;
        @BindView(R.id.tv_my_order_note_pay)
        TextView tvMyOrderNotePay;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void showDialog(){

        Dialog dialog = new Dialog(context ,R.style.FullHeightDialog);

        //2.填充布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.my_order_note_comment_dialog, null);
        //将自定义布局设置进去
        dialog.setContentView(dialogView);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        lp.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的
        lp.width = (int) (d.getWidth() * 0.9); // 高度设置为屏幕的

        dialogWindow.setAttributes(lp);

        dialog.show();
        dialog.setCancelable(false);

        initDialogListener(dialog);

    }


    private void initDialogListener(final Dialog dialog) {

        final RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        Button commit = dialog.findViewById(R.id.btn_order_note_comment_commit);
        ImageView delete = dialog.findViewById(R.id.iv_order_note_comment_delete);
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {

            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }


}
