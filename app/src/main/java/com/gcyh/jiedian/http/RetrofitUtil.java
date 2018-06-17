package com.gcyh.jiedian.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求
 *
 * @author 蔡志广
 * @date: 2018/4/16
 * @time: 10:33
 */
public class RetrofitUtil {

    public static final String BASE_URL = UrlAll.LOCATION_SERVICE_ADDRESS; //主地址
    private static final int DEFAULT_TIMEOUT = 8;

    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {

                OkHttpClient client = new OkHttpClient
                        .Builder()
                        .addNetworkInterceptor(new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY))
                        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .build();

                instance = new Retrofit
                        .Builder()
                        .client(client)
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
            }
        }
        return instance;
    }
}
