package com.vodafone.iot.products.components.devices.common.error.exceptions;

import com.vodafone.iot.products.components.devices.common.error.codes.ApiErrorCode;


public abstract class ServiceException extends RuntimeException{

    ServiceException(String message) {
        super(message);
    }

    ServiceException() {
        super();
    }

    public abstract ApiErrorCode getApiErrorCode();

}
