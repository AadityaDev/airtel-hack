package com.technawabs.cityservices.network.exceptions;


import com.technawabs.cityservices.constants.AppConstant;

public class AccessDeniedException extends HttpException {
    public AccessDeniedException() {
        super(AppConstant.EXCEPTION.ACCESS_DENIED);
    }
}
