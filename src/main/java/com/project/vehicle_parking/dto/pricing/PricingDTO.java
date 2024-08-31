package com.project.vehicle_parking.dto.pricing;

import com.project.vehicle_parking.commons.BaseRequest;
import com.project.vehicle_parking.dto.vehicleCategory.VehicleCategoryDTO;
import com.project.vehicle_parking.model.pricing.Pricing;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.DecimalMin;

@Getter
@Setter
@Slf4j
public class PricingDTO extends BaseRequest {
    private String id;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private Double pricePerHour;

    private Pricing.PricingStatus status;

    private VehicleCategoryDTO vehicleCategory;

    public static PricingDTO init(Pricing pricing) {
        PricingDTO dto = new PricingDTO();
        dto.setId(pricing.getId());
        dto.setPricePerHour(pricing.getPricePerHour());
        dto.setStatus(pricing.getStatus());
        return dto;
    }

    public static PricingDTO initWithCategory(Pricing pricing) {
        PricingDTO pricingDTO = PricingDTO.init(pricing);
        pricingDTO.setVehicleCategory(VehicleCategoryDTO.init(pricing.getVehicleCategory()));
        return pricingDTO;
    }
}
