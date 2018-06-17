package com.gcyh.jiedian.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gcyh.jiedian.util.EventBusCode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import butterknife.ButterKnife;


/**
 * Fragment基类
 * @author 蔡志广
 */
public abstract class BaseFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initPresenter();
        this.initView();
        this.initValue();
    }

    /**
     * Every fragment has to inflate a layout in the onCreateView method. We have added this method to
     * avoid duplicate all the inflate code in every fragment.
     */
    protected abstract int getFragmentLayout();

    /**
     * 初始化presenter
     * @return
     */
    protected abstract void initPresenter();

    /**
     * 初始化view
     * @return
     */
    protected abstract void initView();

    /**
     * 初始化数据
     * @return
     */
    protected abstract void initValue();

    protected final View findViewById(int id){
        return getView().findViewById(id);
    }


    /**
     * 启动页面跳转
     *
     * @param cls
     */
    public void startIntent(Class<?> cls) {
        this.startIntent(getActivity(), cls);
    }

    /**
     * 启动页面跳转
     *
     * @param cls
     */
    public void startIntent(Class<?> cls, Bundle bundle) {
        this.startIntent(getActivity(), cls, bundle);
    }

    /**
     * 启动页面跳转
     *
     * @param context
     * @param cls
     */
    public void startIntent(Context context, Class<?> cls) {
        this.startIntent(context, cls, null);
    }

    /**
     * 启动页面跳转
     *
     * @param context
     * @param cls
     * @param bundle
     */
    public void startIntent(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);

        if (bundle != null){
            intent.putExtras(bundle);
        }

        context.startActivity(intent);
    }

    /**
     * 启动页面跳转 附带返回
     *
     * @param cls
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 启动页面跳转 附带返回
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    /**
     * eventBus可以重写，用来处理接收消息，
     * @param event
     */
    @Subscribe
    public void onEventMainThread(Object event) {
        if (event instanceof Bundle){
            int eventCode = ((Bundle) event).getInt(EventBusCode.EVENT_BUS_CODE);
            onEventMainThread(eventCode, (Bundle) event);
        }
    }

    /**
     * eventBus可以重写，用来处理接收消息，
     * @param eventCode
     */
    public void onEventMainThread(int eventCode, Bundle bundle) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
