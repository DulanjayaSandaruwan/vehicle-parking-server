package com.project.vehicle_parking.repository.discount;

import com.project.vehicle_parking.model.discount.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, String> {

    @Query("SELECT d FROM discount d WHERE d.startDate <= :currentDateTime AND d.endDate >= :currentDateTime AND d.status = 'ACTIVE'")
    List<Discount> findActiveDiscounts(LocalDateTime currentDateTime);
}
