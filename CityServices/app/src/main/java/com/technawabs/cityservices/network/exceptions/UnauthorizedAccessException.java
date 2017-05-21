package com.technawabs.cityservices.network.exceptions;

import com.technawabs.cityservices.constants.AppConstant;

public class UnauthorizedAccessException extends HttpException {

    public UnauthorizedAccessException() {
        super(AppConstant.EXCEPTION.UNAUTHORIZED_ACCESS);
    }
}
