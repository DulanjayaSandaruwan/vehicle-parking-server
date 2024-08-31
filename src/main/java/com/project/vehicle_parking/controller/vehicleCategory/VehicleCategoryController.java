package com.project.vehicle_parking.controller.vehicleCategory;

import com.project.vehicle_parking.dto.vehicleCategory.VehicleCategoryDTO;
import com.project.vehicle_parking.dto.vehicleCategory.VehicleCategorySearchParamDTO;
import com.project.vehicle_parking.dto.vehicleCategory.VehicleCategoryUpdateDTO;
import com.project.vehicle_parking.model.vehicleCategory.VehicleCategory;
import com.project.vehicle_parking.service.vehicleCategory.VehicleCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/vehicle-category")
@RestController
@Slf4j
public class VehicleCategoryController {

    @Autowired
    private VehicleCategoryService vehicleCategoryService;

    @PostMapping("/create-category")
    public ResponseEntity<VehicleCategoryDTO> createCategory(@RequestBody VehicleCategoryDTO categoryDTO) {
        categoryDTO.validate();
        VehicleCategory category = vehicleCategoryService.addVehicleCategory(categoryDTO);
        VehicleCategoryDTO createCategoryDTO = VehicleCategoryDTO.init(category);
        return ResponseEntity.ok(createCategoryDTO);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<VehicleCategoryDTO> updateCategory(
            @RequestBody VehicleCategoryUpdateDTO updateDTO,
            @PathVariable String categoryId
    ) {
        updateDTO.validate();
        VehicleCategory category = vehicleCategoryService.updateVehicleCategory(updateDTO, categoryId);
        VehicleCategoryDTO createCategoryDTO = VehicleCategoryDTO.init(category);
        return ResponseEntity.ok(createCategoryDTO);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<VehicleCategoryDTO> getVehicleCategoryById(@PathVariable String categoryId) {
        VehicleCategory vehicleCategory = vehicleCategoryService.getVehicleCategoryById(categoryId);
        VehicleCategoryDTO dto = VehicleCategoryDTO.init(vehicleCategory);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<VehicleCategoryDTO> deleteVehicleCategory(@PathVariable String categoryId) {
        VehicleCategory vehicleCategory = vehicleCategoryService.deleteVehicleCategory(categoryId);
        return ResponseEntity.ok(VehicleCategoryDTO.init(vehicleCategory));
    }

    @GetMapping("/vehicle-categories")
    public ResponseEntity<Page<VehicleCategoryDTO>> getAllVehicleCategories(
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "status", required = false) VehicleCategory.VehicleCategoryStatus status,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        VehicleCategorySearchParamDTO searchParams = new VehicleCategorySearchParamDTO();
        searchParams.setCategoryName(categoryName);
        searchParams.setStatus(status);
        searchParams.setPage(page);
        searchParams.setSize(size);

        Page<VehicleCategory> vehicleCategoryPage = vehicleCategoryService.getAllVehicleCategories(searchParams);
        List<VehicleCategoryDTO> vehicleCategoryDTOs = vehicleCategoryPage.getContent().stream()
                .map(VehicleCategoryDTO::init)
                .collect(Collectors.toList());
        Page<VehicleCategoryDTO> vehicleCategoryDTOPage = new PageImpl<>(
                vehicleCategoryDTOs,
                PageRequest.of(page, size),
                vehicleCategoryPage.getTotalElements()
        );
        return ResponseEntity.ok(vehicleCategoryDTOPage);
    }
}
