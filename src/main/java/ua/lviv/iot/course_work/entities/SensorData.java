package ua.lviv.iot.course_work.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorData {
    private int airTemperature;
    private int airHumidity;
    private int soilMoisture;
}
