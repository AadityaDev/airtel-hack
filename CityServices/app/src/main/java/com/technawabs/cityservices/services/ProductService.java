package com.technawabs.cityservices.services;

import android.util.Log;

import com.google.common.util.concurrent.ListenableFuture;
import com.technawabs.cityservices.concurrency.ExecutorUtils;
import com.technawabs.cityservices.constants.AppAPI;
import com.technawabs.cityservices.constants.AppConstant;
import com.technawabs.cityservices.network.RequestGenerator;
import com.technawabs.cityservices.network.RequestHandler;
import com.technawabs.cityservices.utils.StringUtils;

import org.json.JSONArray;

import java.util.concurrent.Callable;

import okhttp3.Request;

public class ProductService {

    private final String TAG=this.getClass().getSimpleName();

    public ListenableFuture<JSONArray> getProducts(){
        return ExecutorUtils.getBackgroundPool().submit(new Callable<JSONArray>() {
            @Override
            public JSONArray call() throws Exception {
                 Request request = RequestGenerator.get(AppAPI.PRODUCT_URL);
//                Request request=new Request.Builder()..url(AppAPI.PRODUCT_URL).get().build();
                Log.d(TAG, request.toString());
                String result = RequestHandler.makeRequestAndValidate(request);
                Log.d(TAG, result);
                JSONArray score = new JSONArray(result);
                try {
                    Log.d(TAG, score.toString());
                } catch (Exception exception) {
                    if ((exception != null) && (!StringUtils.isNotEmptyOrNull(exception.getMessage()))) {
                        Log.d(TAG, exception.getMessage());
                    } else {
                        Log.d(TAG, AppConstant.EXCEPTION.EXCEPTION);
                    }
                } finally {
                    return score;
                }
            }
        });
    }

}
