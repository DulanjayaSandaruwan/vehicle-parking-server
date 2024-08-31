package com.project.vehicle_parking.service.discount;

import com.project.vehicle_parking.commons.Check;
import com.project.vehicle_parking.commons.exceptions.http.BadRequestException;
import com.project.vehicle_parking.commons.exceptions.http.DiscountNotFoundException;
import com.project.vehicle_parking.dto.discount.DiscountDTO;
import com.project.vehicle_parking.dto.discount.DiscountUpdateDTO;
import com.project.vehicle_parking.model.discount.Discount;
import com.project.vehicle_parking.repository.discount.DiscountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    public Discount addDiscount(DiscountDTO discountDTO) {
        log.info("create discount");
        Discount discount = Discount.init(discountDTO);
        discount = discountRepository.save(discount);
        return discount;
    }

    public Discount getDiscountById(String discountId) {
        log.info("Get discount by id = {}", discountId);
        Optional<Discount> discountOptional = discountRepository.findById(discountId);
        Check.throwIfEmpty(discountOptional, new DiscountNotFoundException("Discount not found with Id : " + discountId));
        Discount discount = discountOptional.get();
        return discount;
    }

    public Discount updateDiscount(DiscountUpdateDTO updateDTO, String discountId) {
        log.info("updated discount id {}", discountId);
        Discount discount = this.getDiscountById(discountId);
        discount.setDescription(updateDTO.getDescription());
        discount.setPercentage(updateDTO.getPercentage());
        discount.setStartDate(updateDTO.getStartDate());
        discount.setEndDate(updateDTO.getEndDate());
        discount = discountRepository.save(discount);
        return discount;
    }

    public List<Discount> getCurrentActiveDiscounts() {
        LocalDateTime now = LocalDateTime.now();
        return discountRepository.findActiveDiscounts(now);
    }

    public Discount deleteDiscount(String discountId) {
        log.info("delete. discount id={}", discountId);
        Discount discount = getDiscountById(discountId);
        if (discount.getStatus() == Discount.DiscountStatus.INACTIVE) {
            throw new BadRequestException(discountId + " is already deleted");
        } else {
            discount.setStatus(Discount.DiscountStatus.INACTIVE);
        }
        return discountRepository.save(discount);
    }
}
