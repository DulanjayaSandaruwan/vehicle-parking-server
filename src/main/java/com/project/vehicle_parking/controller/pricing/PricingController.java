package com.project.vehicle_parking.controller.pricing;

import com.project.vehicle_parking.dto.pricing.PricingDTO;
import com.project.vehicle_parking.model.pricing.Pricing;
import com.project.vehicle_parking.service.pricing.PricingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/pricing")
@RestController
@Slf4j
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @PostMapping("/add-price")
    public ResponseEntity<PricingDTO> addDiscount(@RequestBody PricingDTO pricingDTO) {
        pricingDTO.validate();
        Pricing price = pricingService.createPrice(pricingDTO);
        PricingDTO dto = PricingDTO.initWithCategory(price);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/price/{categoryId}")
    public ResponseEntity<PricingDTO> getPriceForVehicleCategory(@PathVariable String categoryId) {
        Pricing price = pricingService.getPriceForVehicleCategory(categoryId);
        PricingDTO dto = PricingDTO.initWithCategory(price);
        return ResponseEntity.ok(dto);
    }
}
