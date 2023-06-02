package ua.lviv.iot.course_work.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "data_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "air_temperature")
    private double airTemperature;

    @Column(name = "airHumidity")
    private double airHumidity;

    @Column(name = "soil_moisture")
    private double soilMoisture;

    @ManyToOne
    @JoinColumn(name = "serial_number")
    private DeviceEntity device;

    public DataEntity(LocalDateTime dateTime, double airTemperature, double airHumidity, double soilMoisture, DeviceEntity device) {
        this.dateTime = dateTime;
        this.airTemperature = airTemperature;
        this.airHumidity = airHumidity;
        this.soilMoisture = soilMoisture;
        this.device = device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataEntity that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", airTemperature=" + airTemperature +
                ", airHumidity=" + airHumidity +
                ", soilMoisture=" + soilMoisture +
                '}';
    }
}
