package com.technawabs.cityservices.services;

import android.util.Log;

import com.google.common.util.concurrent.ListenableFuture;
import com.technawabs.cityservices.concurrency.ExecutorUtils;
import com.technawabs.cityservices.constants.AppAPI;
import com.technawabs.cityservices.constants.AppConstant;
import com.technawabs.cityservices.network.RequestGenerator;
import com.technawabs.cityservices.network.RequestHandler;
import com.technawabs.cityservices.utils.ExceptionUtils;
import com.technawabs.cityservices.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.Callable;

import okhttp3.Request;

public class FriendsService {

    private final String TAG=this.getClass().getSimpleName();

    public ListenableFuture<JSONArray> getFriends() {
        return ExecutorUtils.getBackgroundPool().submit(new Callable<JSONArray>() {
            @Override
            public JSONArray call() throws Exception {
                Request request = RequestGenerator.get(AppAPI.FRIENDS_LIST_URL);
                Log.d(TAG, request.toString());
                String result = RequestHandler.makeRequestAndValidate(request);
                Log.d(TAG, result);
                JSONObject response=new JSONObject(result);
                JSONArray users = new JSONArray();
                users=response.getJSONArray(AppConstant.USERS);
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
