package com.vodafone.iot.products.components.devices;

import java.util.List;
import com.vodafone.iot.products.components.devices.dao.DeviceEntity;
import com.vodafone.iot.products.components.devices.dao.SIMEntity;
import com.vodafone.iot.products.components.devices.dto.Device;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
class DevicesMapperTest {

    @Test
    void whenConvertDeviceEntityToDeviceDtoObjectWithSIMEntitySuccessfully() {
        // given
        SIMEntity simEntity = SIMEntity.builder()
                .id(5L).status("ACTIVE").country("EGYPT").operatorCode("12334").build();

        DeviceEntity deviceEntity = DeviceEntity.builder()
                .id(10L).name("deviceName").temperature(25).sim(simEntity).build();

        // when
        Device result = DevicesMapper.INSTANCE.toDevice(deviceEntity);

        // then
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getName()).isEqualTo("deviceName");
        assertThat(result.getTemperature()).isEqualTo(25);
        assertThat(result.getManufactureDate()).isNull();
        assertThat(result.getLastUpdateDate()).isNull();
        assertThat(result.getSim().getId()).isEqualTo(5L);
        assertThat(result.getSim().getStatus()).isEqualTo("ACTIVE");
        assertThat(result.getSim().getCountry()).isEqualTo("EGYPT");
        assertThat(result.getSim().getOperatorCode()).isEqualTo("12334");

    }


    @Test
    void whenConvertListOfDeviceEntityToListOfDeviceDtoObjectWithSIMEntitySuccessfullyx() {
        // given
        SIMEntity simEntity1 = SIMEntity.builder()
                .id(5L).status("ACTIVE").country("Egypt").operatorCode("12334").build();

        DeviceEntity deviceEntity1 = DeviceEntity.builder()
                .id(10L).name("device1").temperature(25).sim(simEntity1).build();

        SIMEntity simEntity2 = SIMEntity.builder()
                .id(6L).status("ACTIVE").country("Germany").operatorCode("3214").build();

        DeviceEntity deviceEntity2 = DeviceEntity.builder()
                .id(11L).name("device2").temperature(23).sim(simEntity2).build();

        List<DeviceEntity> deviceEntities = List.of(deviceEntity1, deviceEntity2);
        // when
        List<Device> result = DevicesMapper.INSTANCE.toListOfDevices(deviceEntities);

        // then
        // first
        assertThat(result.size()).isEqualTo(deviceEntities.size());
        assertThat(result.get(0).getId()).isEqualTo(10L);
        assertThat(result.get(0).getName()).isEqualTo("device1");
        assertThat(result.get(0).getTemperature()).isEqualTo(25);
        assertThat(result.get(0).getManufactureDate()).isNull();
        assertThat(result.get(0).getLastUpdateDate()).isNull();
        assertThat(result.get(0).getSim().getId()).isEqualTo(5L);
        assertThat(result.get(0).getSim().getStatus()).isEqualTo("ACTIVE");
        assertThat(result.get(0).getSim().getCountry()).isEqualTo("Egypt");
        assertThat(result.get(0).getSim().getOperatorCode()).isEqualTo("12334");

        // second
        assertThat(result.size()).isEqualTo(deviceEntities.size());
        assertThat(result.get(1).getId()).isEqualTo(11L);
        assertThat(result.get(1).getName()).isEqualTo("device2");
        assertThat(result.get(1).getTemperature()).isEqualTo(23);
        assertThat(result.get(1).getManufactureDate()).isNull();
        assertThat(result.get(1).getLastUpdateDate()).isNull();
        assertThat(result.get(1).getSim().getId()).isEqualTo(6L);
        assertThat(result.get(1).getSim().getStatus()).isEqualTo("ACTIVE");
        assertThat(result.get(1).getSim().getCountry()).isEqualTo("Germany");
        assertThat(result.get(1).getSim().getOperatorCode()).isEqualTo("3214");
    }

}
