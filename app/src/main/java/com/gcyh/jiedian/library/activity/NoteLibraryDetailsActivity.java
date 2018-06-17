package com.gcyh.jiedian.library.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.entity.AddCollect;
import com.gcyh.jiedian.entity.AddShopCar;
import com.gcyh.jiedian.entity.DeleteCollect;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;

import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NoteLibraryDetailsActivity extends AppCompatActivity {


    @BindView(R.id.iv_note_library_back)
    ImageView ivNoteLibraryBack;
    @BindView(R.id.tv_note_library_title)
    TextView tvNoteLibraryTitle;
    @BindView(R.id.iv_note_library_help)
    ImageView ivNoteLibraryHelp;
    @BindView(R.id.ll_unity)
    LinearLayout llUnity;
    @BindView(R.id.iv_note_library_zhunxing)
    ImageView ivNoteLibraryZhunxing;
    @BindView(R.id.iv_note_library_lock)
    ImageView ivNoteLibraryLock;
    @BindView(R.id.iv_library_note_saoma)
    ImageView ivLibraryNoteSaoma;
    @BindView(R.id.iv_library_note_add_shop_car)
    ImageView ivLibraryNoteAddShopCar;
    @BindView(R.id.iv_library_note_ar)
    ImageView ivLibraryNoteAr;
    @BindView(R.id.iv_library_note_buy)
    ImageView ivLibraryNoteBuy;
    @BindView(R.id.iv_note_library_include)
    ImageView ivNoteLibraryInclude;
    @BindView(R.id.mParentlayout)
    LinearLayout mParentlayout;


    private PopupWindow mPopupWindow;
    private Stack<View> mStack = new Stack<>();
    private LayoutInflater mLayountInflater;
    private RelativeLayout linearlayout;
    private String token_id;
    private int id;
    private int collect;
    private String title;
    private int shopCar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_note_library_details);
        ButterKnife.bind(this);
        token_id = SPUtil.getString(this, "token_id", "");
        id = getIntent().getIntExtra("id", 0);
        collect = getIntent().getIntExtra("collect", 0);
        title = getIntent().getStringExtra("title");
        shopCar = getIntent().getIntExtra("shopcar" , 0) ;

        int lock = SPUtil.getInt(this, "LIBRARY_LOCK", 1);
        if (lock == 1) {
            ivNoteLibraryLock.setImageResource(R.mipmap.library_note_lock_normal);
        } else if (lock == 2) {
            ivNoteLibraryLock.setImageResource(R.mipmap.library_note_lock_select);
        }
        tvNoteLibraryTitle.setText(title);
        //是否显示引导页面
        boolean guide = SPUtil.getBoolean(this, "GUIDE", true);

        if (guide) {
            initView();
            addView();
        }

    }

    @OnClick({R.id.iv_note_library_back, R.id.iv_note_library_help, R.id.iv_note_library_zhunxing, R.id.iv_note_library_lock, R.id.iv_library_note_saoma, R.id.iv_library_note_add_shop_car, R.id.iv_library_note_ar, R.id.iv_library_note_buy, R.id.iv_note_library_include})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_note_library_back:
                finish();
                break;
            case R.id.iv_note_library_help:
                //帮助
                Intent intent1 = new Intent(NoteLibraryDetailsActivity.this, HelpActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_note_library_zhunxing:
                //准星
                break;
            case R.id.iv_note_library_lock:
                //锁
                int lock = SPUtil.getInt(this, "LIBRARY_LOCK", 1);
                if (lock == 1) {
                    ivNoteLibraryLock.setImageResource(R.mipmap.library_note_lock_select);
                    SPUtil.setInt(this, "LIBRARY_LOCK", 2);
                } else if (lock == 2) {
                    ivNoteLibraryLock.setImageResource(R.mipmap.library_note_lock_normal);
                    SPUtil.setInt(this, "LIBRARY_LOCK", 1);
                }
                break;
            case R.id.iv_library_note_saoma:
                //扫码
                break;
            case R.id.iv_library_note_add_shop_car:
                if (shopCar == 1){
                    //已加入购物车
                    ToastUtil.show(this , "已加入购物车");
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    bundle.putInt("type", 2); //  2：节点   1：项目
                    EventBusUtil.postEvent(EventBusCode.ADD_SHOP_CAR, bundle);
                }
                break;
            case R.id.iv_library_note_ar:
                //AR
                break;
            case R.id.iv_library_note_buy:
                //直接购买
                Intent intent = new Intent(NoteLibraryDetailsActivity.this, PayMethodActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_note_library_include:
                ivNoteLibraryInclude.setImageResource(R.mipmap.library_note_include_select);
                testPopupWindowType1(ivNoteLibraryInclude);
                break;
        }
    }


    private void testPopupWindowType1(View view) {
        View contentView = getPopupWindowContentView();
        // 创建PopupWindow时候指定高宽时showAsDropDown能够自适应(能够根据剩余空间自动选中向上向下弹出)
        // 如果设置为wrap_content,showAsDropDown会认为下面空间一直很充足（我以认为这个Google的bug）
        // 备注如果PopupWindow里面有ListView,ScrollView时，一定要动态设置PopupWindow的大小
        mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight = contentView.getMeasuredHeight();
        int popupWidth = contentView.getMeasuredWidth();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight - 35);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivNoteLibraryInclude.setImageResource(R.mipmap.library_note_include_normal);
            }
        });

    }

    private View getPopupWindowContentView() {
        // 一个自定义的布局，作为显示的内容
        int layoutId = R.layout.popup_include;   // 布局ID
        final View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        final ImageView ivCollect = contentView.findViewById(R.id.iv_popup_include_collect);
        if (collect == 1) {
            //已收藏--显示红心
            ivCollect.setImageResource(R.mipmap.library_note_collect_select);
        } else {
            ivCollect.setImageResource(R.mipmap.library_note_collect_normal);
        }
        //评论
        contentView.findViewById(R.id.iv_popup_include_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteLibraryDetailsActivity.this, CommentListActivity.class);
                startActivity(intent);
            }
        });
        //分享
        contentView.findViewById(R.id.iv_popup_include_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        //收藏
        contentView.findViewById(R.id.iv_popup_include_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collect == 1) {
                    //取消收藏
                    ivCollect.setImageResource(R.mipmap.library_note_collect_normal);
                    collect = 0;
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    bundle.putInt("type", 2);
                    EventBusUtil.postEvent(EventBusCode.DELETE_COLLECT, bundle);
                } else {
                    //添加收藏
                    ivCollect.setImageResource(R.mipmap.library_note_collect_select);
                    collect = 1;
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    bundle.putInt("type", 2);
                    EventBusUtil.postEvent(EventBusCode.ADD_COLLECT, bundle);
                }
                mPopupWindow.dismiss();

            }
        });
        //施工工艺下载
        contentView.findViewById(R.id.iv_popup_include_download_gongyi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                showDialog2();
            }
        });

        return contentView;
    }


    //工艺下载
    public void showDialog2() {

        Dialog dialog = new Dialog(this, R.style.FullHeightDialog);

        //2.填充布局
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.my_note_library_gongyi_loaddown_dialog, null);
        //将自定义布局设置进去
        dialog.setContentView(dialogView);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        lp.height = (int) (d.getHeight() * 0.75); // 高度设置为屏幕的
        lp.width = (int) (d.getWidth() * 0.9); // 高度设置为屏幕的

        dialogWindow.setAttributes(lp);

        dialog.show();
        dialog.setCancelable(false);

        initDialogListener2(dialog);

    }

    private void initDialogListener2(final Dialog dialog) {
        ImageView delete = (ImageView) dialog.findViewById(R.id.iv_note_library_gongyi_loaddown_delete);
        TextView copy = (TextView) dialog.findViewById(R.id.btn_note_library_gongyi_loaddown);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void initView() {
        mLayountInflater = LayoutInflater.from(this);

    }

    private void removeView() {
        SPUtil.setBoolean(this, "GUIDE", false);
        if (mStack.size() > 0) {
            mParentlayout.removeView(mStack.pop());
        }
    }

    private int value = 1;

    private void addView() {
        linearlayout = (RelativeLayout) mLayountInflater.inflate(R.layout.add_view, null);
        mParentlayout.addView(linearlayout);
        mStack.push(linearlayout);
        final ImageView ivGuideBg = (ImageView) linearlayout.findViewById(R.id.iv_guide_bg);
        final ImageView ivGuideNext = (ImageView) linearlayout.findViewById(R.id.iv_guide_next);
        ivGuideBg.setImageResource(R.mipmap.guide_saoma);
        ivGuideNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value == 1) {
                    ivGuideBg.setImageResource(R.mipmap.guide_add_car);
                    value = 2;
                } else if (value == 2) {
                    ivGuideBg.setImageResource(R.mipmap.guide_ar);
                    value = 3;
                } else if (value == 3) {
                    ivGuideBg.setImageResource(R.mipmap.guide_bug);
                    value = 4;
                } else if (value == 4) {
                    ivGuideBg.setImageResource(R.mipmap.guide_include);
                    value = 5;
                } else if (value == 5) {
                    ivGuideBg.setImageResource(R.mipmap.guide_help);
                    value = 6;
                } else if (value == 6) {
                    ivGuideBg.setImageResource(R.mipmap.guide_zhuxing);
                    value = 7;
                } else if (value == 7) {
                    ivGuideBg.setImageResource(R.mipmap.guide_lock);
                    ivGuideNext.setImageResource(R.mipmap.guide_know);
                    value = 8;
                } else {
                    removeView();
                }
            }
        });

    }


    //下载
    public void showDialog1() {

        Dialog dialog = new Dialog(this, R.style.FullHeightDialog);

        //2.填充布局
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.my_note_library_loaddown_dialog, null);
        //将自定义布局设置进去
        dialog.setContentView(dialogView);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        lp.height = (int) (d.getHeight() * 0.83); // 高度设置为屏幕的
        lp.width = (int) (d.getWidth() * 0.9); // 高度设置为屏幕的

        dialogWindow.setAttributes(lp);

        dialog.show();
        dialog.setCancelable(false);


        initDialogListener1(dialog);

    }

    private void initDialogListener1(final Dialog dialog) {
        ImageView delete = (ImageView) dialog.findViewById(R.id.iv_note_library_loaddown_delete);
        Button copy = (Button) dialog.findViewById(R.id.btn_note_library_loaddown_copy);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
