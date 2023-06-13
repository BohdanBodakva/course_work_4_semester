package ua.lviv.iot.course_work.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.lviv.iot.course_work.entities.*;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final DataRepository dataRepository;
    private final ClearDataRepository clearDataRepository;
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final String ESP32IPAddress = "192.168.1.102";


//    @Override
//    public AverageValues getAverageValues(String username, String serialNumber) throws UserNotFoundException, DeviceNotFoundException {
//        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
//                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
//        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
//                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
//        if(deviceRepository.findDeviceEntitiesByUserUsername(username).size() == 0){
//            throw new DeviceNotFoundException("Device with serial number = " + serialNumber + " doesn't exist for user with username = " + username);
//        }
//        System.out.println(dataRepository.findAverageValues(serialNumber));
//        return dataRepository.findAverageValues(serialNumber);
//    }

    @Override
    public List<DataEntity> getAllDataByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        if(deviceRepository.findDeviceEntitiesByUserUsername(username).size() == 0){
            throw new DeviceNotFoundException("Device with serial number = " + serialNumber + " doesn't exist for user with username = " + username);
        }
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
        if(deviceRepository.findDeviceEntitiesByUserUsername(username).size() == 0){
            throw new DeviceNotFoundException("Device with serial number = " + serialNumber + " doesn't exist for user with username = " + username);
        }
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

        List<DeviceEntity> devices = deviceRepository.findDeviceEntitiesByUserUsername(username);
        System.out.println(devices);
        if(devices.stream().filter(device -> device.getSerialNumber().equals(serialNumber)).toList().size() == 0){
            throw new DeviceNotFoundException("Device with serialNumber = " + serialNumber + " doesn't exist for user with username " + username);
        }
        System.out.println(devices.stream().filter(device -> device.getSerialNumber().equals(serialNumber)).toList().size());

        return dataRepository.findDataEntitiesBySerialNumberSortedByDateDESC(serialNumber);
    }

    @Override
    public double getAverageAirTemperatureByDeviceSerialNumberAndUsername(String username, String serialNumber) throws DeviceNotFoundException, UserNotFoundException {
        userRepository.findUserEntityByUsernameAndStatusIsActive(username)
                .orElseThrow(() -> new UserNotFoundException("Active user with username = " + username + " doesn't exist"));
        deviceRepository.findDeviceEntityBySerialNumber(serialNumber)
                .orElseThrow(() -> new DeviceNotFoundException("Device with serial number = " + serialNumber + " wasn't found"));
        if(deviceRepository.findDeviceEntitiesByUserUsername(username).size() == 0){
            throw new DeviceNotFoundException("Device with serial number = " + serialNumber + " doesn't exist for user with username = " + username);
        }
        return dataRepository.findDataEntitiesByDeviceSerialNumber(serialNumber)
                .stream()
                .mapToDouble(DataEntity::getAirTemperature)
                .average()
                .orElse(-1);
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
        System.out.println(dataRepository.findAverageSoilMoistureBySerialNumber(serialNumber));
        return dataRepository.findAverageSoilMoistureBySerialNumber(serialNumber);
    }

    @Override
    public SetParameters setESP32ParametersByDeviceSerialNumberAndUsername(String username,
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
        return new SetParameters(s);
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
