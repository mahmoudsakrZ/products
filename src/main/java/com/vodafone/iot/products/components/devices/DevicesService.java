package com.vodafone.iot.products.components.devices;

import java.util.List;
import com.vodafone.iot.products.components.devices.dto.Device;
import com.vodafone.iot.products.components.devices.dto.DeviceStatus;


public interface DevicesService {

    List<Device> getAllDevicesByDeviceSIMConfigStatus(String status, Integer offset, Integer limit);

    Device setDeviceConfigStatus(Long deviceId, DeviceStatus deviceStatus);

    List<Device> getAllDevicesReadyForSale(Integer offset, Integer limit);

}
