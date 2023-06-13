package ua.lviv.iot.course_work.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "device_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEntity {
    @Id
    @Column(name = "serial_number")
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "username")
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceEntity that)) return false;
        return Objects.equals(serialNumber, that.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }

    @Override
    public String toString() {
        return "DeviceEntity{" +
                "serialNumber='" + serialNumber + '\'' +
                ", user=" + user +
                '}';
    }
}
