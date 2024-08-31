package com.project.vehicle_parking.model.park;

import com.project.vehicle_parking.model.vehicleCategory.VehicleCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "parking_details")
public class ParkingDetails {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "parking_time", nullable = false)
    private LocalDateTime parkingTime;

    @Column(name = "exit_time", nullable = false)
    private LocalDateTime exitTime;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "park_id")
    private Park park;

    @ManyToOne
    @JoinColumn(name = "vehicle_category_id")
    private VehicleCategory vehicleCategory;
}
