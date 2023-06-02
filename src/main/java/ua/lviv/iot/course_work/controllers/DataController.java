package ua.lviv.iot.course_work.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.course_work.entities.DataEntity;
import ua.lviv.iot.course_work.entities.SensorData;
import ua.lviv.iot.course_work.exceptions.DataNotFoundException;
import ua.lviv.iot.course_work.exceptions.DatabaseIsEmptyException;
import ua.lviv.iot.course_work.services.DataService;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/data")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
public class DataController {
    private final DataService dataService;

    private int sendDataFrequency = -1;

    @GetMapping("/")
    public ResponseEntity<List<DataEntity>> getAllData(){
        return new ResponseEntity<>(
                dataService.getAllData(),
                HttpStatus.OK
        );
    }

    @GetMapping("/current-data")
    public ResponseEntity<?> getCurrentData(){
        try {
            LocalDateTime lastDateTime = dataService.getCurrentData().getDateTime();
            if(Duration.between(lastDateTime, LocalDateTime.now()).getSeconds() < sendDataFrequency + 7){
                return new ResponseEntity<>(
                        dataService.getCurrentData(),
                        HttpStatus.OK
                );
            }
            return new ResponseEntity<>(
                    new DataEntity(LocalDateTime.now(), -1, -1, -1),
                    HttpStatus.OK
            );
        } catch (DatabaseIsEmptyException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{dataId}")
    public ResponseEntity<?> getDataById(@PathVariable(name = "dataId") long dataId){
        try {
            return new ResponseEntity<>(
                    dataService.getDataById(dataId),
                    HttpStatus.OK
            );
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/between-dates")
    public ResponseEntity<List<DataEntity>> getDataBetweenDates(@RequestParam(name = "first-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime firstDateTime,
                                                                @RequestParam(name = "second-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime secondDateTime){
        return new ResponseEntity<>(
                dataService.getDataBetweenDates(firstDateTime, secondDateTime),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteAllData(){
        dataService.deleteAllData();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/sorted-by-date")
    public ResponseEntity<?> getAllDataSortedByDate(@RequestParam(name = "param") String param){
        if(Objects.equals(param, "ASC")){
            return new ResponseEntity<>(
                    dataService.getDataSortedByDateASC(),
                    HttpStatus.OK
            );
        } else if (Objects.equals(param, "DESC")) {
            return new ResponseEntity<>(
                    dataService.getDataSortedByDateDESC(),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                HttpStatus.BAD_REQUEST
        );
    }

    @GetMapping("/average-value")
    public ResponseEntity<?> getAverageValue(@RequestParam(name = "param") String param){
        if(Objects.equals(param, "airTemperature")){
            return new ResponseEntity<>(
                    dataService.getAverageAirTemperature(),
                    HttpStatus.OK
            );
        } else if (Objects.equals(param, "airHumidity")) {
            return new ResponseEntity<>(
                    dataService.getAverageAirHumidity(),
                    HttpStatus.OK
            );
        } else if (Objects.equals(param, "soilMoisture")) {
            return new ResponseEntity<>(
                    dataService.getAverageSoilMoisture(),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                HttpStatus.BAD_REQUEST
        );
    }



    // ==========================================================================

    @GetMapping("/set-esp32-parameters")
    public String setESP32Parameters(@RequestParam(name = "frequency") String frequency,
                                     @RequestParam(name = "irrigationThreshold") String irrigationThreshold){
        System.out.println(frequency + " " + irrigationThreshold);
        sendDataFrequency = Integer.parseInt(frequency);
        return dataService.setESP32ParametersByDeviceSerialNumberAndUsername(Integer.parseInt(frequency), Integer.parseInt(irrigationThreshold));
    }

    @PostMapping("/")
    public ResponseEntity<DataEntity> saveData(@RequestBody SensorData data){
        return new ResponseEntity<>(
                dataService.saveData(new DataEntity(LocalDateTime.now(),
                                    Double.parseDouble(data.getAirTemperature()),
                                    Double.parseDouble(data.getAirHumidity()),
                                    Double.parseDouble(data.getSoilMoisture()))),
                HttpStatus.OK
        );
    }


}
