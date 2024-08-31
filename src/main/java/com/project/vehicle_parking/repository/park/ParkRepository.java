package com.project.vehicle_parking.repository.park;

import com.project.vehicle_parking.model.park.Park;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ParkRepository extends JpaRepository<Park, String> {

    @Query("SELECT p FROM park p JOIN p.parkingDetails pd WHERE " +
            "(:userId IS NULL OR p.user.id = :userId) AND " +
            "(:status IS NULL OR p.status = :status) AND " +
            "(:startTime IS NULL OR pd.parkingTime >= :startTime) AND " +
            "(:endTime IS NULL OR pd.exitTime <= :endTime)")
    Page<Park> searchParks(
            @Param("userId") String userId,
            @Param("status") Park.ParkStatus status,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable
    );
}
