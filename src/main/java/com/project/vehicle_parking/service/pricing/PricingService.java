package com.project.vehicle_parking.service.pricing;

import com.project.vehicle_parking.commons.Check;
import com.project.vehicle_parking.commons.exceptions.http.PricingNotFoundException;
import com.project.vehicle_parking.commons.exceptions.http.VehicleCategoryNotFoundException;
import com.project.vehicle_parking.dto.pricing.PricingDTO;
import com.project.vehicle_parking.model.pricing.Pricing;
import com.project.vehicle_parking.model.vehicleCategory.VehicleCategory;
import com.project.vehicle_parking.repository.pricing.PricingRepository;
import com.project.vehicle_parking.repository.vehicleCategory.VehicleCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class PricingService {

    @Autowired
    private PricingRepository pricingRepository;

    @Autowired
    private VehicleCategoryRepository vehicleCategoryRepository;

    public Pricing createPrice(PricingDTO pricingDTO) {
        log.info("create price");
        Optional<VehicleCategory> category = vehicleCategoryRepository.findById(pricingDTO.getVehicleCategory().getId());
        Check.throwIfEmpty(category, new VehicleCategoryNotFoundException("Category not found with Id : " + pricingDTO.getVehicleCategory().getId()));

        Optional<Pricing> existingPricing = pricingRepository.findByVehicleCategoryId(pricingDTO.getVehicleCategory().getId());

        Pricing pricing;
        if (existingPricing.isPresent()) {
            pricing = existingPricing.get();
            pricing.setPricePerHour(pricingDTO.getPricePerHour());
        } else {
            pricing = Pricing.initWithCategory(pricingDTO, category.get());
        }

        return pricingRepository.save(pricing);
    }

    public Pricing getPriceForVehicleCategory(String categoryId) {
        log.info("get price for vehicle category");
        Optional<Pricing> pricing = pricingRepository.findByVehicleCategoryId(categoryId);
        if (pricing.isPresent()) {
            return pricing.get();
        } else {
            throw new PricingNotFoundException("Pricing not for category id : " + categoryId);
        }
    }
}
