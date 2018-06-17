package com.gcyh.jiedian.entity;

/**
 * Created by Administrator on 2018/6/11.
 */

public class ParentBean {

    public ParentBean(String title, boolean isCheck) {
        this.title = title;
        this.isCheck = isCheck;
    }

    public String title;        //一级列表的标题
    public boolean isCheck;     //一级列表的CheckBox

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

}
