package com.project.vehicle_parking.dto.park;

import com.project.vehicle_parking.commons.BaseRequest;
import com.project.vehicle_parking.dto.vehicleCategory.VehicleCategoryDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Slf4j
public class ParkingDetailsDTO extends BaseRequest {
    private String id;

    @NotNull(message = "Parking time is mandatory")
    private LocalDateTime parkingTime;

    @NotNull(message = "Exit time is mandatory")
    private LocalDateTime exitTime;

    private VehicleCategoryDTO vehicleCategory;
}
