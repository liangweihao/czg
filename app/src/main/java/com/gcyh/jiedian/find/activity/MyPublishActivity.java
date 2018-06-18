package com.gcyh.jiedian.find.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.gcyh.jiedian.adapter.PublicPhotoAdapter;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.entity.PostMessage;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.util.NetWorkUtils;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.SpaceItemDecoration;
import com.gcyh.jiedian.util.ToastUtil;
import com.yanzhenjie.album.Album;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyPublishActivity extends BaseActivity implements PublicPhotoAdapter.IPublicItemListener {


    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.txt_toolbar_right)
    TextView txtToolbarRight;
    @BindView(R.id.et_my_publish_content)
    EditText etMyPublishContent;
    @BindView(R.id.recycleView_my_publish)
    RecyclerView recycleViewMyPublish;

    private PublicPhotoAdapter adapter;
    private static final int ACTIVITY_REQUEST_SELECT_PHOTO = 1024;
    private List<String> piclist = new ArrayList<>();  //图片集合
    private String token_id;
    private String imageList = "";


    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_publish);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("我要发布");
        txtToolbarRight.setVisibility(View.VISIBLE);
        txtToolbarRight.setText("发布");
        token_id = SPUtil.getString(this, "token_id", "");

    }

    @Override
    protected void initView() {
        recycleViewMyPublish.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new PublicPhotoAdapter(piclist, this, this);
        adapter.setShopNum(4);
        recycleViewMyPublish.setAdapter(adapter);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onAdd() {
        picSelect();
    }

    @Override
    public void onDelete(View v, int position) {
        piclist.remove(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onShop(int position) {

    }

    private void picSelect() {
        // 1. 使用默认风格，并指定选择数量：
        // 第一个参数Activity/Fragment； 第二个request_code； 第三个允许选择照片的数量，不填可以无限选择。
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO, 9);
        // 2. 使用默认风格，不指定选择数量：
        // Album.startAlbum(this, ACTIVITY_REQUEST_SELECT_PHOTO); // 第三个参数不填的话，可以选择无数个。
        // 3. 指定风格，并指定选择数量，如果不想限制数量传入Integer.MAX_VALUE;
        Album.startAlbum(MyPublishActivity.this, ACTIVITY_REQUEST_SELECT_PHOTO
                , 4 - piclist.size()                                                         // 指定选择数量。
                , ContextCompat.getColor(this, R.color.colorAccent)        // 指定Toolbar的颜色。
                , ContextCompat.getColor(this, R.color.albumTransparentHalf));  // 指定状态栏的颜色。
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_REQUEST_SELECT_PHOTO) {
            // 拿到用户选择的图片路径List：
            piclist.clear();
            if (data != null) {
                List<String> pathList = Album.parseResult(data);
                piclist.addAll(pathList);
                adapter.notifyDataSetChanged();

            }
        }
    }

    @OnClick({R.id.btn_back, R.id.txt_toolbar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.txt_toolbar_right:
                //发布
                String content = etMyPublishContent.getText().toString(); //内容
                long time = System.currentTimeMillis();  // 时间戳
                String phone = SPUtil.getString(this, "phone", "");
                String phoneNumber = phone.substring(7, phone.length());  //电话后四位
                if (piclist != null) {
                    for (int i = 0; i < piclist.size(); i++) {
                        if (i == piclist.size() - 1) {
                            imageList += phoneNumber + time + (i + 1);
                        } else {
                            imageList += phoneNumber + time + (i + 1) + ",";
                        }
                    }
                }
                //把图片上传到阿里云服务器
                ossUpload(piclist);

                if (NetWorkUtils.isNetworkEnable(this)) {
                    setPublicHttp(imageList, content);
                }
                Log.i(TAG, "onViewClicked: ====" + imageList + "=====" + content);
                break;
        }
    }

    //发帖--接口
    private void setPublicHttp(String images, String content) {
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.postmessage(token_id, images, content)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<PostMessage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MyPublishActivity.this, "发布失败");
                    }

                    @Override
                    public void onNext(PostMessage model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MyPublishActivity.this, model.getRntMsg());
                            finish();
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MyPublishActivity.this, model.getRntMsg());
                        }

                    }
                });
    }

    //把图片上传到阿里云
    /**
     * 阿里云OSS上传（默认是异步多文件上传）
     *
     * @param urls
     */
    private int number = 1;

    public void ossUpload(final List<String> urls) {

        if (urls.size() <= 0) {
            // 文件全部上传完毕，这里编写上传结束的逻辑，如果要在主线程操作，最好用Handler或runOnUiThead做对应逻辑
            return;// 这个return必须有，否则下面报越界异常，原因自己思考下哈
        }
        final String url = urls.get(0);
        if (TextUtils.isEmpty(url)) {
            urls.remove(0);
            // url为空就没必要上传了，这里做的是跳过它继续上传的逻辑。
            ossUpload(urls);
            return;
        }

        File file = new File(url);
        if (null == file || !file.exists()) {
            urls.remove(0);
            // 文件为空或不存在就没必要上传了，这里做的是跳过它继续上传的逻辑。
            ossUpload(urls);
            return;
        }

        // 文件标识符objectKey
        final String objectKey = "image/" + System.currentTimeMillis() + number;
        // 下面3个参数依次为bucket名，ObjectKey名，上传文件路径
        PutObjectRequest put = new PutObjectRequest("gcyhnodelibrary", objectKey, url);

        // 设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                // 进度逻辑
            }
        });
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        OSSCustomSignerCredentialProvider credentialProvider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                String sign = OSSUtils.sign("LTAIVAo11aVfhBvf", "P0tWvWpQCcBzxH6b1TIvbvrx36cuKg", content);
                return sign;
            }
        };
        OSS oss = new OSSClient(this, endpoint, credentialProvider);
        // 异步上传
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) { // 上传成功
                urls.remove(0);
                ++number;
                if (urls.size() > 0) {
                    ossUpload(urls);// 递归同步效果
                } else {
                    //全部上传成功
                    Log.i("====", "onSuccess: ===上传成功==");

                }

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

}
