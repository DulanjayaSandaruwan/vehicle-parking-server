package com.project.vehicle_parking.commons.exceptions.http;

import com.project.vehicle_parking.commons.exceptions.ExType;
import com.project.vehicle_parking.commons.exceptions.ExceptionType;

public class VehicleCategoryNotFoundException extends BaseException{

    public VehicleCategoryNotFoundException(ExceptionType exType , String message){
        super(exType, message);
    }

    public VehicleCategoryNotFoundException(String message){
        super(message);
        setType(ExType.NOT_FOUND);
    }
}
