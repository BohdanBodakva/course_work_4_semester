package ua.lviv.iot.course_work.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.course_work.entities.ClearDataEntity;

import java.util.List;

@Repository
public interface ClearDataRepository extends JpaRepository<ClearDataEntity, Long> {
    @Query(nativeQuery = true, value = "select c.* from clear_data_dates_entity c where c.serial_number=:serialNumber;")
    List<ClearDataEntity> findAllBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query(nativeQuery = true, value = "delete from clear_data_dates_entity c where c.serial_number=:serialNumber;")
    void deleteAllBySerialNumber(@Param("serialNumber") String serialNumber);
}
