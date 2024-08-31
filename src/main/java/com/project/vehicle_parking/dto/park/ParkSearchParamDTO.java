package com.project.vehicle_parking.dto.park;

import com.project.vehicle_parking.commons.BaseRequest;
import com.project.vehicle_parking.model.park.Park;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Setter
@Slf4j
public class ParkSearchParamDTO extends BaseRequest {
    private String userId;
    private Park.ParkStatus status;
    private LocalDateTime parkingTime;
    private LocalDateTime exitTime;
    private int page;
    private int size;
}
