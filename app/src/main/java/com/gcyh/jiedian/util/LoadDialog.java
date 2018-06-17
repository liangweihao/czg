package com.gcyh.jiedian.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gcyh.jiedian.R;

/**
 * 正在加载框
 * @author 蔡志广
 *         Create at 18/5/26 17:22.
 */
public class LoadDialog {
    private static Dialog dialog;
    private static TextView tvMessage;

    public static void show(Context context ) {
        show(context,"");
    }

    public static void setCanceledOnTouchOutside(boolean cancel) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(cancel);
        }
    }

    public static void setCancelable(boolean flag) {
        if (dialog != null) {
            dialog.setCancelable(flag);
        }
    }

    public static void show(Context context, String message) {
        if (context == null) {
            return;
        }
        if (dialog == null || dialog.getContext() != context) {
            dialog = new Dialog(context, R.style.dialog_loading);
            dialog.setContentView(R.layout.dialog_loading);
            dialog.setCanceledOnTouchOutside(false);
            tvMessage = (TextView) dialog.findViewById(R.id.tv_message);
        }
        dialog.show();

        if (tvMessage == null) {
            tvMessage = (TextView) dialog.findViewById(R.id.tv_message);
        }
        if (tvMessage != null) {
            if (TextUtils.isEmpty(message)) {
                tvMessage.setText(context.getText(R.string.loading_text));
            } else {
                tvMessage.setVisibility(View.VISIBLE);
                tvMessage.setText(message);
            }
        }
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
