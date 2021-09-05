package com.vodafone.iot.products.components.devices;

import java.util.List;
import com.vodafone.iot.products.components.devices.dao.DeviceEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface DevicesRepository extends JpaRepository<DeviceEntity, Long> {

    @Query("SELECT device FROM DeviceEntity device INNER JOIN SIMEntity sim ON device.id = sim.device.id AND sim.status =:status")
    List<DeviceEntity> getAllDevicesBySIMStatus(@Param("status") String status, Pageable pageable);

    @Query("SELECT device FROM DeviceEntity device WHERE device.ready =:ready AND device.temperature >= :minTemperature AND  device.temperature <= :maxTemperature")
    List<DeviceEntity> getAllDevicesReadyForSale(@Param("ready") boolean ready,
                                                 @Param("minTemperature") Integer minTemperature,
                                                 @Param("maxTemperature") Integer maxTemperature, Pageable pageable);



}
