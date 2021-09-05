package com.vodafone.iot.products.controllers;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import com.vodafone.iot.products.components.devices.DevicesService;
import com.vodafone.iot.products.components.devices.common.constants.ApiConstants;
import com.vodafone.iot.products.components.devices.dto.Device;
import com.vodafone.iot.products.components.devices.dto.DeviceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/products/devices")
@RequiredArgsConstructor
@Validated
public class DevicesController implements DevicesApi {

    private final DevicesService devicesService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Device> getAllDevicesByDeviceSIMConfigStatus(@RequestParam String status,
                                                             @RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_OFFSET) @Min(ApiConstants.MIN_OFFSET) Integer offset,
                                                             @RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_LIMIT) @Min(ApiConstants.MIN_LIMIT) @Max(ApiConstants.MAX_LIMIT) Integer limit) {

        return devicesService.getAllDevicesByDeviceSIMConfigStatus(status, offset, limit);
    }

    @PutMapping("/{deviceId}/status")
    @ResponseStatus(HttpStatus.OK)
    public Device setDeviceConfigStatus(@PathVariable Long deviceId, @Valid @RequestBody DeviceStatus deviceStatus) {

        return devicesService.setDeviceConfigStatus(deviceId, deviceStatus);
    }

    @GetMapping("/sale")
    @ResponseStatus(HttpStatus.OK)
    public List<Device> getAllDevicesReadyForSale(@RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_OFFSET) @Min(ApiConstants.MIN_OFFSET) Integer offset,
                                                  @RequestParam(required = false, defaultValue = ApiConstants.DEFAULT_LIMIT) @Min(ApiConstants.MIN_LIMIT) @Max(ApiConstants.MAX_LIMIT) Integer limit) {

        return devicesService.getAllDevicesReadyForSale(offset, limit);
    }

}
