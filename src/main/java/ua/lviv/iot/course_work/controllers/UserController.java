package ua.lviv.iot.course_work.controllers;

import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.course_work.entities.DataEntity;
import ua.lviv.iot.course_work.entities.DeviceEntity;
import ua.lviv.iot.course_work.entities.SensorData;
import ua.lviv.iot.course_work.entities.UserEntity;
import ua.lviv.iot.course_work.exceptions.*;
import ua.lviv.iot.course_work.repositories.DataRepository;
import ua.lviv.iot.course_work.services.ClearDataService;
import ua.lviv.iot.course_work.services.DataService;
import ua.lviv.iot.course_work.services.DeviceService;
import ua.lviv.iot.course_work.services.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final DeviceService deviceService;
    private final DataService dataService;
    private final ClearDataService clearDataService;

//  ========================================== USERS ========================================================

//    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsernameIfActive(@PathVariable(name = "username") String username,
                                                       @AuthenticationPrincipal UserEntity user){
        try {
            System.out.println(user);
            return new ResponseEntity<>(
                    userService.getUserByUsernameIfActive(username),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }




    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserEntity user){
        return new ResponseEntity<>(
                userService.saveUser(user),
                HttpStatus.OK
        );
    }

//    @PreAuthorize("#username == principal.username")
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUserByUsername(@PathVariable(name = "username") String username,
                                                  @RequestBody UserEntity user) {
        try {
            return new ResponseEntity<>(
                    userService.updateUserByUsernameIfActive(username, user),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | UsernameAlreadyExistsException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//    @PreAuthorize("#username == principal.username")
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable(name = "username") String username) {
        try {
            userService.deleteUserByUsername(username);
            return new ResponseEntity<>(
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//  ========================================== DEVICES ========================================================

//    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/devices")
    public ResponseEntity<?> getUserDevicesByUsername(@PathVariable(name = "username") String username){
        try {
            return new ResponseEntity<>(
                    userService.getAllUserDevicesByUsername(username),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//    @PreAuthorize("#username == principal.username")
    @PostMapping("/{username}/devices")
    public ResponseEntity<?> saveDeviceByUsername(@PathVariable(name = "username") String username,
                                                  @RequestBody DeviceEntity device){
        try {
            return new ResponseEntity<>(
                    deviceService.saveDeviceByUserUsername(username, device),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//    @PreAuthorize("#username == principal.username")
    @DeleteMapping("/{username}/devices/{serialNumber}")
    public ResponseEntity<?> deleteDeviceSerialNumberAndUsername(@PathVariable(name = "username") String username,
                                                                 @PathVariable(name = "serialNumber") String serialNumber) {
        try {
            deviceService.deleteDeviceBySerialNumberAndUsername(username, serialNumber);
            return new ResponseEntity<>(
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//  ========================================== DATA ========================================================

//    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/devices/{serialNumber}/data")
    public ResponseEntity<?> getDataByUsernameAndDeviceSerialNumber(@PathVariable(name = "username") String username,
                                                                    @PathVariable(name = "serialNumber") String serialNumber){
        try {
            return new ResponseEntity<>(
                    dataService.getAllDataByDeviceSerialNumberAndUsername(username, serialNumber),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | DeviceNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/devices/{serialNumber}/data/between")
    public ResponseEntity<?> getDataBetweenDatesBySerialNumberAndUsername(@PathVariable(name = "username") String username,
                                                                          @PathVariable(name = "serialNumber") String serialNumber,
                                                                          @PathParam("startDate") String start,
                                                                          @PathParam("endDate") String end){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime startDateTime = LocalDateTime.parse(start, dateTimeFormatter);
            LocalDateTime endDateTime = LocalDateTime.parse(end, dateTimeFormatter);

        try {
            return new ResponseEntity<>(
                    dataService.getDataBetweenDatesBySerialNumberAndUsername(username, serialNumber, startDateTime, endDateTime),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | DeviceNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private final DataRepository dataRepository;

//    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/devices/{serialNumber}/data?sortedByDate=asc")
    public ResponseEntity<?> getDataByDeviceSerialNumberAndUsernameSortedByDateASC(@PathVariable(name = "username") String username,
                                                                                   @PathVariable(name = "serialNumber") String serialNumber){
        try {
            return new ResponseEntity<>(
                    dataService.getDataByDeviceSerialNumberAndUsernameSortedByDateASC(username, serialNumber),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | DeviceNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/devices/{serialNumber}/data?sortedByDate=desc")
    public ResponseEntity<?> getDataByDeviceSerialNumberAndUsernameSortedByDateDESC(@PathVariable(name = "username") String username,
                                                                                    @PathVariable(name = "serialNumber") String serialNumber){
        try {
            return new ResponseEntity<>(
                    dataService.getDataByDeviceSerialNumberAndUsernameSortedByDateDESC(username, serialNumber),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | DeviceNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/devices/{serialNumber}/avg-data")
    public ResponseEntity<?> getAverageAirTemperatureByDeviceSerialNumberAndUsername(@PathVariable(name = "username") String username,
                                                                                     @PathVariable(name = "serialNumber") String serialNumber,
                                                                                     @RequestParam("avg") String avgParameter) {
        try {
            switch (avgParameter) {
                case "airTemperature" -> {
                    return new ResponseEntity<>(
                            dataService.getAverageAirTemperatureByDeviceSerialNumberAndUsername(username, serialNumber),
                            HttpStatus.OK
                    );
                }
                case "airHumidity" -> {
                    return new ResponseEntity<>(
                            dataService.getAverageAirHumidityByDeviceSerialNumberAndUsername(username, serialNumber),
                            HttpStatus.OK
                    );
                }
                case "soilMoisture" -> {
                    return new ResponseEntity<>(
                            dataService.getAverageSoilMoistureByDeviceSerialNumberAndUsername(username, serialNumber),
                            HttpStatus.OK
                    );
                }
            }
            throw new IncorrectRequestParameterException("Request parameter '" + avgParameter + "' is incorrect");
        } catch (UserNotFoundException | DeviceNotFoundException | IncorrectRequestParameterException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
//        return new ResponseEntity<>(dataRepository.findDataEntitiesByDeviceSerialNumber(serialNumber)
//                .stream()
//                .mapToDouble(DataEntity::getAirTemperature)
//                .average()
//                .orElse(-1), HttpStatus.OK);
//    }

//    @PreAuthorize("#username == principal.username")
//    @GetMapping("/{username}/devices/{serialNumber}/data?avg=humidity")
//    public ResponseEntity<?> getAverageAirHumidityByDeviceSerialNumberAndUsername(@PathVariable(name = "username") String username,
//                                                                                     @PathVariable(name = "serialNumber") String serialNumber){
//        try {
//            return new ResponseEntity<>(
//                    dataService.getAverageAirHumidityByDeviceSerialNumberAndUsername(username, serialNumber),
//                    HttpStatus.OK
//            );
//        } catch (UserNotFoundException | DeviceNotFoundException e) {
//            return new ResponseEntity<>(
//                    e.getMessage(),
//                    HttpStatus.BAD_REQUEST
//            );
//        }
//    }
//
////    @PreAuthorize("#username == principal.username")
//    @GetMapping("/{username}/devices/{serialNumber}/data/average-values")
//    public ResponseEntity<?> getAverageSoilMoistureByDeviceSerialNumberAndUsername(@PathVariable(name = "username") String username,
//                                                                                   @PathVariable(name = "serialNumber") String serialNumber){
//        try {
//            System.out.println(dataService.getAverageSoilMoistureByDeviceSerialNumberAndUsername(username, serialNumber));
//            return new ResponseEntity<>(
//                    dataService.getAverageSoilMoistureByDeviceSerialNumberAndUsername(username, serialNumber),
//                    HttpStatus.OK
//            );
//        } catch (UserNotFoundException | DeviceNotFoundException e) {
//            return new ResponseEntity<>(
//                    e.getMessage(),
//                    HttpStatus.BAD_REQUEST
//            );
//        }
//    }

//    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/devices/{serialNumber}/current-data")
    public ResponseEntity<?> getCurrentDataByUsernameAndDeviceSerialNumber(@PathVariable(name = "username") String username,
                                                                           @PathVariable(name = "serialNumber") String serialNumber){
        try {
            return new ResponseEntity<>(
                    dataService.getCurrentDataByDeviceSerialNumberAndUsername(username, serialNumber),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | DatabaseTableIsEmptyException | DeviceNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//    @PreAuthorize("#username == principal.username")
    @PostMapping("/{username}/devices/{serialNumber}/data")
    public ResponseEntity<?> saveDataByUsernameAndDeviceSerialNumber(@PathVariable(name = "username") String username,
                                                                     @PathVariable(name = "serialNumber") String serialNumber,
                                                                     @RequestBody SensorData data){
        try {
            return new ResponseEntity<>(
                    dataService.saveDataBySerialNumberAndUsername(username, serialNumber, data),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | DeviceNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//    @PreAuthorize("#username == principal.username")
    @DeleteMapping("/{username}/devices/{serialNumber}/data")
    public ResponseEntity<?> deleteDataByUsernameAndDeviceSerialNumber(@PathVariable(name = "username") String username,
                                                                       @PathVariable(name = "serialNumber") String serialNumber) {
        try {
            dataService.deleteDataByDeviceSerialNumberAndUsername(username, serialNumber);
            clearDataService.saveClearDataBySerialNumberAndUsername(username, serialNumber);
            return new ResponseEntity<>(
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | DeviceNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//  ==================== ESP 32 ============================

    @GetMapping("/{username}/devices/{serialNumber}/set-esp32-parameters")
    public ResponseEntity<?> setESP32Parameters(@PathVariable(name = "username") String username,
                                                @PathVariable(name = "serialNumber") String serialNumber,
                                                @PathParam("frequency") int frequency,
                                                @PathParam("irrigationThreshold") int irrigationThreshold){
        try {
            return new ResponseEntity<>(
                    dataService.setESP32ParametersByDeviceSerialNumberAndUsername(username, serialNumber, frequency, irrigationThreshold),
                    HttpStatus.OK
            );
        } catch (DeviceNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }

    }

    @PostMapping("/{username}/devices/{serialNumber}/post-esp32-values")
    public ResponseEntity<?> postESP32SensorDataByDeviceSerialNumberAndUsername(@PathVariable(name = "username") String username,
                                                                                 @PathVariable(name = "serialNumber") String serialNumber,
                                                                                 @RequestBody SensorData data){
        try {
            return new ResponseEntity<>(
                    dataService.postESP32SensorDataByDeviceSerialNumberAndUsername(username, serialNumber, data),
                    HttpStatus.OK
            );
        }  catch (DeviceNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }

    }




}
