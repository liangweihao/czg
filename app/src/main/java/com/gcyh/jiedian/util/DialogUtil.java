package com.gcyh.jiedian.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.gcyh.jiedian.R;

import java.util.HashMap;

/**
 * Created by 蔡志广 on 2018/4/27
 */
public class DialogUtil {

    private static HashMap<String,Dialog> dialogMap = new HashMap<>();
    private YesOrNoDialogCallback callback;

    /**
     * 显示进度条对话框
     * @param activity
     * @param dialogTag
     * @param msg
     */
    public static void showProgressDialog(Activity activity, String dialogTag, String msg){
       filterDialog(dialogTag);

        ProgressDialog progressDialog =  new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        dialogMap.put(dialogTag,progressDialog);
        progressDialog.show();
    }

    private static void filterDialog(String dialogTag){
        Dialog dialog = dialogMap.get(dialogTag);
        if(dialog!=null){
            dismissDialog(dialogTag);
        }
    }

    /**
     * 关闭对话框
     * @param dialogTag
     */
    public static void dismissDialog(String dialogTag){
        Dialog dialog = dialogMap.get(dialogTag);
        if(dialog!=null){
            dialog.dismiss();
            removeDialog(dialogTag);
        }
    }

    public static void removeDialog(String dialogTag){
        dialogMap.remove(dialogTag);
    }


    /**
     * 显示确认对话框
     * @param activity
     * @param dialogTag
     * @param title
     * @param msg
     */
    public static void showConfirmDialog(Activity activity, String dialogTag, String title, String msg, DialogInterface.OnClickListener listener){
        filterDialog(dialogTag);

        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("确定",listener)
                .setNegativeButton("取消",null)
                .create();
        alertDialog.show();
        dialogMap.put(dialogTag,alertDialog);
    }

    //定义回调的接口
    public interface YesOrNoDialogCallback {
        void onClick(View v);
    }

    public static void dialogConfirmDialog2(Activity mContext, final YesOrNoDialogCallback listener , String content) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.ye_no_login_dialog, null);
        TextView sure = (TextView) v.findViewById(R.id.tv_yes);
        TextView cancel = (TextView) v.findViewById(R.id.tv_no);
        TextView title = (TextView) v.findViewById(R.id.tv_title);
        title.setText(content);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置

        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onClick(v);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 显示列表选择的对话框
     * @param activity
     * @param dialogTag
     * @param items
     * @param listener
     */
    public static void showListDialog(Activity activity, String dialogTag, String[] items, DialogInterface.OnClickListener listener) {
        filterDialog(dialogTag);
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle("请选择：")
                .setItems(items,listener)
                .create();
        alertDialog.show();
        dialogMap.put(dialogTag,alertDialog);
    }

    /**
     * 显示输入框的对话框
     * @param activity
     */
    public static void showEditDialog(Activity activity, String dialogTag, String title, final OnEditDialogConfirmListener
                                      listener) {
        filterDialog(dialogTag);
        final EditText editText = new EditText(activity);
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(listener!=null && !TextUtils.isEmpty(editText.getText().toString())){
                            listener.onEditDialogConfirm(editText.getText().toString());
                        }
                    }
                })
                .create();
        alertDialog.show();
        dialogMap.put(dialogTag,alertDialog);
    }

    /**
     * 创建单选对话框
     */
    public static void showSingleChoiceDialog(Activity activity, String dialogTag, String title
        , String[] items, int selectedItem,DialogInterface.OnClickListener listener) {
        filterDialog(dialogTag);
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setSingleChoiceItems(items,selectedItem,listener)
                .create();
        alertDialog.show();
        dialogMap.put(dialogTag,alertDialog);
    }

    /**
     * 创建选择日期的对话框
     */
    public static void showDatePickerDialog(Activity activity, String dialogTag, String title
        , final OnDatePickerDialogConfirmListener listener) {
        filterDialog(dialogTag);
        final DatePicker datePicker = new DatePicker(activity);
        datePicker.setCalendarViewShown(false);
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setView(datePicker)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       if(listener!=null){
                           listener.onDatePickerComfirm(datePicker.getYear()
                           ,datePicker.getMonth(),datePicker.getDayOfMonth());
                       }
                    }
                })
                .create();
        alertDialog.show();
        dialogMap.put(dialogTag,alertDialog);
    }


    public interface OnDatePickerDialogConfirmListener{
        void onDatePickerComfirm(int year, int month, int day);
    }


    public interface OnEditDialogConfirmListener{
        void onEditDialogConfirm(String content);
    }
}
