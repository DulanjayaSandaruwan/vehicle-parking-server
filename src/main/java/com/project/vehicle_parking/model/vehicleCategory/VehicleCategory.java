package com.project.vehicle_parking.model.vehicleCategory;

import com.project.vehicle_parking.dto.vehicleCategory.VehicleCategoryDTO;
import com.project.vehicle_parking.model.park.ParkingDetails;
import com.project.vehicle_parking.model.pricing.Pricing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "vehicle_category")
public class VehicleCategory {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleCategoryStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "vehicleCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParkingDetails> parkingDetails;

    @OneToOne(mappedBy = "vehicleCategory")
    private Pricing pricing;

    public enum VehicleCategoryStatus {
        ACTIVE,
        INACTIVE
    }

    public static VehicleCategory init(VehicleCategoryDTO dto) {
        VehicleCategory vehicleCategory = new VehicleCategory();
        vehicleCategory.setId(UUID.randomUUID().toString());
        vehicleCategory.setCategoryName(dto.getCategoryName());
        vehicleCategory.setDescription(dto.getDescription());
        vehicleCategory.setStatus(VehicleCategoryStatus.ACTIVE);
        return vehicleCategory;
    }
}
