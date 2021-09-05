package com.vodafone.iot.products.components.devices.common.error.codes;

import com.vodafone.iot.products.components.devices.common.error.ApiError;
import org.springframework.http.HttpStatus;


public interface IApiErrorCode {

    HttpStatus getHttpStatus();

    ApiError toResponseEntity();

    ApiError toResponseEntity(String detailedMessage);

}
