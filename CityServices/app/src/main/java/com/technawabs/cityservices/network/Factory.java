package com.technawabs.cityservices.network;

import android.os.StrictMode;

import com.technawabs.cityservices.services.FriendsService;
import com.technawabs.cityservices.services.ProductService;

import net.jcip.annotations.GuardedBy;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class Factory {

    private static final Object LOCK = new Object();
    public static final int TIMEOUT_IN_SECONDS = 60;

    @GuardedBy("LOCK")
    private static OkHttpClient mOkHttpClient;
    @GuardedBy("LOCK")
    private static ProductService songService;
    @GuardedBy("LOCK")
    private static FriendsService friendsService;

    public static OkHttpClient getOkHTTPClient() {
        synchronized (LOCK) {
            if (mOkHttpClient == null) {
                mOkHttpClient = new OkHttpClient.Builder()
                        .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                        .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                        .build();
            }
        }
        return mOkHttpClient;
    }

    public static ProductService getSongService() {
        synchronized (LOCK) {
            if (songService == null) {
                songService = new ProductService();
            }
        }
        return songService;
    }

    public static FriendsService getFriendsService() {
        synchronized (LOCK) {
            if (friendsService == null) {
                friendsService = new FriendsService();
            }
        }
        return friendsService;
    }

    public static void setUpThreadPolicy() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


}
