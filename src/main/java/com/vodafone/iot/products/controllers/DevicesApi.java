package com.vodafone.iot.products.controllers;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import com.vodafone.iot.products.components.devices.common.constants.ApiConstants;
import com.vodafone.iot.products.components.devices.common.enums.SIMStatusEnum;
import com.vodafone.iot.products.components.devices.common.error.ApiError;
import com.vodafone.iot.products.components.devices.dto.Device;
import com.vodafone.iot.products.components.devices.dto.DeviceStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Devices Controller API")
public interface DevicesApi {

    @Operation(summary = "Get All Devices By SIM Config Status")
    @ApiResponse(responseCode = "200", description = "The devices List With The Status has been returned",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = Device.class)
                 ))
    @ApiResponse(responseCode = "400", description = "Some parameters are missing or invalid",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiError.class)
                 ))
    @ApiResponse(responseCode = "500", description = "An error has been occurred",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiError.class)
                 ))
    List<Device> getAllDevicesByDeviceSIMConfigStatus(
            @Parameter(description = "Device SIM config status allowed values: "+ SIMStatusEnum.VALUE_LIST, required = true) String status,
            @Parameter(description = "The page offset number") @Min(ApiConstants.MIN_OFFSET) Integer offset,
            @Parameter(description = "The page limit size") @Min(ApiConstants.MIN_LIMIT) @Max(ApiConstants.MAX_LIMIT) Integer limit);

    @Operation(summary = "Update Device Ready Status By DeviceId")
    @ApiResponse(responseCode = "200", description = "The device ready status has been updated",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = Device.class)
                 ))
    @ApiResponse(responseCode = "400", description = "Some parameters are missing or invalid",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiError.class)
                 ))
    @ApiResponse(responseCode = "500", description = "An error has been occurred",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiError.class)
                 ))
    Device setDeviceConfigStatus(
            @Parameter(description = "The device id to be updated", required = true) Long deviceId,
            @Parameter(description = "The device status", required = true, schema = @Schema(implementation = DeviceStatus.class)) @Valid DeviceStatus deviceStatus);

    @Operation(summary = "Get All Devices Ready For Sale")
    @ApiResponse(responseCode = "200", description = "The devices which are ready for sale has been returned",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = Device.class)
                 ))
    @ApiResponse(responseCode = "400", description = "Some parameters are missing or invalid",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiError.class)
                 ))
    @ApiResponse(responseCode = "500", description = "An error has been occurred",
                 content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiError.class)
                 ))

    List<Device> getAllDevicesReadyForSale(
            @Parameter(description = "The page offset number") @Min(ApiConstants.MIN_OFFSET) Integer offset,
            @Parameter(description = "The page limit size") @Min(ApiConstants.MIN_LIMIT) @Max(ApiConstants.MAX_LIMIT) Integer limit);

}
