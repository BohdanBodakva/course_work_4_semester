package ua.lviv.iot.course_work.services;

import ua.lviv.iot.course_work.entities.DeviceEntity;
import ua.lviv.iot.course_work.entities.UserEntity;
import ua.lviv.iot.course_work.exceptions.DatabaseTableIsEmptyException;
import ua.lviv.iot.course_work.exceptions.DeviceNotFoundException;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;

import java.util.List;

public interface DeviceService {
    List<DeviceEntity> getAllDevices();
    DeviceEntity getDeviceBySerialNumber(String serialNumber) throws DeviceNotFoundException, DatabaseTableIsEmptyException;
    DeviceEntity saveDeviceByUserUsername(String username, DeviceEntity device) throws UserNotFoundException;
    void deleteDeviceBySerialNumberAndUsername(String username, String serialNumber) throws UserNotFoundException;
}
