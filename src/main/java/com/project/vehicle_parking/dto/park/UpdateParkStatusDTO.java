package com.project.vehicle_parking.dto.park;

import com.project.vehicle_parking.commons.BaseRequest;
import com.project.vehicle_parking.model.park.Park;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class UpdateParkStatusDTO extends BaseRequest {
    private Park.ParkStatus status;
}
