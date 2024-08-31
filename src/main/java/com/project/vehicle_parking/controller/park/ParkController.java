package com.project.vehicle_parking.controller.park;

import com.project.vehicle_parking.dto.park.ParkDTO;
import com.project.vehicle_parking.dto.park.ParkSearchParamDTO;
import com.project.vehicle_parking.dto.park.UpdateParkStatusDTO;
import com.project.vehicle_parking.model.park.Park;
import com.project.vehicle_parking.service.park.ParkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/park")
@RestController
@Slf4j
public class ParkController {

    @Autowired
    private ParkService parkService;

    @PostMapping("/add-park")
    public ResponseEntity<ParkDTO> createOrder(@RequestBody ParkDTO parkDTO) {
        parkDTO.validate();
        Park park = parkService.addPark(parkDTO);
        return ResponseEntity.ok(ParkDTO.init(park));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ParkDTO> getOrderById(@PathVariable String orderId) {
        Park park = parkService.getParkById(orderId);
        return ResponseEntity.ok(ParkDTO.init(park));
    }

    @PutMapping("/update-order-status/{id}")
    public ResponseEntity<ParkDTO> updateOrderStatus(
            @RequestBody UpdateParkStatusDTO updateParkStatusDTO,
            @PathVariable String id
    ) {
        updateParkStatusDTO.validate();
        Park park = parkService.updateParkStatus(updateParkStatusDTO, id);
        ParkDTO parkDTO = ParkDTO.init(park);
        return ResponseEntity.ok(parkDTO);
    }

    @GetMapping("/search-parks")
    public ResponseEntity<Page<ParkDTO>> searchParks(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "status", required = false) Park.ParkStatus status,
            @RequestParam(value = "parkingTime", required = false) LocalDateTime parkingTime,
            @RequestParam(value = "exitTime", required = false) LocalDateTime exitTime,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        ParkSearchParamDTO searchParams = new ParkSearchParamDTO();
        searchParams.setUserId(userId);
        searchParams.setStatus(status);
        searchParams.setParkingTime(parkingTime);
        searchParams.setExitTime(exitTime);
        searchParams.setPage(page);
        searchParams.setSize(size);

        Page<Park> parksPage = parkService.getAllParks(searchParams);
        List<ParkDTO> parkDTOs = parksPage.getContent().stream()
                .map(ParkDTO::init)
                .collect(Collectors.toList());
        Page<ParkDTO> parkDTOPage = new PageImpl<>(
                parkDTOs,
                PageRequest.of(page, size),
                parksPage.getTotalElements()
        );
        return ResponseEntity.ok(parkDTOPage);
    }
}
