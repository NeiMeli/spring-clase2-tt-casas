package com.bootcamp.springclase2ttcasas.service.sqrmeter;

import com.bootcamp.springclase2ttcasas.service.BadRequestException;

public class SqrMeterCalculatorServiceException extends BadRequestException {
    public SqrMeterCalculatorServiceException(SqrMeterCalculatorError error) {
        super(error.getMessage());
    }
}
