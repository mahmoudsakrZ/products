package com.vodafone.iot.products.components.devices.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    private Long id;
    private String name;
    private String manufactureDate;
    private boolean ready;
    private Integer temperature;
    private String lastUpdateDate;
    private SIM sim;

}
