package com.project.vehicle_parking.repository.vehicleCategory;

import com.project.vehicle_parking.model.vehicleCategory.VehicleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleCategoryRepository extends JpaRepository<VehicleCategory, String> {

    Optional<VehicleCategory> findByCategoryNameAndStatusNot(String name, VehicleCategory.VehicleCategoryStatus status);

    @Query("SELECT vc FROM vehicle_category vc WHERE (:categoryName IS NULL OR vc.categoryName LIKE %:categoryName%) " +
            "AND (:status IS NULL OR vc.status = :status)")
    Page<VehicleCategory> findAllVehicleCategories(
            @Param("categoryName") String categoryName,
            @Param("status") VehicleCategory.VehicleCategoryStatus status,
            Pageable pageable
    );
}
