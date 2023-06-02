package ua.lviv.iot.course_work.services;

import ua.lviv.iot.course_work.entities.ClearDataEntity;
import ua.lviv.iot.course_work.exceptions.ClearDataNotFoundException;
import ua.lviv.iot.course_work.exceptions.DeviceNotFoundException;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;

import java.util.List;

public interface ClearDataService {
    List<ClearDataEntity> getAllClearData();
    List<ClearDataEntity> getAllClearDataByDeviceSerialNumberAndUsername(String username, String serialNumber) throws UserNotFoundException, DeviceNotFoundException;
    void deleteAllClearDataByDeviceSerialNumberAndUsername(String username, String serialNumber) throws UserNotFoundException, DeviceNotFoundException;
    ClearDataEntity saveClearDataBySerialNumberAndUsername(String username, String serialNumber) throws UserNotFoundException, DeviceNotFoundException;
    void deleteAllClearData();
}
