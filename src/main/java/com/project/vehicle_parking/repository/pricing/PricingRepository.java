package com.project.vehicle_parking.repository.pricing;

import com.project.vehicle_parking.model.pricing.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PricingRepository extends JpaRepository<Pricing, String> {

    Optional<Pricing> findByVehicleCategoryId(String vehicleCategoryId);
}
