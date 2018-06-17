package com.gcyh.jiedian.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.My;
import com.gcyh.jiedian.entity.MyInformation;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.util.DialogUtil;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.LoadDialog;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.TakePhotoPopWin;
import com.gcyh.jiedian.util.ToastUtil;
import com.gcyh.jiedian.widget.CircleImageView;
import com.gcyh.jiedian.widget.GlideCircleTransform;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.yanzhenjie.album.Album;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyInformationActivity extends BaseActivity {


    private static final int ACTIVITY_REQUEST_SELECT_PHOTO = 2012;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.txt_toolbar_right)
    TextView txtToolbarRight;
    @BindView(R.id.iv_toolbar_right)
    ImageView ivToolbarRight;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.ll_information_name)
    LinearLayout llInformationName;
    @BindView(R.id.ll_my_information_profession)
    LinearLayout llMyInformationProfession;
    @BindView(R.id.ll_my_information_province)
    LinearLayout llMyInformationProvince;
    @BindView(R.id.ll_my_information_mail)
    LinearLayout llMyInformationMail;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_information_head)
    CircleImageView ivInformationHead;
    @BindView(R.id.tv_information_name)
    TextView tvInformationName;
    @BindView(R.id.tv_information_profession)
    TextView tvInformationProfession;
    @BindView(R.id.tv_information_province)
    TextView tvInformationProvince;
    @BindView(R.id.tv_information_phone)
    TextView tvInformationPhone;
    @BindView(R.id.tv_information_mail)
    TextView tvInformationMail;

    //省份
    CityPickerView mCityPickerView = new CityPickerView();
    CityConfig.WheelType mWheelType = CityConfig.WheelType.PRO_CITY;

    private TakePhotoPopWin takePhotoPopWin;

    int[] imageRes = {
            R.mipmap.material_man,
            R.mipmap.material_women,
            R.mipmap.teacher_man,
            R.mipmap.teacher_women,
            R.mipmap.stylist_man,
            R.mipmap.stylist_women,
            R.mipmap.worker_man,
            R.mipmap.worker_women,
            R.mipmap.student_man,
            R.mipmap.student_women,
    };
    private My.ResponseParamsBean.UserInfoBean infor;
    private String token_id;
    private List<String> pathList;
    private String provinceName;
    private String cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_information);
        ButterKnife.bind(this);
        token_id = SPUtil.getString(this, "token_id", "");
        infor = (My.ResponseParamsBean.UserInfoBean) getIntent().getSerializableExtra("infor");
        mCityPickerView.init(this);
        txtToolbarRight.setVisibility(View.VISIBLE);
        txtToolbarRight.setText("提交");
        setData(infor) ;
    }

    private void setData(My.ResponseParamsBean.UserInfoBean userInfoBean) {
        //头像
        if (!TextUtils.isEmpty(userInfoBean.getPhoto())) {
            Glide.with(this).load(UrlAll.DOWN_LOAD_IMAGE+userInfoBean.getPhoto()+".png").asBitmap().placeholder(R.mipmap.my_head).error(R.mipmap.my_head).into(ivInformationHead);
        }
        //昵称
        tvInformationName.setText(userInfoBean.getNameNick());
        //职业
        tvInformationProfession.setText(userInfoBean.getOccupation());
        //省份
        if (!TextUtils.isEmpty(userInfoBean.getProvince())&&!TextUtils.isEmpty(userInfoBean.getCity())){
            tvInformationProvince.setText(userInfoBean.getProvince()+"-"+userInfoBean.getCity());
            tvInformationProvince.setTextColor(getResources().getColor(R.color.color_85858b));
        }
        //邮箱
        if (!TextUtils.isEmpty(userInfoBean.getEmail())){
            tvInformationMail.setText(userInfoBean.getEmail());
            tvInformationMail.setTextColor(getResources().getColor(R.color.color_85858b));
        }
        //手机号
        tvInformationPhone.setText(userInfoBean.getPhone());


    }

    @Override
    protected void initView() {
        tvToolbarTitle.setText("个人资料");
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                //拍照 相册
                case R.id.tv_person_information_take_photo_from_photo_album:
                    takePhotoPopWin.dismiss();
                    picSelect();
                    break;
                //默认图片
                case R.id.tv_person_information_take_photo_from_default:
                    takePhotoPopWin.dismiss();
                    startIntent(DefaultHeadActivity.class);
                    break;
            }
        }
    };


    @OnClick({R.id.txt_toolbar_right,R.id.btn_back, R.id.iv_information_head, R.id.ll_information_name, R.id.ll_my_information_province, R.id.ll_my_information_mail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_toolbar_right:
               //提交
                //把头像上传到阿里云---头像上传阿里云成功在上传本地数据到服务器

                //头像路径
                long time = System.currentTimeMillis();  // 时间戳
                String phoneNumber = infor.getPhone();
                String phone = phoneNumber.substring(7, phoneNumber.length());  //电话后四位
                String imagePic = phone + time +"0" ;
                // 昵称
                String name = tvInformationName.getText().toString();

                // 邮箱
                String email = tvInformationMail.getText().toString();
                if (TextUtils.isEmpty("imagePic")){
                    ToastUtil.show(this , "头像不能为空");
                }else if (TextUtils.isEmpty(name)){
                    ToastUtil.show(this , "昵称不能为空");
                }else if (TextUtils.isEmpty("省")&&TextUtils.isEmpty("市")){
                    ToastUtil.show(this , "省市不能为空");
                }else {
                    setSubmitHttp(token_id ,imagePic , name , provinceName , cityName ,email) ;
                }
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_information_head:
                //头像
                takePhotoPopWin = new TakePhotoPopWin(this, onClickListener);
                takePhotoPopWin.showAtLocation(findViewById(R.id.main_view), Gravity.CENTER, 0, 0);

                break;
            case R.id.ll_information_name:
                //昵称
                Bundle bundle = new Bundle();
                bundle.putString("name" , infor.getNameNick());
                startIntent(MyNameActivity.class , bundle);
                break;
            case R.id.ll_my_information_province:
                //省份
                wheel();
                break;
            case R.id.ll_my_information_mail:
                //邮箱
                Bundle bundle1 = new Bundle();
                bundle1.putString("email" , infor.getEmail());
                startIntent(MyEmailActivity.class ,bundle1);
                break;
        }
    }

    private void setSubmitHttp(String token_id, String imagePic, String name, String province, String city, String email) {

        LoadDialog.show(this, "正在上传");
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.myinformation(token_id, imagePic , name ,province,city ,email , "" , "")
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<MyInformation>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MyInformationActivity.this, "上传失败");
                        Log.i("====", "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(MyInformation model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MyInformationActivity.this, model.getRntMsg());
                             finish();
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MyInformationActivity.this, model.getRntMsg());
                        }
                        LoadDialog.dismiss();

                    }
                });

    }

    // 图片选择器
    private void picSelect() {
        // 1. 使用默认风格，并指定选择数量：
        // 第一个参数Activity/Fragment； 第二个request_code； 第三个允许选择照片的数量，不填可以无限选择。
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO, 9);
        // 2. 使用默认风格，不指定选择数量：
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO); // 第三个参数不填的话，可以选择无数个。
        // 3. 指定风格，并指定选择数量，如果不想限制数量传入Integer.MAX_VALUE;
        Album.startAlbum(MyInformationActivity.this, ACTIVITY_REQUEST_SELECT_PHOTO
                , 1                                                         // 指定选择数量。
                , ContextCompat.getColor(this, R.color.colorAccent)        // 指定Toolbar的颜色。
                , ContextCompat.getColor(this, R.color.albumTransparentHalf));  // 指定状态栏的颜色。
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_REQUEST_SELECT_PHOTO) {
            // 拿到用户选择的图片路径List：
            if (data != null) {
                pathList = Album.parseResult(data);
                Glide.with(MyInformationActivity.this).load(pathList.get(0)).asBitmap().into(ivInformationHead);

            }
        }
    }


    /**
     * 省份选择器
     */
    private void wheel() {

        CityConfig cityConfig = new CityConfig.Builder().title("选择城市").setCityWheelType(mWheelType).build();
        cityConfig.setDefaultProvinceName("北京市");
        cityConfig.setDefaultCityName("北京市");

        mCityPickerView.setConfig(cityConfig);
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
                if (province != null) {
                    provinceName = province.getName();
                    sb.append(provinceName + "-");
                }
                if (city != null) {
                    cityName = city.getName();
                    sb.append(cityName);
                }
                tvInformationProvince.setText("" + sb.toString());
                tvInformationProvince.setTextColor(Color.parseColor("#85858b"));
            }

            @Override
            public void onCancel() {
                ToastUtil.show(MyInformationActivity.this, "您未选择地址");
            }
        });
        mCityPickerView.showCityPicker();

    }

    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {
        //默认头像
        if (eventCode == EventBusCode.DEFAULT_HEAD){
            int position = bundle.getInt("DEFAULT_HEAD");
            ivInformationHead.setImageResource(imageRes[position]);
        }else if (eventCode == EventBusCode.INFORMATION_NAME){
            //昵称
            String name = bundle.getString("information_name");
            tvInformationName.setText(name);
        }else if (eventCode == EventBusCode.INFORMATION_EMAIL){
            //邮箱
            String email = bundle.getString("information_email");
            tvInformationMail.setText(email);
            tvInformationMail.setTextColor(getResources().getColor(R.color.color_85858b));
        }
    }
}
