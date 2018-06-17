package com.gcyh.jiedian.entrance;

/**
 * Created by Administrator on 2018/6/11.
 */

public interface ICartView {

    //修改购物车中全选按钮的状态
    void changeCheckBtn(boolean flag);

    //计算总价的方法
    void addPrice();
    //删除条目的方法
    void delete(int groupPosition , int childPosition , int type , int id);
    //收藏 ---  type : 收藏类型 ：1 ：项目  2 ：节点     collect ：是否收藏
    void collect( int type , int id , int collect) ;

}
