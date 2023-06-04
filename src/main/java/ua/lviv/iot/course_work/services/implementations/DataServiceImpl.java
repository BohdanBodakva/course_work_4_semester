package ua.lviv.iot.course_work.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.lviv.iot.course_work.entities.ClearDataEntity;
import ua.lviv.iot.course_work.entities.DataEntity;
import ua.lviv.iot.course_work.entities.DeviceEntity;
import ua.lviv.iot.course_work.entities.SensorData;
import ua.lviv.iot.course_work.exceptions.DatabaseTableIsEmptyException;
import ua.lviv.iot.course_work.exceptions.DeviceNotFoundException;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;
import ua.lviv.iot.course_work.repositories.ClearDataRepository;
import ua.lviv.iot.course_work.repositories.DataRepository;
import ua.lviv.iot.course_work.repositories.DeviceRepository;
import ua.lviv.iot.course_work.repositories.UserRepository;
import ua.lviv.iot.course_work.services.DataService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataRepository dataRepository;
    private final ClearDataRepository clearDataRepository;
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final String ESP32IPAddress = "192.168.1.101";




    @Override
    public List<DataEntity> getAllDataByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        return dataRepository.findDataEntitiesByDeviceSerialNumber(serialNumber);
    }

    @Override
    public List<DataEntity> getDataBetweenDatesBySerialNumberAndUsername(String username, String serialNumber, LocalDateTime firstDateTime, LocalDateTime secondDateTime) throws DeviceNotFoundException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        return dataRepository.findDataBetweenDatesBySerialNumber(serialNumber, firstDateTime, secondDateTime);
    }

    @Override
    public DataEntity saveDataBySerialNumberAndUsername(String username, String serialNumber, SensorData data) throws DeviceNotFoundException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        DeviceEntity device = deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        DataEntity dataToSave = new DataEntity(
                LocalDateTime.now(),
                data.getAirTemperature(),
                data.getAirHumidity(),
                data.getSoilMoisture(),
                device
        );
        return dataRepository.save(dataToSave);
    }

    @Override
    public void deleteDataByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        dataRepository.deleteDataEntityBySerialNumber(serialNumber);
    }

    @Override
    public DataEntity getCurrentDataByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, DatabaseTableIsEmptyException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        return dataRepository.findCurrentDataByDeviceSerialNumber(serialNumber)
                .orElseThrow(() -> new DatabaseTableIsEmptyException("There is no device data in database"));
    }

    @Override
    public List<DataEntity> getDataByDeviceSerialNumberAndUsernameSortedByDateASC(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        return dataRepository.findDataEntitiesBySerialNumberSortedByDateASC(serialNumber);
    }

    @Override
    public List<DataEntity> getDataByDeviceSerialNumberAndUsernameSortedByDateDESC(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        return dataRepository.findDataEntitiesBySerialNumberSortedByDateDESC(serialNumber);
    }

    @Override
    public double getAverageAirTemperatureByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        return dataRepository.findAverageAirTemperatureBySerialNumber(serialNumber);
    }

    @Override
    public double getAverageAirHumidityByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        return dataRepository.findAverageAirHumidityBySerialNumber(serialNumber);
    }

    @Override
    public double getAverageSoilMoistureByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        return dataRepository.findAverageSoilMoistureBySerialNumber(serialNumber);
    }

    @Override
    public String setESP32ParametersByDeviceSerialNumberAndUsername(String username,
                                                         String serialNumber,
                                                         int temperatureSensorDataTransferFrequencyInSeconds,
                                                         int irrigationThreshold) throws UserNotFoundException, DeviceNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));

        System.out.println("--------------------------------------------");

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://" + ESP32IPAddress + "/set-parameters?frequency=" + temperatureSensorDataTransferFrequencyInSeconds
                + "&irrigationThreshold=" + irrigationThreshold;
        String s =  restTemplate.getForObject(url, String.class);
        System.out.println(s);
        return s;
    }

    @Override
    public String postESP32SensorDataByDeviceSerialNumberAndUsername(String username, String serialNumber, SensorData data) throws UserNotFoundException, DeviceNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        DeviceEntity device = deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));

        dataRepository.save(new DataEntity(
                LocalDateTime.now(),
                data.getAirTemperature(),
                data.getAirHumidity(),
                data.getSoilMoisture(),
                device
        ));

        return "Data was saved";
    }


}
