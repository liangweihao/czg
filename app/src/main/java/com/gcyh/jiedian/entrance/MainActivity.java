package com.gcyh.jiedian.entrance;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gcyh.jiedian.R;
import com.gcyh.jiedian.base.BaseActivity;
import com.gcyh.jiedian.educationVR.fragment.EducationFragment;
import com.gcyh.jiedian.entity.AddCollect;
import com.gcyh.jiedian.entity.AddShopCar;
import com.gcyh.jiedian.entity.DeleteCollect;
import com.gcyh.jiedian.entity.DeleteNotice;
import com.gcyh.jiedian.entity.DeleteZan;
import com.gcyh.jiedian.entity.DeltetShopCar;
import com.gcyh.jiedian.entity.Notice;
import com.gcyh.jiedian.entity.Zan;
import com.gcyh.jiedian.find.fragment.FindFragment;
import com.gcyh.jiedian.http.ApiService;
import com.gcyh.jiedian.http.RetrofitUtil;
import com.gcyh.jiedian.http.UrlAll;
import com.gcyh.jiedian.library.fragment.LibraryFragment;
import com.gcyh.jiedian.my.fragment.MyFragment;
import com.gcyh.jiedian.productionAR.fragment.ProductionFragment;
import com.gcyh.jiedian.util.EventBusCode;
import com.gcyh.jiedian.util.EventBusUtil;
import com.gcyh.jiedian.util.PermissionCheckUtil;
import com.gcyh.jiedian.util.SPUtil;
import com.gcyh.jiedian.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.attr.name;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.main_production)
    RadioButton mainProduction;
    @BindView(R.id.main_education)
    RadioButton mainEducation;
    @BindView(R.id.main_library)
    RadioButton mainLibrary;
    @BindView(R.id.main_find)
    RadioButton mainFind;
    @BindView(R.id.main_my)
    RadioButton mainMy;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.library_img)
    ImageView libraryImg;
    @BindView(R.id.rl_library_img)
    RelativeLayout rlLibraryImg;


    /**
     * 施工图+VR
     */
    public static final int TAB_PRODUCTION = 0;
    /**
     * VR教育
     */
    public static final int TAB_EDUCATION = 1;
    /**
     * 库
     */
    public static final int TAB_LIBRARY = 2;
    /**
     * 发现
     */
    public static final int TAB_FIND = 3;
    /**
     * 我的
     */
    public static final int TAB_MY = 4;

    private Fragment mFragment = null;

    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private String token_id;


    @Override
    protected void onCreate(Bundle savedInstanceState, String tag) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        token_id = SPUtil.getString(this, "token_id", "");
        radioGroup.setOnCheckedChangeListener(this);
        mainLibrary.setChecked(true);

        Intent intent = new Intent(this, DownloadService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        libraryImg.setImageResource(R.mipmap.library_normal);
        switch (checkedId) {
            case R.id.main_production:
                replaceView(TAB_PRODUCTION);
                break;
            case R.id.main_education:
                replaceView(TAB_EDUCATION);
                break;
            case R.id.main_library:
                replaceView(TAB_LIBRARY);
                libraryImg.setImageResource(R.mipmap.library_select);
                break;
            case R.id.main_find:
                replaceView(TAB_FIND);
                break;
            case R.id.main_my:
                replaceView(TAB_MY);
                break;
            default:
                break;
        }

    }

    /**
     * 切换视图
     *
     * @param currItem
     */
    private synchronized void replaceView(int currItem) {

        switch (currItem) {
            case TAB_PRODUCTION:
                showFragment(ProductionFragment.class.getName());
                if (!mainProduction.isChecked()) {
                    mainProduction.setChecked(true);
                }
                break;
            case TAB_EDUCATION:
                showFragment(EducationFragment.class.getName());
                if (!mainEducation.isChecked()) {
                    mainEducation.setChecked(true);
                }
                break;
            case TAB_LIBRARY:
                showFragment(LibraryFragment.class.getName());
                break;
            case TAB_FIND:
                showFragment(FindFragment.class.getName());
                if (!mainFind.isChecked()) {
                    mainFind.setChecked(true);
                }
                break;
            case TAB_MY:
                showFragment(MyFragment.class.getName());
                if (!mainMy.isChecked()) {
                    mainMy.setChecked(true);
                }
                break;
            default:
                break;
        }

    }

    //切换视图
    private void showFragment(String frgName) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(frgName);
        if (mFragment != null && fragment == mFragment) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment == null) {
            fragment = Fragment.instantiate(this, frgName);
            ft.add(R.id.frameLayout, fragment, frgName);
            ft.addToBackStack(null);
        }
        if (mFragment != null) {
            ft.hide(mFragment);
        }
        mFragment = fragment;
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    private boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_SHORT).show();
                isExit = true;
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onFinish() {
                        isExit = false;
                    }

                    @Override
                    public void onTick(long arg0) {
                    }

                }.start();
                return true;
            } else {
                //退出程序
                finish();
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    //库点击事件
    @OnClick(R.id.rl_library_img)
    public void onViewClicked() {

        mainLibrary.setChecked(true);

    }

    @Override
    public void onEventMainThread(int eventCode, Bundle bundle) {

        if (eventCode == EventBusCode.LIBRARY_DOWNLOAD_MODEL) {
            //开启下载AR模型
            String url = bundle.getString("url");
            String name = url.substring(6, url.length());
            int position = bundle.getInt("position");

            downloadBinder.startDownload(Environment.getExternalStorageDirectory() + "/Library/",
                    name +".dm",
                    UrlAll.DOWN_LOAD_IMAGE+url+".dm?="+ SystemClock.currentThreadTimeMillis(),
                    url,position);

        }else if (eventCode == EventBusCode.ADD_COLLECT){
            //添加收藏
            int type = bundle.getInt("type");
            int id = bundle.getInt("id");
            setAddCollect(type ,id) ;
        }else if (eventCode == EventBusCode.DELETE_COLLECT){
            //取消收藏
            int type = bundle.getInt("type");
            int id = bundle.getInt("id");
            setDeleteCollect(type ,id) ;
        }else if (eventCode == EventBusCode.ADD_SHOP_CAR){
            //加入购物车
            int id = bundle.getInt("id");
            int type = bundle.getInt("type");
            setAddShopCarHttp(id , type) ;
        }else if (eventCode == EventBusCode.DELETE_SHOP_CAR){
            //移除购物车
            int id = bundle.getInt("id");
            int type = bundle.getInt("type");
            setDeleteShopCarHttp(id , type) ;
        }else if (eventCode == EventBusCode.NOTICE){
            //关注
            String code = bundle.getString("code");
            setNoticeHttp(code) ;
        }else if (eventCode == EventBusCode.DELETE_NOTICE){
            //取消关注
            String code = bundle.getString("code");
            setDeleteNoticeHttp(code) ;
        }else if (eventCode == EventBusCode.ZAN){
            //点赞
            String code = bundle.getString("postid");
            setZanHttp(code) ;
        }else if (eventCode == EventBusCode.DELETE_ZAN){
            //取消点赞
            String code = bundle.getString("postid");
            setDeleteZanHttp(code) ;
        }
    }



    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }

    //添加收藏
    private void setAddCollect(final int type, int id) {
        Log.i("====", "setAddCollect: "+token_id+"==="+type+"==="+id);
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.addcollect(token_id , type , id)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<AddCollect>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MainActivity.this, "失败");
                    }

                    @Override
                    public void onNext(AddCollect model) {
                        if (model.getRntCode().equals("OK")) {

                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                            if (type == 1){
                                //通知 --- 项目库刷新数据
                                EventBusUtil.postEvent(EventBusCode.LIBRARY_PROJECT_UPDATE);
                            }else if (type == 2){
                                //通知 --- 节点库刷新数据
                                EventBusUtil.postEvent(EventBusCode.LIBRARY_NOTE_UPDATE);
                            }else if (type == 3){
                                //通知 --- 常用资料库刷新数据
                                EventBusUtil.postEvent(EventBusCode.LIBRARY_DATA_UPDATE);
                            }


                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        }


                    }
                });

    }

    // 移除收藏
    private void setDeleteCollect(final int type, int id) {

        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.deletecollect(token_id , type , id)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<DeleteCollect>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MainActivity.this, "失败");
                    }

                    @Override
                    public void onNext(DeleteCollect model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                            if (type == 1){
                                //通知 --- 项目库刷新数据
                                EventBusUtil.postEvent(EventBusCode.LIBRARY_PROJECT_UPDATE);
                            }else if (type == 2){
                                //通知 --- 节点库刷新数据
                                EventBusUtil.postEvent(EventBusCode.LIBRARY_NOTE_UPDATE);
                            }else if (type == 3){
                                //通知 --- 常用资料库刷新数据
                                EventBusUtil.postEvent(EventBusCode.LIBRARY_DATA_UPDATE);
                            }
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        }
                    }
                });
    }

    //加入购物车
    private void setAddShopCarHttp(int id, int type) {
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.addshopcar(token_id , id , type)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<AddShopCar>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MainActivity.this, "添加购物车失败");
                        Log.i("====", "onError: "+e.toString());
                    }

                    @Override
                    public void onNext(AddShopCar model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        }

                    }
                });
    }

   //移除购物车
    private void setDeleteShopCarHttp(int id, int type) {
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.deleteshopcar(token_id , id , type)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<DeltetShopCar>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MainActivity.this, "添加购物车失败");
                        Log.i("====", "onError: "+e.toString());
                    }

                    @Override
                    public void onNext(DeltetShopCar model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        }

                    }
                });
    }

    //关注
    private void setNoticeHttp(String code) {
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.notice(token_id , code)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Notice>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MainActivity.this, "关注失败");
                        Log.i("====", "onError: "+e.toString());
                    }

                    @Override
                    public void onNext(Notice model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        }

                    }
                });
    }

    //关取消注
    private void setDeleteNoticeHttp(String code) {
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.deletenotice(token_id , code)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<DeleteNotice>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MainActivity.this, "关注失败");
                        Log.i("====", "onError: "+e.toString());
                    }

                    @Override
                    public void onNext(DeleteNotice model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        }

                    }
                });
    }

    //点赞
    private void setZanHttp(String postId){
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.zan(token_id , postId)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Zan>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MainActivity.this, "点赞失败");
                        Log.i("====", "onError: "+e.toString());
                    }

                    @Override
                    public void onNext(Zan model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        }

                    }
                });

    }

    //取消赞
    private void setDeleteZanHttp(String postId){
        ApiService service = RetrofitUtil.getInstance().create(ApiService.class);
        service.deletezan(token_id , postId)
                .subscribeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<DeleteZan>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(MainActivity.this, "点赞失败");
                        Log.i("====", "onError: "+e.toString());
                    }

                    @Override
                    public void onNext(DeleteZan model) {
                        if (model.getRntCode().equals("OK")) {
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        } else if (model.getRntCode().equals("ERROR")) {
                            //获取数据失败
                            ToastUtil.show(MainActivity.this, model.getRntMsg());
                        }

                    }
                });

    }


}
