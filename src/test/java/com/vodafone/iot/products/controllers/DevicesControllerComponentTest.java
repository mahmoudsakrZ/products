package com.vodafone.iot.products.controllers;

import com.vodafone.iot.products.components.devices.common.error.ApiError;
import com.vodafone.iot.products.components.devices.common.error.codes.ApiErrorCode;
import com.vodafone.iot.products.components.devices.dto.Device;
import com.vodafone.iot.products.components.devices.dto.DeviceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.liquibase.change-log=classpath:/liquibase/master-test.xml")
class DevicesControllerComponentTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URI = "/products/devices";



    @Test
    @Sql("classpath:/liquibase/data/clear-data.sql")
    @Sql("classpath:/liquibase/data/add-devices-data.sql")
    void whenGetAllDevicesByDeviceSIMConfigStatus_WAITING_FOR_ACTIVATION_SuccessfullyThenReturn_200() {
        // given
        String status = "Waiting_for_activation";
        // when
        ResponseEntity<Device[]> response = restTemplate.exchange(BASE_URI + "/?status="+status , HttpMethod.GET, null, Device[].class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getId()).isEqualTo(3);
        assertThat(response.getBody()[0].getName()).isEqualTo("device3");

    }

    @Test
    @Sql("classpath:/liquibase/data/clear-data.sql")
    @Sql("classpath:/liquibase/data/add-devices-data.sql")
    void whenGetAllDevicesReadyForSaleSuccessfullyThenReturn_200() {
        // given
        // when
        ResponseEntity<Device[]> response = restTemplate.exchange(BASE_URI + "/sale" , HttpMethod.GET, null, Device[].class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()[0].getId()).isEqualTo(3);
        assertThat(response.getBody()[0].getName()).isEqualTo("device3");
        assertThat(response.getBody()[1].getId()).isEqualTo(2);
        assertThat(response.getBody()[1].getName()).isEqualTo("device2");
    }

    @Test
    @Sql("classpath:/liquibase/data/clear-data.sql")
    void whenSetDeviceConfigStatusForDeviceWhichNotExistThenReturn_404() {
        // given
        Long deviceId = 5L;
        DeviceStatus deviceStatus = DeviceStatus.builder()
                .ready(true).build();
        HttpEntity<DeviceStatus> requestEntity = new HttpEntity<>(deviceStatus, null);

        // when
        ResponseEntity<ApiError> response = restTemplate.exchange(BASE_URI +"/"+deviceId+"/status" , HttpMethod.PUT, requestEntity, ApiError.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getCode()).isEqualTo(ApiErrorCode.DEVICE_NOT_FOUND.getCode());
        assertThat(response.getBody().getMessage()).isEqualTo(ApiErrorCode.DEVICE_NOT_FOUND.name());
    }

    @Test
    @Sql("classpath:/liquibase/data/clear-data.sql")
    @Sql("classpath:/liquibase/data/add-devices-data.sql")
    void whenSetDeviceConfigStatusForDeviceSuccessfullyThenReturn_200() {
        // given
        Long deviceId = 1L;
        DeviceStatus deviceStatus = DeviceStatus.builder()
                .ready(true).build();
        HttpEntity<DeviceStatus> requestEntity = new HttpEntity<>(deviceStatus, null);

        // when
        ResponseEntity<Device> response = restTemplate.exchange(BASE_URI +"/"+deviceId+"/status" , HttpMethod.PUT, requestEntity, Device.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(deviceId);
        assertThat(response.getBody().getName()).isEqualTo("device1");
    }

}
