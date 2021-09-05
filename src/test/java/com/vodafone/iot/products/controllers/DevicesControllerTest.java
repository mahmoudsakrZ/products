package com.vodafone.iot.products.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.iot.products.components.devices.DevicesService;
import com.vodafone.iot.products.components.devices.common.constants.ApiConstants;
import com.vodafone.iot.products.components.devices.common.error.codes.ApiErrorCode;
import com.vodafone.iot.products.components.devices.common.error.exceptions.DeviceNotFoundException;
import com.vodafone.iot.products.components.devices.dto.DeviceStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DevicesController.class)
class DevicesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DevicesService devicesService;

    private final String BASE_URI = "/products/devices";

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenGetAllDevicesByDeviceSIMConfigStatusWithInvalidRequestParam_offset_ThenReturn_400_INVALID_PARAM_VALUE() throws Exception {
        // given
        String offset = "-1";
        String status = "ACTIVE";
        // when
        //then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(BASE_URI)
                        .queryParam("offset", offset)
                        .queryParam("status", status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(ApiErrorCode.INVALID_PARAM_VALUE.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is(ApiErrorCode.INVALID_PARAM_VALUE.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").isNotEmpty());
    }

    @Test
    void whenGetAllDevicesByDeviceSIMConfigStatusWithMissingQueryParamThenReturn_400_MISSING_QUERY_PARAM() throws Exception {
        // given
        String offset = "1";
        String limit = "10";
        // when
        //then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(BASE_URI)
                        .queryParam("offset", offset)
                        .queryParam("limit", limit)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(ApiErrorCode.MISSING_QUERY_PARAM.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is(ApiErrorCode.MISSING_QUERY_PARAM.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").isNotEmpty());
    }

    @Test
    void whenSetDeviceConfigStatusWithMissingReadyParamInBodyThenReturn_400_MISSING_BODY_FIELD() throws Exception {
        // given
        long deviceId = 44L;
        // when
        //then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(BASE_URI+"/"+deviceId+"/status")
                        .content(objectMapper.writeValueAsString(new DeviceStatus()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(ApiErrorCode.MISSING_BODY_FIELD.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is(ApiErrorCode.MISSING_BODY_FIELD.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").isNotEmpty());
    }

    @Test
    void whenSetDeviceConfigStatusForDeviceIdThatNotExistThenReturn_404_DEVICE_NOT_FOUND() throws Exception {
        // given
        Long deviceId = 4L;
        DeviceStatus deviceStatus = DeviceStatus.builder()
                .ready(true).build();
        given(devicesService.setDeviceConfigStatus(any(), any()))
                .willThrow(new DeviceNotFoundException(String.valueOf(deviceId)));
        // when
        //then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(BASE_URI+"/"+deviceId+"/status")
                        .content(objectMapper.writeValueAsString(deviceStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(ApiErrorCode.DEVICE_NOT_FOUND.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is(ApiErrorCode.DEVICE_NOT_FOUND.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").isNotEmpty());
    }

    @Test
    void whenGetAllDevicesReadyForSaleWithLimitThatExceedTheMaxLimitValueThenReturn_400_INVALID_PARAM_VALUE() throws Exception {
        // given
        String status = "ACTIVE";
        // when
        //then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(BASE_URI)
                        .queryParam("status", status)
                        .queryParam("limit", String.valueOf(ApiConstants.MAX_LIMIT + 1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", is(ApiErrorCode.INVALID_PARAM_VALUE.getCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", is(ApiErrorCode.INVALID_PARAM_VALUE.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").isNotEmpty());
    }

}
