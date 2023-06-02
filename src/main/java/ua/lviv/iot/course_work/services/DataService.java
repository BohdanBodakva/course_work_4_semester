package ua.lviv.iot.course_work.services;

import ua.lviv.iot.course_work.entities.DataEntity;
import ua.lviv.iot.course_work.entities.SensorData;
import ua.lviv.iot.course_work.exceptions.DatabaseTableIsEmptyException;
import ua.lviv.iot.course_work.exceptions.DeviceNotFoundException;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface DataService {
    List<DataEntity> getAllDataByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException;
    List<DataEntity> getDataBetweenDatesBySerialNumberAndUsername(String username, String serialNumber, LocalDateTime firstDateTime, LocalDateTime secondDateTime) throws DeviceNotFoundException, UserNotFoundException;
    DataEntity saveDataBySerialNumberAndUsername(String username, String serialNumber, SensorData data) throws DeviceNotFoundException, UserNotFoundException;
    void deleteDataByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException;
//    void deleteAllData();
    DataEntity getCurrentDataByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DatabaseTableIsEmptyException, DeviceNotFoundException, DatabaseTableIsEmptyException, UserNotFoundException;
    List<DataEntity> getDataByDeviceSerialNumberAndUsernameSortedByDateASC(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException;
    List<DataEntity> getDataByDeviceSerialNumberAndUsernameSortedByDateDESC(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException;
    double getAverageAirTemperatureByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException;
    double getAverageAirHumidityByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException;
    double getAverageSoilMoistureByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException;

    // ========================================================

    String setESP32ParametersByDeviceSerialNumberAndUsername(String serialNumber, int temperatureSensorDataTransferFrequencyInSeconds, int irrigationThreshold);



}
