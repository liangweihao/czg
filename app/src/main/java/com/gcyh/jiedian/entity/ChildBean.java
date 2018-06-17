package com.gcyh.jiedian.entity;

/**
 * Created by Administrator on 2018/6/11.
 */

public class ChildBean {

    public ChildBean(String title, int type, int id, boolean isCheck, String pic, int price,int collect) {
        this.title = title;
        this.type = type;
        this.id = id;
        this.isCheck = isCheck;
        this.pic = pic;
        this.price = price;
        this.collect=collect ;
    }

    public String title;      //二级列表的标题
    public int type;      //二级列表的类型  1 : 项目库   2：节点库
    public int id;      //二级列表的id
    public boolean isCheck;     //二级列表的CheckBox
    public String pic;         //二级列表的图片
    public int price;           //二级列表的价格
    public int collect ;  //是否收藏

}
