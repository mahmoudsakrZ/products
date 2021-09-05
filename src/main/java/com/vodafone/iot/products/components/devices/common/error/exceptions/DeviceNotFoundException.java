package com.vodafone.iot.products.components.devices.common.error.exceptions;

import com.vodafone.iot.products.components.devices.common.error.codes.ApiErrorCode;


public class DeviceNotFoundException extends ServiceException {

    public DeviceNotFoundException(final String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.DEVICE_NOT_FOUND;
    }

}
