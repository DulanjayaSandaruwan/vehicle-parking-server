package com.project.vehicle_parking.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.vehicle_parking.dto.user.UserDTO;
import com.project.vehicle_parking.model.park.Park;
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
@Entity(name = "user")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Park> parks;

    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        PENDING_VERIFICATION,
        TO_DELETE,
        DELETED
    }

    public enum UserRole {
        ADMIN,
        CUSTOMER
    }

    public static User init(UserDTO dto) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(dto.getRole());
        return user;
    }
}
