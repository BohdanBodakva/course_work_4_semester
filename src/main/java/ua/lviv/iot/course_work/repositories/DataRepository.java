package ua.lviv.iot.course_work.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.course_work.entities.DataEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, Long> {

//    @Query(nativeQuery = true, value = "select avg(d.air_temperature) as avgAirTemperature, avg(d.air_humidity) as avgAirHumidity, avg(d.soil_moisture) as avgSoilMoisture from data_entity d where d.serial_number=:serialNumber")
//    AverageValues findAverageValues(@Param("serialNumber") String serialNumber);
    @Query(nativeQuery = true, value = "select d.* from data_entity d where d.serial_number=:serialNumber")
    List<DataEntity> findDataEntitiesByDeviceSerialNumber(@Param("serialNumber") String serialNumber);

    @Query(nativeQuery = true, value = "select d.* from data_entity d where d.serial_number=:serialNumber and (d.date_time between :firstDateTime and :secondDateTime)")
    List<DataEntity> findDataBetweenDatesBySerialNumber(@Param("serialNumber") String serialNumber, @Param("firstDateTime") LocalDateTime firstDateTime, @Param("secondDateTime") LocalDateTime secondDateTime);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from data_entity d where d.serial_number=:serialNumber")
    void deleteDataEntityBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query(nativeQuery = true, value = "select d.* from data_entity d where  d.serial_number=:serialNumber order by d.date")
    List<DataEntity> findDataEntitiesBySerialNumberSortedByDateASC(@Param("serialNumber") String serialNumber);

    @Query(nativeQuery = true, value = "select d.* from data_entity d where  d.serial_number=:serialNumber order by d.date desc")
    List<DataEntity> findDataEntitiesBySerialNumberSortedByDateDESC(@Param("serialNumber") String serialNumber);

    @Query(nativeQuery = true, value = "select AVG(d.air_temperature) from data_entity d where d.serial_number=:serialNumber")
    double findAverageAirTemperatureBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query(nativeQuery = true, value = "select AVG(d.air_humidity) from data_entity d where d.serial_number=:serialNumber")
    double findAverageAirHumidityBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query(nativeQuery = true, value = "select AVG(d.soil_moisture) from data_entity d where d.serial_number=:serialNumber")
    double findAverageSoilMoistureBySerialNumber(@Param("serialNumber") String serialNumber);

    @Query(nativeQuery = true, value = "select d.* from data_entity d order by d.id desc limit 1")
    Optional<DataEntity> findCurrentDataByDeviceSerialNumber(@Param("serialNumber") String serialNumber);

//    List<DataEntity> getDeviceDataByUsernameAndSerialNumber();


}
