package com.project.vehicle_parking.model.discount;

import com.project.vehicle_parking.dto.discount.DiscountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "discount")
public class Discount {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "percentage", nullable = false)
    private Double percentage;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public enum DiscountStatus {
        ACTIVE,
        INACTIVE
    }

    public static Discount init(DiscountDTO dto) {
        Discount discount = new Discount();
        discount.setId(UUID.randomUUID().toString());
        discount.setDescription(dto.getDescription());
        discount.setPercentage(dto.getPercentage());
        discount.setStartDate(dto.getStartDate());
        discount.setEndDate(dto.getEndDate());
        discount.setStatus(DiscountStatus.ACTIVE);
        return discount;
    }
}
