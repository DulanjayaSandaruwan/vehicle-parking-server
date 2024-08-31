package com.project.vehicle_parking.model.park;

import com.project.vehicle_parking.dto.park.ParkDTO;
import com.project.vehicle_parking.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "park")
public class Park {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParkStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "park", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ParkingDetails> parkingDetails;

    public enum ParkStatus {
        PENDING,
        COMPLETED,
        CANCELLED
    }

    public static Park init(ParkDTO dto, User user) {
        Park park = new Park();
        park.setId(UUID.randomUUID().toString());
        park.setTotalAmount(dto.getTotalAmount());
        park.setStatus(ParkStatus.PENDING);
        park.setUser(user);
        return park;
    }
}
