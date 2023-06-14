package ua.lviv.iot.course_work.services;

import ua.lviv.iot.course_work.entities.DataEntity;
import ua.lviv.iot.course_work.entities.DeviceEntity;
import ua.lviv.iot.course_work.entities.UserEntity;
import ua.lviv.iot.course_work.exceptions.DatabaseTableIsEmptyException;
import ua.lviv.iot.course_work.exceptions.DeviceNotFoundException;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;

import java.util.List;

public interface DeviceService {
    List<DeviceEntity> getAllDevices();
    List<DataEntity> getDeviceDataBySerialNumberOnly(String username, String serialNumber) throws UserNotFoundException, DeviceNotFoundException;
    List<DataEntity> getDeviceDataByUsernameAndSerialNumber(String username, String serialNumber) throws UserNotFoundException;
    DeviceEntity getDevicesBySerialNumber(String serialNumber) throws DeviceNotFoundException, DatabaseTableIsEmptyException;
    DeviceEntity saveDeviceByUserUsername(String username, DeviceEntity device) throws UserNotFoundException;
    List<DeviceEntity> getAllDevicesByUserUsername(String username) throws UserNotFoundException;
    void deleteDeviceBySerialNumberAndUsername(String username, String serialNumber) throws UserNotFoundException;
}
