package com.project.vehicle_parking.model.pricing;

import com.project.vehicle_parking.dto.pricing.PricingDTO;
import com.project.vehicle_parking.model.user.User;
import com.project.vehicle_parking.model.vehicleCategory.VehicleCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pricing")
public class Pricing {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "price_per_hour", nullable = false)
    private Double pricePerHour;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PricingStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToOne
    @JoinColumn(name = "vehicle_category_id")
    private VehicleCategory vehicleCategory;

    public enum PricingStatus {
        ACTIVE,
        INACTIVE
    }

    public static Pricing init(PricingDTO pricingDTO) {
        Pricing pricing = new Pricing();
        pricing.setId(UUID.randomUUID().toString());
        pricing.setPricePerHour(pricingDTO.getPricePerHour());
        pricing.setStatus(PricingStatus.ACTIVE);
        return pricing;
    }

    public static Pricing initWithCategory(PricingDTO dto, VehicleCategory category) {
        Pricing pricing = Pricing.init(dto);
        pricing.setVehicleCategory(category);
        return pricing;
    }
}
