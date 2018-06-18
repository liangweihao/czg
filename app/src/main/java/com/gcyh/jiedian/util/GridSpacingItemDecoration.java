package com.gcyh.jiedian.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/6/17.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {


    private int leftRight;
    private int topBottom;

    // 是否包含边缘
    public GridSpacingItemDecoration(int leftRight, int topBottom) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //这里是关键，需要根据你有几列来判断

        int position = parent.getChildAdapterPosition(view)%3; // item position

        if (position == 0){
            outRect.left = leftRight ;
            outRect.top = topBottom ;
            if (parent.getChildAdapterPosition(view) >= parent.getChildCount()-2){
                //最底部条目
                outRect.bottom = topBottom ;
            }else {
                outRect.bottom = 0 ;
            }

        }else if (position == 1){
            outRect.left = leftRight ;
            outRect.right = leftRight ;
            outRect.top = topBottom ;
            if (parent.getChildAdapterPosition(view) >= parent.getChildCount()-2){
                //最底部条目
                outRect.bottom = topBottom ;
            }else {
              outRect.bottom = 0 ;
            }

        }else if (position == 2){
            outRect.right = leftRight ;
            outRect.top = topBottom ;
            if (parent.getChildAdapterPosition(view) >= parent.getChildCount()-2){
                //最底部条目
                outRect.bottom = topBottom ;
            }else {
                outRect.bottom = 0 ;
            }
        }




//        int column = position % spanCount; // item column
//        if (includeEdge) {
//            outRect.left = spacing - column * spacing / spanCount;
//            outRect.right = (column + 1) * spacing / spanCount;
//            if (position < spanCount) {
//                outRect.top = spacing;
//            }
//            outRect.bottom = spacing;
//        } else {
//            outRect.left = column * spacing / spanCount;
//            outRect.right = spacing - (column + 1) * spacing / spanCount;
//            if (position >= spanCount) {
//                outRect.top = spacing;
//            }
//        }
    }


}
