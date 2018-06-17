package com.gcyh.jiedian.my.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseFragment;
import com.gcyh.jiedian.entity.My;
import com.gcyh.jiedian.entity.MySign;
import com.gcyh.jiedian.entrance.LoginActivity;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.my.activity.MyChargeActivity;
import com.gcyh.jiedian.my.activity.MyCollectActivity;
import com.gcyh.jiedian.my.activity.MyDownLoadActivity;
import com.gcyh.jiedian.my.activity.MyFansActivity;
import com.gcyh.jiedian.my.activity.MyInformationActivity;
import com.gcyh.jiedian.my.activity.MyInteractionActivity;
import com.gcyh.jiedian.my.activity.MyMoneyDetailsActivity;
import com.gcyh.jiedian.my.activity.MyNoticeActivity;
import com.gcyh.jiedian.my.activity.MyOrderActivity;
import com.gcyh.jiedian.my.activity.MyProjectManagerActivity;
import com.gcyh.jiedian.my.activity.MyRankActivity;
import com.gcyh.jiedian.my.activity.MyThemeActivity;
import com.gcyh.jiedian.my.activity.SettingActivity;
import com.gcyh.jiedian.my.activity.ShopCarActivity;
import com.gcyh.jiedian.util.DialogUtil;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;
import com.gcyh.jiedian.widget.CircleImageView;
import com.gcyh.jiedian.widget.RatingBar;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Caizhiguang on 2018/4/13.
 */

public class MyFragment extends BaseFragment {


    @BindView(R.id.iv_my_head)
    CircleImageView ivMyHead;
    @BindView(R.id.tv_loginorname)
    TextView tvLoginorname;
    @BindView(R.id.ll_my_information)
    LinearLayout llMyInformation;
    @BindView(R.id.iv_my_login_head)
    CircleImageView ivMyLoginHead;
    @BindView(R.id.tv_my_login_name)
    TextView tvMyLoginName;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.iv_my_login_shop_car)
    ImageView ivMyLoginShopCar;
    @BindView(R.id.iv_my_login_sign)
    ImageView ivMyLoginSign;
    @BindView(R.id.tv_my_login_collect)
    TextView tvMyLoginCollect;
    @BindView(R.id.ll_my_login_collect)
    LinearLayout llMyLoginCollect;
    @BindView(R.id.tv_my_login_notice)
    TextView tvMyLoginNotice;
    @BindView(R.id.ll_my_login_notice)
    LinearLayout llMyLoginNotice;
    @BindView(R.id.ll_my_order)
    LinearLayout llMyOrder;
    @BindView(R.id.tv_my_money)
    TextView tvMyMoney;
    @BindView(R.id.ll_my_money)
    LinearLayout llMyMoney;
    @BindView(R.id.ll_my_charge)
    LinearLayout llMyCharge;
    @BindView(R.id.ll_my_rank)
    LinearLayout llMyRank;
    @BindView(R.id.ll_my_interaction)
    LinearLayout llMyInteraction;
    @BindView(R.id.ll_my_theme)
    LinearLayout llMyTheme;
    @BindView(R.id.ll_my_setting)
    LinearLayout llMySetting;
    @BindView(R.id.ll_my_login_information)
    LinearLayout llMyLoginInformation;
    @BindView(R.id.ll_my_login_collect_notice_fans)
    LinearLayout llMyLoginCollectNoticeFans;
    Unbinder unbinder;
    @BindView(R.id.iv_my_A)
    ImageView ivMyA;
    @BindView(R.id.iv_my_B)
    ImageView ivMyB;
    @BindView(R.id.iv_my_S)
    ImageView ivMyS;
    @BindView(R.id.rl_my_login_name)
    LinearLayout rlMyLoginName;
    @BindView(R.id.tv_my_login_shop_car_number)
    TextView tvMyLoginShopCarNumber;
    @BindView(R.id.rl_my_login_shop_car)
    RelativeLayout rlMyLoginShopCar;
    @BindView(R.id.ll_my_login_fans)
    LinearLayout llMyLoginFans;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.ll_my_download)
    LinearLayout llMyDownload;
    @BindView(R.id.ll_my_project_manager)
    LinearLayout llMyProjectManager;
    @BindView(R.id.iv_my_grade)
    ImageView ivMyGrade;
    @BindView(R.id.tv_my_login_fans)
    TextView tvMyLoginFans;
    @BindView(R.id.tv_my_login_sign)
    TextView tvMyLoginSign;


    private String token_id;

    private Handler mHandler = new Handler() {


        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:// 签到
                    ivMyLoginSign.setImageResource(R.mipmap.my_sign_yes);//已签到
                    textView1.setVisibility(View.GONE);
                    break;

                default:
                    break;
            }
        }
    };
    private TextView textView1;
    private My.ResponseParamsBean.UserInfoBean userInfo;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onResume() {
        super.onResume();
        token_id = SPUtil.getString(getActivity(), "token_id", "");
        //是否登录来判断 布局 是否隐藏
        if (!TextUtils.isEmpty(token_id)) {
            //已登录
            llMyInformation.setVisibility(View.GONE);
            llMyLoginInformation.setVisibility(View.VISIBLE);
            llMyLoginCollectNoticeFans.setVisibility(View.VISIBLE);

        } else {
            //未登录
            llMyInformation.setVisibility(View.VISIBLE);
            llMyLoginInformation.setVisibility(View.GONE);
            llMyLoginCollectNoticeFans.setVisibility(View.GONE);
        }

        //访问网络 获取数据--网络判断
        if (NetWorkUtils.isNetworkEnable(getActivity())) {
            myHttp(token_id);
        }
        Log.i("===", "onResume: ===" + token_id);
    }

    @Override
    protected void initView() {

    }

    private void myHttp(String toke_id) {

        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.my(toke_id)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<My>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(getActivity(), "获取失败");
                    }

                    @Override
                    public void onNext(My model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(getActivity(), model.getRntMsg());

                            My.ResponseParamsBean responseParams = model.getResponseParams();

                            //设置参数
                            setDate(responseParams);

                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(getActivity(), model.getRntMsg());
                        }

                    }
                });


    }

    private void setDate(My.ResponseParamsBean responseParams) {
        //个人信息
        userInfo = responseParams.getUserInfo();
        //头像
        if (!TextUtils.isEmpty(userInfo.getPhoto())) {
            Glide.with(getActivity()).load(UrlAll.DOWN_LOAD_IMAGE+userInfo.getPhoto()+".png").asBitmap().placeholder(R.mipmap.my_head).error(R.mipmap.my_head).into(ivMyLoginHead);
        }
        //昵称
        tvMyLoginName.setText(userInfo.getNameNick());
        //  A  B  S 等级
        if (responseParams.getAVIP() == 1) {
            ivMyA.setVisibility(View.VISIBLE);
        }
        if (responseParams.getBVIP() == 1) {
            ivMyB.setVisibility(View.VISIBLE);
        }
        if (responseParams.getSVIP() == 1) {
            ivMyS.setVisibility(View.VISIBLE);
        }
        //等级 1----10
        int grade = responseParams.getGrade();
        setGrade(grade);
        //星星评价
        ratingBar.setStar(responseParams.getStar());
        // 购物车数量
        if (responseParams.getShoppingCart() <10){
            tvMyLoginShopCarNumber.setBackgroundResource(R.drawable.shape_my_shop_car_circle_bg);
            tvMyLoginShopCarNumber.setText(responseParams.getShoppingCart() + "");
        }else {
            tvMyLoginShopCarNumber.setBackgroundResource(R.drawable.shape_my_shop_car_square_bg);
            tvMyLoginShopCarNumber.setText(responseParams.getShoppingCart() + "");
        }
        // 是否签到
        responseParams.setIsSign(0);
        if (responseParams.getIsSign() == 1) {
            //以签到
            ivMyLoginSign.setImageResource(R.mipmap.my_sign_yes);
            ivMyLoginSign.setClickable(false);
        } else {
            //未签到
            ivMyLoginSign.setImageResource(R.mipmap.my_sign_no);
            ivMyLoginSign.setClickable(true);
        }
        //收藏数
        tvMyLoginCollect.setText(responseParams.getUserCollection() + "");
        //关注数
        tvMyLoginNotice.setText(responseParams.getConcerns() + "");
        //粉丝数
        tvMyLoginFans.setText(responseParams.getFans() + "");
        //线币数
        tvMyMoney.setText(userInfo.getBalance() + "线币");

    }

    private void setGrade(int grade) {
        switch (grade) {
            case 1:
                ivMyGrade.setImageResource(R.mipmap.lv1);
                break;
            case 2:
                ivMyGrade.setImageResource(R.mipmap.lv2);
                break;
            case 3:
                ivMyGrade.setImageResource(R.mipmap.lv3);
                break;
            case 4:
                ivMyGrade.setImageResource(R.mipmap.lv4);
                break;
            case 5:
                ivMyGrade.setImageResource(R.mipmap.lv5);
                break;
            case 6:
                ivMyGrade.setImageResource(R.mipmap.lv6);
                break;
            case 7:
                ivMyGrade.setImageResource(R.mipmap.lv7);
                break;
            case 8:
                ivMyGrade.setImageResource(R.mipmap.lv8);
                break;
            case 9:
                ivMyGrade.setImageResource(R.mipmap.lv9);
                break;
            case 10:
                ivMyGrade.setImageResource(R.mipmap.lv10);
                break;
        }
    }

    @Override
    protected void initValue() {

    }

    @OnClick({R.id.rl_my_login_shop_car, R.id.iv_my_login_sign, R.id.iv_my_head, R.id.ll_my_login_information, R.id.tv_loginorname, R.id.ll_my_login_collect, R.id.ll_my_login_notice, R.id.ll_my_login_fans, R.id.ll_my_order, R.id.ll_my_money, R.id.ll_my_charge, R.id.ll_my_rank, R.id.ll_my_interaction, R.id.ll_my_theme, R.id.ll_my_project_manager, R.id.ll_my_download, R.id.ll_my_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_my_head:
                //未登录的头像
                DialogUtil.dialogConfirmDialog2(getActivity(), new DialogUtil.YesOrNoDialogCallback() {
                    @Override
                    public void onClick(View v) {
                        startIntent(LoginActivity.class);
                    }
                } ,"是否登录");
                break;
            case R.id.tv_loginorname:
                //未登录的昵称（不可点击）或者登陆（可点击）
                startIntent(LoginActivity.class);
                break;
            case R.id.ll_my_login_information:
                //登录的头像
                Bundle bundle = new Bundle() ;
                bundle.putSerializable("infor" , (Serializable) userInfo);
                startIntent(MyInformationActivity.class , bundle);
                break;

            case R.id.rl_my_login_shop_car:
                //购物车
                startIntent(ShopCarActivity.class);
                break;
            case R.id.iv_my_login_sign:
                //签到
                int[] location = new int[2];
                ivMyLoginSign.getLocationInWindow(location);
                showView(location, ivMyLoginSign.getMeasuredWidth(), ivMyLoginSign.getMeasuredHeight());
                if (NetWorkUtils.isNetworkEnable(getActivity())) {
                    signHttp(token_id);
                }
                break;
            case R.id.ll_my_login_collect:
                //我的收藏
                startIntent(MyCollectActivity.class);
                break;
            case R.id.ll_my_login_notice:
                //我的关注
                startIntent(MyNoticeActivity.class);
                break;
            case R.id.ll_my_login_fans:
                //我的粉丝
                startIntent(MyFansActivity.class);
                break;
            case R.id.ll_my_order:
                //我的订单
                startIntent(MyOrderActivity.class);
                break;
            case R.id.ll_my_money:
                //我的线币
                startIntent(MyMoneyDetailsActivity.class);
                break;
            case R.id.ll_my_charge:
                //我要充值
                startIntent(MyChargeActivity.class);
                break;
            case R.id.ll_my_rank:
                //我的等级
                startIntent(MyRankActivity.class);
                break;
            case R.id.ll_my_interaction:
                // 我的互动
                startIntent(MyInteractionActivity.class);
                break;
            case R.id.ll_my_theme:
                // 我的主题
                startIntent(MyThemeActivity.class);
                break;
            case R.id.ll_my_project_manager:
                //我的项目管理
                startIntent(MyProjectManagerActivity.class);
                break;
            case R.id.ll_my_download:
                //下载管理
                startIntent(MyDownLoadActivity.class);
                break;
            case R.id.ll_my_setting:
                //设置
                startIntent(SettingActivity.class);
                break;
        }
    }

    private void signHttp(String toke_id) {

        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.mysign(toke_id)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<MySign>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(getActivity(), "获取失败");
                    }

                    @Override
                    public void onNext(MySign model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(getActivity(), model.getRntMsg());
                            ivMyLoginSign.setImageResource(R.mipmap.my_sign_yes);
                            ivMyLoginSign.setClickable(false);


                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(getActivity(), model.getRntMsg());
                        }

                    }
                });


    }

    private void showView(int[] locations, int width, int height) {

        FrameLayout decorView = (FrameLayout) getActivity().getWindow().getDecorView();
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) decorView.getLayoutParams();
        textView1 = new TextView(getActivity());
        textView1.setText("+3");
        textView1.setTextSize(11);
        textView1.setTextColor(getResources().getColor(R.color.colorAccent));
        decorView.addView(textView1);

        TranslateAnimation translate = new TranslateAnimation(0, 0, 0, -100);
        translate.setDuration(3000);
        //translate.setRepeatCount(1);
        // 渐变动画
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        alpha.setDuration(3000);
        alpha.setFillAfter(true);
        // 创建动画集合，将平移动画和渐变动画添加到集合中，一起start
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        textView1.startAnimation(set);

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) textView1.getLayoutParams();
        lp.leftMargin = locations[0] + width/2 ;
        lp.topMargin = locations[1] ;
        lp.width = width;
        lp.height = height ;
        textView1.requestLayout();
        mHandler.sendEmptyMessage(1) ;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {

    }




}
