package ua.lviv.iot.course_work.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "clear_data_dates_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClearDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "serial_number")
    private DeviceEntity device;

    public ClearDataEntity(LocalDateTime dateTime, DeviceEntity device) {
        this.dateTime = dateTime;
        this.device = device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClearDataEntity that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ClearDataEntity{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                '}';
    }
}
