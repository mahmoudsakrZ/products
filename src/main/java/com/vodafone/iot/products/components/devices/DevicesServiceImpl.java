package com.vodafone.iot.products.components.devices;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import com.vodafone.iot.products.components.devices.common.constants.ApiConstants;
import com.vodafone.iot.products.components.devices.common.enums.SIMStatusEnum;
import com.vodafone.iot.products.components.devices.common.error.exceptions.DeviceNotFoundException;
import com.vodafone.iot.products.components.devices.common.error.exceptions.InvalidParamException;
import com.vodafone.iot.products.components.devices.dao.DeviceEntity;
import com.vodafone.iot.products.components.devices.dto.Device;
import com.vodafone.iot.products.components.devices.dto.DeviceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DevicesServiceImpl implements DevicesService{

    private final DevicesRepository devicesRepository;
    private final DevicesMapper devicesMapper;

    @Override
    public List<Device> getAllDevicesByDeviceSIMConfigStatus(final String status, final Integer offset, final Integer limit) {

        if (!SIMStatusEnum.contains(status.toUpperCase())){
            throw new InvalidParamException(status);
        }
        var pageRequest = PageRequest.of(offset,limit, Sort.by(Sort.Direction.ASC, ApiConstants.DEVICES_SORTING_PARAM));
        List<DeviceEntity> deviceEntities = devicesRepository.getAllDevicesBySIMStatus(SIMStatusEnum.valueOf(status.toUpperCase()).getStatus(), pageRequest);
        return devicesMapper.toListOfDevices(deviceEntities);
    }

    @Override
    @Transactional
    public Device setDeviceConfigStatus(final Long deviceId, final DeviceStatus deviceStatus) {
        Optional<DeviceEntity> deviceEntityOptional = devicesRepository.findById(deviceId);
        if (deviceEntityOptional.isPresent()) {
            deviceEntityOptional.get().setReady(deviceStatus.getReady());
            deviceEntityOptional.get().setLastUpdateDate(new Date());
        } else {
            throw new DeviceNotFoundException(deviceId.toString());
        }
        return devicesMapper.toDevice(deviceEntityOptional.get());
    }

    @Override
    public List<Device> getAllDevicesReadyForSale(final Integer offset, final Integer limit) {
        var pageRequest = PageRequest.of(offset,limit, Sort.by(Sort.Direction.ASC, ApiConstants.DEVICES_SORTING_PARAM));
        List<DeviceEntity> deviceEntities =
                devicesRepository.getAllDevicesReadyForSale(true,ApiConstants.MIN_TEMPERATURE_UK_CONFIG,ApiConstants.MAX_TEMPERATURE_UK_CONFIG, pageRequest);

        return devicesMapper.toListOfDevices(deviceEntities);
    }

}
