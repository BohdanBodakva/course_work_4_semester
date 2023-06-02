package ua.lviv.iot.course_work.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.course_work.entities.ClearDataEntity;
import ua.lviv.iot.course_work.exceptions.ClearDataNotFoundException;
import ua.lviv.iot.course_work.services.ClearDataService;

import java.util.List;

@RestController
@RequestMapping("/api/clear-data")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class ClearDataController {
    private final ClearDataService clearDataService;

    @GetMapping("/")
    public ResponseEntity<List<ClearDataEntity>> getAllClearData(){
        return new ResponseEntity<>(
                clearDataService.getAllClearData(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{clearDataId}")
    public ResponseEntity<?> getClearDataById(@PathVariable(name = "clearDataId") long clearDataId){
        try {
            return new ResponseEntity<>(
                    clearDataService.getClearDataById(clearDataId),
                    HttpStatus.OK
            );
        } catch (ClearDataNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    
}
