package com.gcyh.jiedian.util;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/4/23.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int leftRight;
    private int topBottom;

    public SpaceItemDecoration(int leftRight, int topBottom) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view)%2;

        if (position == 0){
            //第一个条目
            outRect.left = leftRight ;
            outRect.top = topBottom ;
            outRect.right = leftRight ;
            if (parent.getChildAdapterPosition(view) >= parent.getChildCount()-1){
                outRect.bottom = topBottom ;
            }else {
                outRect.bottom = 0 ;
            }
        }else if (position == 1){
            //第二个条目
            outRect.left = leftRight ;
            outRect.top = topBottom ;
            outRect.right = leftRight ;
            if (parent.getChildAdapterPosition(view) >= parent.getChildCount()-1){
                outRect.bottom = topBottom ;
            }else {
                outRect.bottom = 0 ;
            }
        }

    }

}
