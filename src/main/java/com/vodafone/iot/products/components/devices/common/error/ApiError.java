package com.vodafone.iot.products.components.devices.common.error;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ApiError {
    private int code;
    private String message;
    private String description;
}
