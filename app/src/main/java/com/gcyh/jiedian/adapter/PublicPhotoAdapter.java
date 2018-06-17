package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;

import java.util.List;

/**
 * Created by Caizhiguang on 2018/4/25
 * 选择图片的适配器
 */

public class PublicPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> list;
    //    发布商品的数量
    private int shopNum = 1 ;
    private Context context;
    IPublicItemListener iPublicItemListener;
    private static final int TYPE_ADD = 1;
    private static final int TYPE_MEDIA = 2;

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public interface IPublicItemListener {
        void onAdd();

        void onDelete(View v, int position);

        void onShop(int position);
    }

    public PublicPhotoAdapter(List<String> list, Context context, IPublicItemListener listener) {
        this.list = list;
        this.context = context;
        iPublicItemListener = listener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //图片
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_photo_item, null);
            return new MyHolderImage(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_MEDIA) {
               //    显示图片
                Glide.with(context)
                        .load(list.get(position))
                        .asBitmap()
                        .override(100, 100)
                        .centerCrop()
                        .into(((MyHolderImage) holder).ivImg);

            try {
                ((MyHolderImage) holder).ivImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iPublicItemListener.onShop(position);

                    }

                });
                ((MyHolderImage) holder).delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iPublicItemListener.onDelete(view, position);
                    }
                });
            } catch (Exception e) {

            }

        } else if (getItemViewType(position) == TYPE_ADD) {
            //   显示加号

            Glide.with(context).load(R.mipmap.camer).override(100, 100).into(((MyHolderImage) holder).ivImg);
            ((MyHolderImage) holder).delete.setVisibility(View.INVISIBLE);
            try {
                ((MyHolderImage) holder).ivImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iPublicItemListener.onAdd();

                    }
                });
            } catch (Exception e) {

            }
        }

    }

    @Override
    public int getItemCount() {

        if (list == null || list.isEmpty()) {
//            一个图片都没有显示一个加号
            return 1;
        }
        if (list.size() < shopNum) {
            return list.size() + 1;
        }
        return list.size();


    }

    @Override
    public int getItemViewType(int position) {

        if (position == list.size() || list.size() == 0) {
            return TYPE_ADD;
        } else {
            return TYPE_MEDIA;
        }

    }

    public static class MyHolderImage extends RecyclerView.ViewHolder {

        ImageView ivImg;
        ImageView delete;

        public MyHolderImage(View view) {
            super(view);
            ivImg = (ImageView) view.findViewById(R.id.iv_img);
            delete = (ImageView) view.findViewById(R.id.delete);
        }
    }


}
