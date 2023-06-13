package ua.lviv.iot.course_work.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.course_work.entities.DeviceEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Integer> {
    Optional<DeviceEntity> findDeviceEntityBySerialNumber(String serialNumber);
    List<DeviceEntity> findDeviceEntitiesByUserUsername(String username);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from device_entity d where d.serial_number=:serialNumber")
    void deleteDeviceEntityBySerialNumberAndUsername(@Param("serialNumber") String serialNumber);

//    List<DeviceEntity> findDeviceEntitiesByUserUsername(String username);
}
