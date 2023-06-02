package ua.lviv.iot.course_work.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.lviv.iot.course_work.entities.ClearDataEntity;
import ua.lviv.iot.course_work.entities.DeviceEntity;
import ua.lviv.iot.course_work.exceptions.ClearDataNotFoundException;
import ua.lviv.iot.course_work.exceptions.DeviceNotFoundException;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;
import ua.lviv.iot.course_work.repositories.ClearDataRepository;
import ua.lviv.iot.course_work.repositories.DeviceRepository;
import ua.lviv.iot.course_work.repositories.UserRepository;
import ua.lviv.iot.course_work.services.ClearDataService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClearDataServiceImpl implements ClearDataService {
    private final ClearDataRepository clearDataRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public List<ClearDataEntity> getAllClearData() {
        return clearDataRepository.findAll();
    }

    @Override
    public List<ClearDataEntity> getAllClearDataByDeviceSerialNumberAndUsername(String username, String serialNumber) throws UserNotFoundException, DeviceNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        return clearDataRepository.findAllBySerialNumber(serialNumber);
    }

    @Override
    public void deleteAllClearDataByDeviceSerialNumberAndUsername(String username, String serialNumber) throws UserNotFoundException, DeviceNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        clearDataRepository.deleteAllBySerialNumber(serialNumber);
    }

    @Override
    public ClearDataEntity saveClearDataBySerialNumberAndUsername(String username, String serialNumber) throws UserNotFoundException, DeviceNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        DeviceEntity device = deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));

        ClearDataEntity clearData = new ClearDataEntity(
                LocalDateTime.now(),
                device
        );

        return clearDataRepository.save(clearData);
    }

    @Override
    public void deleteAllClearData() {
        clearDataRepository.deleteAll();
    }
}
