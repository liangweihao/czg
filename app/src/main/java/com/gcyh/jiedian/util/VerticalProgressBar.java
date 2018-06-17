package com.gcyh.jiedian.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.gcyh.jiedian.R;

/**
 * Created by Caizhiguang on 2018/6/8.
 */

public class VerticalProgressBar extends View {


    private Paint paint;// 画笔
    private int progress;// 进度值
    private int width;// 宽度值
    private int height;// 高度值

    public VerticalProgressBar(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VerticalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalProgressBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth() - 1;// 宽度值
        height = getMeasuredHeight() - 1;// 高度值
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //画背景
        paint.setColor(getResources().getColor(R.color.white));// 设置背景画笔颜色
        canvas.drawRect(0, 0, width, height, paint);// 画背景矩形
        //画进度
        paint.setColor(getResources().getColor(R.color.color_d6d6d6));// 设置进度画笔颜色
        canvas.drawRect(0, progress / 100f * height, width, height, paint);
        super.onDraw(canvas);
    }


    /** 设置progressbar进度 */
    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }



}
