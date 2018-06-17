package com.gcyh.jiedian.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.ChildBean;
import com.gcyh.jiedian.entity.ParentBean;
import com.gcyh.jiedian.entrance.ICartView;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.my.activity.ShopCarActivity;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public class ShopCarAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<ParentBean> parentList;
    private List<List<ChildBean>> childList;
    private ICartView iCartView;

    public ShopCarAdapter(ShopCarActivity shopCarActivity, List<ParentBean> parentList, List<List<ChildBean>> childList, ICartView iCartView) {
        this.context=shopCarActivity;
        this.parentList=parentList;
        this.childList=childList;
        this.iCartView=iCartView;
    }

    @Override
    public int getGroupCount() {
        return childList.size() ;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public ParentBean getGroup(int groupPosition) {
        return parentList.get(groupPosition);
    }

    @Override
    public ChildBean getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition ;
    }

    @Override
    public boolean hasStableIds() {
        return true ;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        List_1 list_1=null;
        //赋值
        final ParentBean parentBean=getGroup(groupPosition);
        if(view==null) {
            list_1=new List_1();
            view=View.inflate(context, R.layout.activity_shop_car_item1,null);
            list_1.shop_name= (TextView) view.findViewById(R.id.tv_shop_car_item1_content);
            list_1.checkBox= (CheckBox) view.findViewById(R.id.cb_shop_car_item1);
            //绑定数据
            view.setTag(list_1);
            list_1.checkBox.setTag(parentBean);
        }
        else {
            list_1= (List_1) view.getTag();
            list_1.checkBox.setTag(parentBean);
        }
        final List_1 list_11=list_1;
        list_1.checkBox.setChecked(parentBean.isCheck);
        list_1.shop_name.setText(parentBean.title);

        //CheckBox添加选中监听器实现全选
        list_1.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ParentBean parentBean1= (ParentBean) list_11.checkBox.getTag();
                parentBean1.isCheck=b;
                parentCheck(groupPosition);
                //判断是否全选中,方法中含有接口回调
                allCheckChangeFromParent();
                //刷新适配器
                notifyDataSetChanged();
            }
        });
        //CheckBox添加点击监听器实现全选
        list_1.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheck=((CheckBox)view).isChecked();
                if(!isCheck) {
                    shopCheck(false,childList.get(groupPosition));
                }
            }
        });
        return view;
    }

    class List_1 {
        CheckBox checkBox;
        TextView shop_name;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        List_2 list_2=null;
        final ChildBean childBean=getChild(groupPosition,childPosition);

        if(view==null) {
            list_2=new List_2();
            view=View.inflate(context,R.layout.activity_shop_car_item2,null);
            list_2.title= (TextView) view.findViewById(R.id.tv_shop_car_item_name);
            list_2.checkBox= (CheckBox) view.findViewById(R.id.cb_shop_car_item2);
            list_2.price= (TextView) view.findViewById(R.id.tv_shop_car_item_money);
            list_2.collect= (TextView) view.findViewById(R.id.tv_shop_car_collect);
            list_2.delete= (TextView) view.findViewById(R.id.tv_shop_car_delete);
            list_2.image= (ImageView) view.findViewById(R.id.iv_shop_car_item2_pic);
            view.setTag(list_2);
            list_2.checkBox.setTag(childBean);
        } else {
            list_2= (List_2) view.getTag();
            list_2.checkBox.setTag(childBean);
        }

        final List_2 list_21=list_2;

        list_2.checkBox.setChecked(childBean.isCheck);
        list_2.title.setText(childBean.title);
        list_2.price.setText(childBean.price+"线币");
        if (!TextUtils.isEmpty(childBean.pic)) {
            Glide.with(context).load(UrlAll.DOWN_LOAD_IMAGE + childBean.pic + ".png").diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(R.mipmap.jiedian_item).into(list_2.image);
        }
        if (childBean.collect == 1){
            //已收藏
            list_2.collect.setText("取消\n收藏");
        }else {
            list_2.collect.setText("移入\n收藏");
        }
        //收藏
        list_2.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childBean.collect == 1){
                    //移除收藏
                    iCartView.collect(childBean.type , childBean.id , 0);
                    childBean.collect = 0 ;
                }else {
                    //收藏
                    iCartView.collect(childBean.type , childBean.id , 1);
                    childBean.collect = 1 ;
                }

            }
        });
        //删除
        list_2.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCartView.delete(groupPosition , childPosition ,childBean.type , childBean.id );
            }
        });



        //CheckBox添加监听器
        list_2.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ChildBean childBean1= (ChildBean) list_21.checkBox.getTag();
                childBean1.isCheck=b;

                childCheck(groupPosition);
                //接口回调    调用计算总价的方法
                iCartView.addPrice();
                //刷新适配器
                notifyDataSetChanged();
            }
        });
        return view;

    }

    class List_2 {
        CheckBox checkBox;
        ImageView image;
        TextView title,price ,collect ,delete;
    }



    //全选
    public void allCheck(boolean isCheck) {
        //通过遍历所有的集合,修改bean类来控制CheckBox的选中状态
        for(int i=0;i<getGroupCount();i++)
        {
            ParentBean p=parentList.get(i);
            p.isCheck=isCheck;

            for(int a=0;a<getChildrenCount(i);a++)
            {
                ChildBean c=childList.get(i).get(a);
                c.isCheck=isCheck;
            }
        }
        //刷新适配器
        notifyDataSetChanged();
    }

    //判断二级列表全选中时,一级列表选中
    private void childCheck(int parentPosition) {
        //获取一级列表的bean 来修改isCheck属性
        ParentBean parentBean=getGroup(parentPosition);

        for(int i=0;i<getChildrenCount(parentPosition);i++)
        {
            //当前的Childbean
            ChildBean bean=getChild(parentPosition,i);
            if(!bean.isCheck)
            {
                parentBean.isCheck=false;
                return;
            }
            parentBean.isCheck=true;
        }
    }

    //一级列表的全选    一级列表不需要反选
    private void parentCheck(int parentPosition) {
        //获取一级列表的bean 来修改isCheck属性
        ParentBean parentBean=getGroup(parentPosition);

        if(parentBean.isCheck)
        {
            shopCheck(true,childList.get(parentPosition));
            return;
        }
    }

    //修改二级列表Check的属性
    private void shopCheck(boolean flag,List<ChildBean> childBeans) {
        for(ChildBean c:childBeans)
        {
            c.isCheck=flag;
        }
    }

    //当一级列表全选时,全选按钮选中
    private void allCheckChangeFromParent() {
        //遍历一级列表数据
        for (ParentBean p:parentList)
        {
            //有一个未被选中,则全选按钮不会被选中
            if(!p.isCheck)
            {
                iCartView.changeCheckBtn(false);
                return;
            }
        }
        //如果都被选中,则全选按钮选中
        iCartView.changeCheckBtn(true);
    }


}
