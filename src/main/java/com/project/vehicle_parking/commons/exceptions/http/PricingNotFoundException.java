package com.project.vehicle_parking.commons.exceptions.http;

import com.project.vehicle_parking.commons.exceptions.ExType;
import com.project.vehicle_parking.commons.exceptions.ExceptionType;

public class PricingNotFoundException extends BaseException {

    public PricingNotFoundException(ExceptionType exType , String message){
        super(exType, message);
    }

    public PricingNotFoundException(String message){
        super(message);
        setType(ExType.NOT_FOUND);
    }
}
