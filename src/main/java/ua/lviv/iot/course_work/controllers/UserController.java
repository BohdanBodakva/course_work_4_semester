package ua.lviv.iot.course_work.controllers;

import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.course_work.entities.DeviceEntity;
import ua.lviv.iot.course_work.entities.SensorData;
import ua.lviv.iot.course_work.entities.UserEntity;
import ua.lviv.iot.course_work.exceptions.DatabaseTableIsEmptyException;
import ua.lviv.iot.course_work.exceptions.DeviceNotFoundException;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;
import ua.lviv.iot.course_work.exceptions.UsernameAlreadyExistsException;
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

    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsernameIfActive(@PathVariable(name = "username") String username){
        try {
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

    @PreAuthorize("#username == principal.username")
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

    @PreAuthorize("#username == principal.username")
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

    @PreAuthorize("#username == principal.username")
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

    @PreAuthorize("#username == principal.username")
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

    @PreAuthorize("#username == principal.username")
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

    @PreAuthorize("#username == principal.username")
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

    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/devices/{serialNumber}/data/between")
    public ResponseEntity<?> getDataBetweenDatesBySerialNumberAndUsername(@PathVariable(name = "username") String username,
                                                                          @PathVariable(name = "serialNumber") String serialNumber,
                                                                          @PathParam("startDate") String start,
                                                                          @PathParam("endDate") String end){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(start, dateTimeFormatter);

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

    @PreAuthorize("#username == principal.username")
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

    @PreAuthorize("#username == principal.username")
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

    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/devices/{serialNumber}/data?avg=temperature")
    public ResponseEntity<?> getAverageAirTemperatureByDeviceSerialNumberAndUsername(@PathVariable(name = "username") String username,
                                                                                     @PathVariable(name = "serialNumber") String serialNumber){
        try {
            return new ResponseEntity<>(
                    dataService.getAverageAirTemperatureByDeviceSerialNumberAndUsername(username, serialNumber),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | DeviceNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/devices/{serialNumber}/data?avg=humidity")
    public ResponseEntity<?> getAverageAirHumidityByDeviceSerialNumberAndUsername(@PathVariable(name = "username") String username,
                                                                                     @PathVariable(name = "serialNumber") String serialNumber){
        try {
            return new ResponseEntity<>(
                    dataService.getAverageAirHumidityByDeviceSerialNumberAndUsername(username, serialNumber),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | DeviceNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PreAuthorize("#username == principal.username")
    @GetMapping("/{username}/devices/{serialNumber}/data?avg=moisture")
    public ResponseEntity<?> getAverageSoilMoistureByDeviceSerialNumberAndUsername(@PathVariable(name = "username") String username,
                                                                                  @PathVariable(name = "serialNumber") String serialNumber){
        try {
            return new ResponseEntity<>(
                    dataService.getAverageSoilMoistureByDeviceSerialNumberAndUsername(username, serialNumber),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException | DeviceNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PreAuthorize("#username == principal.username")
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

    @PreAuthorize("#username == principal.username")
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

    @PreAuthorize("#username == principal.username")
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



}
