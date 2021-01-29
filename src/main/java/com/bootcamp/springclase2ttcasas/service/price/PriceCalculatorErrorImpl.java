package com.bootcamp.springclase2ttcasas.service.price;

public enum PriceCalculatorErrorImpl implements PriceCalculatorError {
    INVALID_METERS("Valor de metros invalido");

    private final String message;

    PriceCalculatorErrorImpl(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
