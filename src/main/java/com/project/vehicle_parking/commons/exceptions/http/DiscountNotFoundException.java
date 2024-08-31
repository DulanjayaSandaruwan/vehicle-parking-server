package com.project.vehicle_parking.commons.exceptions.http;

import com.project.vehicle_parking.commons.exceptions.ExType;
import com.project.vehicle_parking.commons.exceptions.ExceptionType;

public class DiscountNotFoundException extends BaseException {

    public DiscountNotFoundException(ExceptionType exType , String message){
        super(exType, message);
    }

    public DiscountNotFoundException(String message){
        super(message);
        setType(ExType.NOT_FOUND);
    }
}
