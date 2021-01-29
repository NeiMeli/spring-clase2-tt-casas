package com.bootcamp.springclase2ttcasas.service.price;

import com.bootcamp.springclase2ttcasas.service.BadRequestException;

public class PriceCalculatorServiceException extends BadRequestException {
    public PriceCalculatorServiceException(PriceCalculatorError error) {
        super(error.getMessage());
    }
}
