package ua.lviv.iot.course_work.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.course_work.exceptions.UserNotFoundException;
import ua.lviv.iot.course_work.services.ClearDataService;
import ua.lviv.iot.course_work.services.DeviceService;
import ua.lviv.iot.course_work.services.UserService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ClearDataService clearDataService;
    private final DeviceService deviceService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(
                userService.getAllUsers(),
                HttpStatus.OK
        );
    }

    @GetMapping("/users/{username}/devices")
    public ResponseEntity<?> getUserDevicesByUsername(@PathVariable(name = "username") String username){
        try {
            return new ResponseEntity<>(
                    deviceService.getAllDevicesByUserUsername(username),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/users/{username}/devices/{serialNumber}/data")
    public ResponseEntity<?> getDeviceDataByUsernameAndSerialNumber(@PathVariable(name = "username") String username,
                                                                    @PathVariable(name = "serialNumber") String serialNumber){
        try {
            return new ResponseEntity<>(
                    deviceService.getDeviceDataByUsernameAndSerialNumber(username, serialNumber),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }


    @PostMapping("/users/{username}/ban")
    public ResponseEntity<?> banUserByUsername(@PathVariable(name = "username") String username){
        try {
            userService.banUserByUsername(username);
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

    @PostMapping("/users/{username}/makeActive")
    public ResponseEntity<?> makeUserActiveByUsername(@PathVariable(name = "username") String username){
        try {
            userService.makeUserActiveByUsername(username);
            return new ResponseEntity<>(
                    userService.getAllUsers(),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

//    @PostMapping("/")
//    public ResponseEntity<?> save

    @DeleteMapping("/clear-data")
    public ResponseEntity<?> getAllClearData(){
        return new ResponseEntity<>(
                clearDataService.getAllClearData(),
                HttpStatus.OK
        );
    }

//
//    @DeleteMapping("/clear-data")
//    public ResponseEntity<?> deleteAllClearData(){
//        clearDataService.deleteAllClearData();
//        return new ResponseEntity<>(
//                HttpStatus.OK
//        );
//    }






}
