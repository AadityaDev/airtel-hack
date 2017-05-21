package com.technawabs.cityservices.services;

import android.util.Log;

import com.google.common.util.concurrent.ListenableFuture;
import com.technawabs.cityservices.concurrency.ExecutorUtils;
import com.technawabs.cityservices.constants.AppAPI;
import com.technawabs.cityservices.network.RequestGenerator;
import com.technawabs.cityservices.network.RequestHandler;
import com.technawabs.cityservices.utils.ExceptionUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.Callable;

import okhttp3.Request;

public class MobileUserService {

    private final String TAG=this.getClass().getSimpleName();

    public ListenableFuture<JSONObject> getProducts() {
        return ExecutorUtils.getBackgroundPool().submit(new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                Request request = RequestGenerator.get(AppAPI.PROFILE_DETAIL_URL);
                Log.d(TAG, request.toString());
                String result = RequestHandler.makeRequestAndValidate(request);
                Log.d(TAG, result);
                JSONObject users = new JSONObject(result);
                try {
                    Log.d(TAG, users.toString());
                } catch (Exception exception) {
                    ExceptionUtils.exceptionMessage(exception,TAG);
                } finally {
                    return users;
                }
            }
        });
    }


}
