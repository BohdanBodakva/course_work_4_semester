package ua.lviv.iot.course_work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query(nativeQuery = true, value = "delete from device_entity d where d.username=:username and d.serial_number=:serialNumber")
    void deleteDeviceEntityBySerialNumberAndUsername(@Param("username") String username, @Param("serialNumber") String serialNumber);
}
