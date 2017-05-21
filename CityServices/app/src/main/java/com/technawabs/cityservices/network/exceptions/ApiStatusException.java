package com.technawabs.cityservices.network.exceptions;

public class ApiStatusException extends HttpException {
    public ApiStatusException(String message) {
        super(message);
    }
}
