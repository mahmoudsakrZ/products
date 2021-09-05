package com.vodafone.iot.products.components.devices.common.error.exceptions;

import com.vodafone.iot.products.components.devices.common.error.codes.ApiErrorCode;


public class InvalidParamException extends ServiceException {

    public InvalidParamException(final String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.INVALID_PARAM_VALUE;
    }

}
