package com.gcyh.jiedian.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.gcyh.jiedian.R;
import com.gcyh.jiedian.adapter.MyThemeRecyclerAdapter;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.MyInformation;
import com.gcyh.jiedian.entity.MyThemeList;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.LoadDialog;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;
import com.yanzhenjie.album.Album;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyThemeActivity extends BaseActivity {

    private static final int ACTIVITY_REQUEST_SELECT_PHOTO = 1024;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.recycleView_my_theme)
    RecyclerView recycleViewMyTheme;
    @BindView(R.id.ll_my_theme_null)
    LinearLayout llMyThemeNull;
    @BindView(R.id.iv_my_theme_null)
    ImageView ivMyThemeNull;
    @BindView(R.id.tv_my_theme_null)
    TextView tvMyThemeNull;

    private int pages = 1;
    private int row = 10;
    private List<MyThemeList.ResponseParamsBean.UserPostFabulousDTOtListBean> list = new ArrayList<>();
    private MyThemeRecyclerAdapter adapter;
    private MyThemeList.ResponseParamsBean.UserInfoDTOBean userInfo;
    private String token_id;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_theme);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("我的主题");

    }

    @Override
    protected void initView() {

        token_id = SPUtil.getString(this, "token_id", "");
        if (NetWorkUtils.isNetworkEnable(this)) {
            setMyThemeHttp(token_id, pages, row);
        } else {
            llMyThemeNull.setVisibility(View.VISIBLE);
            ivMyThemeNull.setImageResource(R.mipmap.internet_null);
            tvMyThemeNull.setText("网络不给力，点击重新加载");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(MyThemeActivity.this);
        //设置布局管理器
        recycleViewMyTheme.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);


    }

    private void setMyThemeHttp(String token_id, int pages, int row) {

        LoadDialog.show(this, "正在加载");
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.mythemelist(token_id, pages, row)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<MyThemeList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MyThemeActivity.this, "我的主题列表获取失败");
                        Log.i("====", "onError: " + e.toString());
                        LoadDialog.dismiss();
                    }

                    @Override
                    public void onNext(MyThemeList model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MyThemeActivity.this, model.getRntMsg());
                            list.clear();
                            MyThemeList.ResponseParamsBean responseParams = model.getResponseParams();
                            List<MyThemeList.ResponseParamsBean.UserPostFabulousDTOtListBean> userPostList = responseParams.getUserPostFabulousDTOtList();
                            userInfo = responseParams.getUserInfoDTO();
                            if (userPostList != null) {
                                if (userPostList.size() > 0 || userInfo != null) {
                                    list.addAll(userPostList);
                                    adapter = new MyThemeRecyclerAdapter(MyThemeActivity.this, list, userInfo);
                                    recycleViewMyTheme.setAdapter(adapter);
                                } else {
                                    llMyThemeNull.setVisibility(View.VISIBLE);
                                }
                            }

                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MyThemeActivity.this, model.getRntMsg());
                        }
                        LoadDialog.dismiss();

                    }
                });

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {
        //更换我的主题背景图片
        if (eventCode == EventBusCode.CHARGE_THEME_PIC) {
            picSelect();
        }
    }

    // 图片选择器
    private void picSelect() {
        // 1. 使用默认风格，并指定选择数量：
        // 第一个参数Activity/Fragment； 第二个request_code； 第三个允许选择照片的数量，不填可以无限选择。
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO, 9);
        // 2. 使用默认风格，不指定选择数量：
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO); // 第三个参数不填的话，可以选择无数个。
        // 3. 指定风格，并指定选择数量，如果不想限制数量传入Integer.MAX_VALUE;
        Album.startAlbum(MyThemeActivity.this, ACTIVITY_REQUEST_SELECT_PHOTO
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
                List<String> pathList = Album.parseResult(data);
                //把图片路径传到阿里云---上传成功后把图片名称传递到本地服务器
                long time = System.currentTimeMillis();  // 时间戳
                String phone = SPUtil.getString(this, "phone", "");
                String phoneNumber = phone.substring(7, phone.length());  //电话后四位
                String imageName ="image/" + phoneNumber + time + "0";
                ossUpload(pathList.get(0), imageName);

                // 访问网络获取图片
                userInfo.getUserInfo().setBackImg(pathList.get(0));
                // 刷新适配器
                adapter.notifyItemChanged(0);
            }
        }
    }

    private void ossUpload(final String url, final String name) {

        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        OSSCustomSignerCredentialProvider credentialProvider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                String sign = OSSUtils.sign("LTAIVAo11aVfhBvf", "P0tWvWpQCcBzxH6b1TIvbvrx36cuKg", content);
                return sign;
            }
        };
        OSS oss = new OSSClient(this, endpoint, credentialProvider);
        PutObjectRequest put = new PutObjectRequest("gcyhnodelibrary",  name, url);
        // 设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                // 进度逻辑
            }
        });
        // 异步上传
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) { // 上传成功
                Log.i("====", "onSuccess: =====" + url);
                //把背景图片名称传到本地服务器
                setImageHttp(name) ;
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion,
                                  ServiceException serviceException) { // 上传失败
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
        // task.cancel(); // 可以取消任务
        // task.waitUntilFinished(); // 可以等待直到任务完成
    }
    //把背景图片的名称传递到本地服务器

    private void setImageHttp(String imageName) {
        LoadDialog.show(this, "正在上传");
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.myinformation(token_id, "头像路径" , "昵称" ,"","" ,"" , "" , imageName)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<MyInformation>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MyThemeActivity.this, "上传失败");
                        Log.i("====", "onError: " + e.toString());
                        LoadDialog.dismiss();
                    }

                    @Override
                    public void onNext(MyInformation model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MyThemeActivity.this, model.getRntMsg());
                            finish();
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MyThemeActivity.this, model.getRntMsg());
                        }
                        LoadDialog.dismiss();

                    }
                });
    }


    @OnClick({R.id.btn_back, R.id.ll_my_theme_null})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_library_search_null:
                if (NetWorkUtils.isNetworkEnable(this)) {
                    setMyThemeHttp(token_id, pages, row);
                } else {
                    llMyThemeNull.setVisibility(View.VISIBLE);
                    ivMyThemeNull.setImageResource(R.mipmap.internet_null);
                    tvMyThemeNull.setText("网络不给力，点击重新加载");
                }
                break;
        }
    }
}
