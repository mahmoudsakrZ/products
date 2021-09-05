package com.vodafone.iot.products.components.devices;

import java.util.List;
import java.util.Optional;
import com.vodafone.iot.products.components.devices.common.constants.ApiConstants;
import com.vodafone.iot.products.components.devices.common.error.exceptions.DeviceNotFoundException;
import com.vodafone.iot.products.components.devices.common.error.exceptions.InvalidParamException;
import com.vodafone.iot.products.components.devices.dao.DeviceEntity;
import com.vodafone.iot.products.components.devices.dto.Device;
import com.vodafone.iot.products.components.devices.dto.DeviceStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(SpringExtension.class)
class DevicesServiceTest {

    @InjectMocks
    private DevicesServiceImpl devicesService;

    @Mock
    private DevicesRepository devicesRepository;

    @Mock
    private DevicesMapper devicesMapper;

    @Test
    void whenGetAllDevicesByDeviceSIMConfigStatusWithInvalidStatusParamThenThrow_InvalidParamException() {
        // given
        String status = "invalid";
        int offset = 0;
        int limit = 5;
        // when
        // then
        assertThrows(InvalidParamException.class, () -> devicesService.getAllDevicesByDeviceSIMConfigStatus(status, offset, limit));
    }

    @Test
    void whenGetAllDevicesByDeviceSIMConfigStatusWithValidDataThenReturnListOfDevicesSuccessfully() {
        // given
        String status = "ACTIVE";
        int offset = 0;
        int limit = 5;
        DeviceEntity deviceEntity1 = DeviceEntity
                .builder()
                .id(5L)
                .name("dev1")
                .build();

        DeviceEntity deviceEntity2 = DeviceEntity
                .builder()
                .id(10L)
                .name("dev2")
                .build();

        Device device1 = Device
                .builder()
                .id(5L)
                .name("dev1")
                .build();

        Device device2 = Device
                .builder()
                .id(10L)
                .name("dev2")
                .build();

        var pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, ApiConstants.DEVICES_SORTING_PARAM));

        given(devicesRepository.getAllDevicesBySIMStatus(status, pageRequest))
                .willReturn(List.of(deviceEntity1, deviceEntity2));
        given(devicesMapper.toListOfDevices(List.of(deviceEntity1, deviceEntity2)))
                .willReturn(List.of(device1, device2));

        // when
        List<Device> result = devicesService.getAllDevicesByDeviceSIMConfigStatus(status, offset, limit);

        // then
        assertThat(result.size())
                .isEqualTo(2);

    }

    @Test
    void whenSetDeviceConfigStatusForDeviceIdThatNotExistThenThrow_DeviceNotFoundException() {
        // given
        Long deviceId = 15L;
        DeviceStatus deviceStatus = DeviceStatus.builder()
                .ready(true).build();
        given(devicesRepository.findById(deviceId)).willReturn(Optional.empty());
        // when
        // then
        assertThrows(DeviceNotFoundException.class, () -> devicesService.setDeviceConfigStatus(deviceId, deviceStatus));
    }

}
