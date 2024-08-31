package com.project.vehicle_parking.repository.park;

import com.project.vehicle_parking.model.park.ParkingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkDetailsRepository extends JpaRepository<ParkingDetails, String> {
}
