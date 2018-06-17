package com.gcyh.jiedian.entity;

/**
 * Created by Administrator on 2018/6/10.
 */

public class CommentDetailsList {
    //评论列表
    public static final int FIRST_TYPE = 1;
    public static final int SECOND_TYPE = 2;
    //添加类型变量
     public int type;
     private String title;
     private String content;
     private String imgUrl;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
