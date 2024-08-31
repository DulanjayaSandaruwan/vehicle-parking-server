package com.project.vehicle_parking.dto.vehicleCategory;

import com.project.vehicle_parking.commons.BaseRequest;
import com.project.vehicle_parking.model.vehicleCategory.VehicleCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Slf4j
public class VehicleCategoryUpdateDTO extends BaseRequest {
    @Size(max = 40, min = 3, message = "Category name length should be more than 3 and less than 40")
    @NotBlank(message = "Category name is mandatory")
    private String categoryName;

    @Size(max = 100, min = 5, message = "Description length should be more than 5 and less than 100")
    @NotBlank(message = "Description is mandatory")
    private String description;

    private VehicleCategory.VehicleCategoryStatus status;
}
