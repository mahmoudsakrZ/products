package com.vodafone.iot.products.components.devices;

import java.util.List;
import com.vodafone.iot.products.components.devices.dao.DeviceEntity;
import com.vodafone.iot.products.components.devices.dao.SIMEntity;
import com.vodafone.iot.products.components.devices.dto.Device;
import com.vodafone.iot.products.components.devices.dto.SIM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring"
)
public interface DevicesMapper {
    DevicesMapper INSTANCE = Mappers.getMapper( DevicesMapper.class);

    @Mapping(source = "manufactureDate", target = "manufactureDate" ,dateFormat = "yyyy-mm-dd hh:mm:ssZ")
    @Mapping(source = "lastUpdateDate", target = "lastUpdateDate" ,dateFormat = "yyyy-mm-dd hh:mm:ssZ")
    Device toDevice(DeviceEntity deviceEntity);

    @Mapping(source = "manufactureDate", target = "manufactureDate" ,dateFormat = "yyyy-mm-dd hh:mm:ssZ")
    @Mapping(source = "lastUpdateDate", target = "lastUpdateDate" ,dateFormat = "yyyy-mm-dd hh:mm:ssZ")
    List<Device> toListOfDevices(List<DeviceEntity> deviceEntities);

    SIM toSIM(SIMEntity simEntity);
}
