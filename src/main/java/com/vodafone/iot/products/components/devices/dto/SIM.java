package com.vodafone.iot.products.components.devices.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SIM {
    private Long id;
    private String operatorCode;
    private String country;
    private String status;
}
