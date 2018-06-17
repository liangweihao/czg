package com.gcyh.jiedian.find.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    private String imageList = "" ;
    private String phone ;

    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_my_publish);
        ButterKnife.bind(this);
        tvToolbarTitle.setText("我要发布");
        txtToolbarRight.setVisibility(View.VISIBLE);
        txtToolbarRight.setText("发布");
        token_id = SPUtil.getString(this, "token_id", "");
        phone = SPUtil.getString(this, "phone", "");
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
            if (data != null) {
                List<String> pathList = Album.parseResult(data);
                piclist.addAll(pathList);
                adapter.notifyDataSetChanged();
                Log.i(TAG, "onActivityResult: ===="+pathList.get(0));
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
                String phone = this.phone.substring(7, this.phone.length());  //电话后四位
                if (piclist != null){
                    for (int i=0 ; i<piclist.size() ;i++){
                        if (i == piclist.size() - 1){
                            imageList += phone+time+(i+1) ;
                        }else {
                            imageList += phone+time+(i+1)+"," ;
                        }
                    }
                }

                //图片上传阿里云


                if (NetWorkUtils.isNetworkEnable(this)){
                    setPublicHttp(imageList , content) ;
                }
                Log.i(TAG, "onViewClicked: ===="+imageList + "====="+content);
                break;
        }
    }

    //发帖--接口
    private void setPublicHttp(String images , String content){
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.postmessage(token_id , images , content)
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

}
