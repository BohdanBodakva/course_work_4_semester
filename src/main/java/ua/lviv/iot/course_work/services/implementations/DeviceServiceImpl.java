package ua.lviv.iot.course_work.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.lviv.iot.course_work.entities.DeviceEntity;
import ua.lviv.iot.course_work.entities.UserEntity;
import ua.lviv.iot.course_work.exceptions.DatabaseTableIsEmptyException;
import ua.lviv.iot.course_work.exceptions.DeviceNotFoundException;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;
import ua.lviv.iot.course_work.repositories.DeviceRepository;
import ua.lviv.iot.course_work.repositories.UserRepository;
import ua.lviv.iot.course_work.services.DeviceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    @Override
    public List<DeviceEntity> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public DeviceEntity getDeviceBySerialNumber(String serialNumber) throws DeviceNotFoundException, DatabaseTableIsEmptyException {
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        return deviceRepository.findDeviceEntityBySerialNumber(serialNumber).
            orElseThrow(() -> new DatabaseTableIsEmptyException("There is no devices in database"));
    }

    @Override
    public DeviceEntity saveDeviceByUserUsername(String username, DeviceEntity device) throws UserNotFoundException {
        UserEntity user =  userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        device.setUser(user);
        return deviceRepository.save(device);
    }

    @Override
    public void deleteDeviceBySerialNumberAndUsername(String username, String serialNumber) throws UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.deleteDeviceEntityBySerialNumberAndUsername(username, serialNumber);
    }
}
