package com.technawabs.cityservices.utils;

import android.support.annotation.NonNull;
import android.util.Log;
import com.technawabs.cityservices.utils.StringUtils;

public class ExceptionUtils {

    public static void exceptionMessage(@NonNull Exception exception, @NonNull String TAG) {
        if (StringUtils.isNotEmptyOrNull(exception.getMessage())) {
            Log.d(TAG, exception.getMessage());
        } else {
            Log.d(TAG, exception.getMessage());
        }
    }

}
