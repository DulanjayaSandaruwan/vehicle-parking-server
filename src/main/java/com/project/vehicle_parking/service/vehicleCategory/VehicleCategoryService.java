package com.project.vehicle_parking.service.vehicleCategory;

import com.project.vehicle_parking.commons.Check;
import com.project.vehicle_parking.commons.exceptions.http.BadRequestException;
import com.project.vehicle_parking.commons.exceptions.http.VehicleCategoryNotFoundException;
import com.project.vehicle_parking.commons.exceptions.vehicleCategory.VehicleCategoryExType;
import com.project.vehicle_parking.dto.vehicleCategory.VehicleCategoryDTO;
import com.project.vehicle_parking.dto.vehicleCategory.VehicleCategorySearchParamDTO;
import com.project.vehicle_parking.dto.vehicleCategory.VehicleCategoryUpdateDTO;
import com.project.vehicle_parking.model.vehicleCategory.VehicleCategory;
import com.project.vehicle_parking.repository.vehicleCategory.VehicleCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class VehicleCategoryService {

    @Autowired
    private VehicleCategoryRepository vehicleCategoryRepository;

    public VehicleCategory addVehicleCategory(VehicleCategoryDTO dto) {
        log.info("create vehicle category");
        VehicleCategory vehicleCategory = VehicleCategory.init(dto);
        if (vehicleCategoryRepository.findByCategoryNameAndStatusNot(dto.getCategoryName() , VehicleCategory.VehicleCategoryStatus.INACTIVE).isPresent()) {
            throw new BadRequestException("category already exist", VehicleCategoryExType.CATEGORY_ALREADY_EXIST);
        }
        vehicleCategory = vehicleCategoryRepository.save(vehicleCategory);
        return vehicleCategory;
    }

    public VehicleCategory getVehicleCategoryById(String categoryId) {
        log.info("Get category by id = {}", categoryId);
        Optional<VehicleCategory> vehicleCategoryOptional = vehicleCategoryRepository.findById(categoryId);
        Check.throwIfEmpty(vehicleCategoryOptional, new VehicleCategoryNotFoundException("Vehicle category not found with Id : " + categoryId));
        VehicleCategory vehicleCategory = vehicleCategoryOptional.get();
        return vehicleCategory;
    }

    public VehicleCategory updateVehicleCategory(VehicleCategoryUpdateDTO updateDTO, String categoryId) {
        log.info("updated category id {}", categoryId);
        VehicleCategory vehicleCategory = this.getVehicleCategoryById(categoryId);
        vehicleCategory.setCategoryName(updateDTO.getCategoryName());
        vehicleCategory.setDescription(updateDTO.getDescription());
        vehicleCategory = vehicleCategoryRepository.save(vehicleCategory);
        return vehicleCategory;
    }

    public VehicleCategory deleteVehicleCategory(String categoryId) {
        log.info("delete. category id={}", categoryId);
        VehicleCategory vehicleCategory = getVehicleCategoryById(categoryId);
        if (vehicleCategory.getStatus() == VehicleCategory.VehicleCategoryStatus.INACTIVE) {
            throw new BadRequestException(categoryId + " is already deleted");
        } else {
            vehicleCategory.setStatus(VehicleCategory.VehicleCategoryStatus.INACTIVE);
        }
        return vehicleCategoryRepository.save(vehicleCategory);
    }

    public Page<VehicleCategory> getAllVehicleCategories(VehicleCategorySearchParamDTO searchParams) {
        log.info("Get all vehicle categories");
        Pageable pageable = PageRequest.of(searchParams.getPage(), searchParams.getSize());
        return vehicleCategoryRepository.findAllVehicleCategories(
                searchParams.getCategoryName(),
                searchParams.getStatus(),
                pageable
        );
    }
}
