package com.dj.katchup.network;


import com.dj.katchup.KatchUpApplication;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dhirain Jain on 14-09-2017.
 */

public class NetworkService {

    public static final String API_BASE_URL = "http://c1e25ee7.ngrok.io/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static NetworkClient builder;

    public static NetworkClient instance() {
        return builder;
    }

    static {
        //setup cache
        httpClient.addNetworkInterceptor(new CacheInterceptor());
        httpClient.cache(setCacheSize(2 * 1024 * 1024));// 2 MB

        builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build().create(NetworkClient.class);
    }

    private static Cache setCacheSize(int cacheSize) {
        File httpCacheDirectory = new File(KatchUpApplication.getInstance().getContext().getCacheDir(), "responses");
        return new Cache(httpCacheDirectory, cacheSize);
    }

}
