package com.project.vehicle_parking.service.park;

import com.project.vehicle_parking.commons.Check;
import com.project.vehicle_parking.commons.exceptions.http.BadRequestException;
import com.project.vehicle_parking.commons.exceptions.http.ParkNotFoundException;
import com.project.vehicle_parking.dto.park.ParkDTO;
import com.project.vehicle_parking.dto.park.ParkSearchParamDTO;
import com.project.vehicle_parking.dto.park.ParkingDetailsDTO;
import com.project.vehicle_parking.dto.park.UpdateParkStatusDTO;
import com.project.vehicle_parking.model.park.Park;
import com.project.vehicle_parking.model.park.ParkingDetails;
import com.project.vehicle_parking.model.vehicleCategory.VehicleCategory;
import com.project.vehicle_parking.repository.park.ParkDetailsRepository;
import com.project.vehicle_parking.repository.park.ParkRepository;
import com.project.vehicle_parking.security.Session;
import com.project.vehicle_parking.service.vehicleCategory.VehicleCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class ParkService {

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private ParkDetailsRepository parkDetailsRepository;

    @Autowired
    private VehicleCategoryService vehicleCategoryService;

    public Park addPark(ParkDTO parkDTO) {
        log.info("Park vehicle by user {}", Session.getUser().getId());
        Park park = Park.init(parkDTO, Session.getUser());

        Set<ParkingDetails> parkingDetails = new HashSet<>();
        for (ParkingDetailsDTO detailDTO : parkDTO.getParkingDetails()) {
            ParkingDetails details = new ParkingDetails();
            details.setId(UUID.randomUUID().toString());
            details.setParkingTime(detailDTO.getParkingTime());
            details.setExitTime(detailDTO.getExitTime());
            details.setPark(park);

            if (detailDTO.getVehicleCategory() != null) {
                VehicleCategory vehicleCategory = vehicleCategoryService.getVehicleCategoryById(detailDTO.getVehicleCategory().getId());
                details.setVehicleCategory(vehicleCategory);
            }

            if (details.getVehicleCategory() == null) {
                throw new IllegalArgumentException("Vehicle category must be provided for park details.");
            }
            parkingDetails.add(details);
        }
        park.setParkingDetails(parkingDetails);

        Park savedPark = parkRepository.save(park);
        parkDetailsRepository.saveAll(park.getParkingDetails());

        return savedPark;
    }

    public Park getParkById(String parkId) {
        log.info("Get park by id = {}", parkId);
        Optional<Park> parkOptional = parkRepository.findById(parkId);
        Check.throwIfEmpty(parkOptional, new ParkNotFoundException("Park not found with Id : " + parkId));
        return parkOptional.get();
    }

    public Park updateParkStatus(UpdateParkStatusDTO updateParkStatusDTO, String id) {
        log.info("update park status orderId {}", id);
        Park park = this.getParkById(id);
        if (park.getStatus().equals(updateParkStatusDTO.getStatus())) {
            throw new BadRequestException(id + "'s status is already exist");
        } else {
            park.setStatus(updateParkStatusDTO.getStatus());
        }
        return parkRepository.save(park);
    }

    public Page<Park> getAllParks(ParkSearchParamDTO searchParams) {
        log.info("Get all parks with search parameters");
        Pageable pageable = PageRequest.of(searchParams.getPage(), searchParams.getSize());
        return parkRepository.searchParks(
                searchParams.getUserId(),
                searchParams.getStatus(),
                searchParams.getParkingTime(),
                searchParams.getExitTime(),
                pageable
        );
    }
}
