package com.project.vehicle_parking.commons.exceptions.http;

import com.project.vehicle_parking.commons.exceptions.ExType;
import com.project.vehicle_parking.commons.exceptions.ExceptionType;

public class ParkNotFoundException extends BaseException {

    public ParkNotFoundException(ExceptionType exType , String message){
        super(exType, message);
    }

    public ParkNotFoundException(String message){
        super(message);
        setType(ExType.NOT_FOUND);
    }
}
