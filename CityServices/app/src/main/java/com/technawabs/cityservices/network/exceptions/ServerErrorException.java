package com.technawabs.cityservices.network.exceptions;

import com.technawabs.cityservices.constants.AppConstant;

public class ServerErrorException extends HttpException {

    public ServerErrorException() {
        super(AppConstant.MESSAGE_SERVER_ERROR);
    }
}
