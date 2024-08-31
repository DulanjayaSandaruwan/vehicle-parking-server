package com.project.vehicle_parking.controller.discount;

import com.project.vehicle_parking.dto.discount.DiscountDTO;
import com.project.vehicle_parking.dto.discount.DiscountUpdateDTO;
import com.project.vehicle_parking.model.discount.Discount;
import com.project.vehicle_parking.service.discount.DiscountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/discount")
@RestController
@Slf4j
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @PostMapping("/add-discount")
    public ResponseEntity<DiscountDTO> addDiscount(@RequestBody DiscountDTO discountDTO) {
        discountDTO.validate();
        Discount discount = discountService.addDiscount(discountDTO);
        DiscountDTO dto = DiscountDTO.init(discount);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update/{discountId}")
    public ResponseEntity<DiscountDTO> updateCategory(
            @RequestBody DiscountUpdateDTO updateDTO,
            @PathVariable String discountId
    ) {
        updateDTO.validate();
        Discount discount = discountService.updateDiscount(updateDTO, discountId);
        DiscountDTO discountDTO = DiscountDTO.init(discount);
        return ResponseEntity.ok(discountDTO);
    }

    @GetMapping("/{discountId}")
    public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable String discountId) {
        Discount discount = discountService.getDiscountById(discountId);
        DiscountDTO dto = DiscountDTO.init(discount);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{discountId}")
    public ResponseEntity<DiscountDTO> deleteVehicleCategory(@PathVariable String discountId) {
        Discount discount = discountService.deleteDiscount(discountId);
        return ResponseEntity.ok(DiscountDTO.init(discount));
    }

    @GetMapping("/current-active-discounts")
    public ResponseEntity<List<DiscountDTO>> getCurrentActiveDiscounts() {
        List<Discount> discounts = discountService.getCurrentActiveDiscounts();
        List<DiscountDTO> discountDTOs = discounts.stream()
                .map(DiscountDTO::init)
                .collect(Collectors.toList());
        return ResponseEntity.ok(discountDTOs);
    }
}
