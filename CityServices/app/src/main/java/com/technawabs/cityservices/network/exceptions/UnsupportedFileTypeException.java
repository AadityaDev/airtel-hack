package com.technawabs.cityservices.network.exceptions;

import com.technawabs.cityservices.constants.AppConstant;

public class UnsupportedFileTypeException extends HttpException {

    public UnsupportedFileTypeException() {
        super(AppConstant.EXCEPTION.MESSAGE_UNSUPPORTED_FILE_TYPE);
    }

}
