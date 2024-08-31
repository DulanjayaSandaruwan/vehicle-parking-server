package com.project.vehicle_parking.dto.vehicleCategory;

import com.project.vehicle_parking.model.vehicleCategory.VehicleCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class VehicleCategorySearchParamDTO {
    private String categoryName;
    private VehicleCategory.VehicleCategoryStatus status;
    private int page;
    private int size;
}
