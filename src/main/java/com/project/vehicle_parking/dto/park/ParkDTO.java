package com.project.vehicle_parking.dto.park;

import com.project.vehicle_parking.commons.BaseRequest;
import com.project.vehicle_parking.dto.user.UserDTO;
import com.project.vehicle_parking.dto.vehicleCategory.VehicleCategoryDTO;
import com.project.vehicle_parking.model.park.Park;
import com.project.vehicle_parking.model.park.ParkingDetails;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Slf4j
public class ParkDTO extends BaseRequest {
    private String id;

    @NotNull(message = "Total amount is mandatory")
    private Double totalAmount;

    private Park.ParkStatus status;

    @NotNull(message = "User is mandatory")
    private UserDTO user;

    private Set<ParkingDetailsDTO> parkingDetails;

    public static ParkDTO init(Park park) {
        ParkDTO parkDTO = new ParkDTO();
        parkDTO.setId(park.getId());
        parkDTO.setTotalAmount(park.getTotalAmount());
        parkDTO.setStatus(park.getStatus());
        parkDTO.setUser(UserDTO.init(park.getUser()));

        Set<ParkingDetailsDTO> parkingDetailsDTOS = new HashSet<>();
        for (ParkingDetails detail : park.getParkingDetails()) {
            ParkingDetailsDTO detailDTO = new ParkingDetailsDTO();
            detailDTO.setId(detail.getId());
            detailDTO.setParkingTime(detail.getParkingTime());
            detailDTO.setExitTime(detail.getExitTime());
            detailDTO.setVehicleCategory(VehicleCategoryDTO.init(detail.getVehicleCategory()));
            parkingDetailsDTOS.add(detailDTO);
        }
        parkDTO.setParkingDetails(parkingDetailsDTOS);
        return parkDTO;
    }
}
