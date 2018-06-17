package com.gcyh.jiedian.app;

import android.support.multidex.MultiDexApplication;

/**
 * Created by caizhiguang on 2018/4/16.
 */

public class App extends MultiDexApplication {


    private static App mInstance;

    public static App getInstance() {
        synchronized (App.class) {
            if (mInstance == null) {
                mInstance = new App();
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}
