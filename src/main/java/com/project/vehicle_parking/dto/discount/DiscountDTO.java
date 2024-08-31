package com.project.vehicle_parking.dto.discount;

import com.project.vehicle_parking.commons.BaseRequest;
import com.project.vehicle_parking.model.discount.Discount;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Slf4j
public class DiscountDTO extends BaseRequest {
    private String id;

    @Size(max = 100, min = 5, message = "Description length should be more than 5 and less than 100")
    @NotBlank(message = "Description is mandatory")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Discount percentage must be greater than or equal to 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Discount percentage must be less than or equal to 100")
    private Double percentage;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Discount.DiscountStatus status;

    public static DiscountDTO init(Discount discount) {
        DiscountDTO dto = new DiscountDTO();
        dto.setId(discount.getId());
        dto.setDescription(discount.getDescription());
        dto.setPercentage(discount.getPercentage());
        dto.setStartDate(discount.getStartDate());
        dto.setEndDate(discount.getEndDate());
        dto.setStatus(discount.getStatus());
        return dto;
    }
}
