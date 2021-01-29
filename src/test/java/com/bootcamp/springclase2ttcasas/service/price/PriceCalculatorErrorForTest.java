package com.bootcamp.springclase2ttcasas.service.price;

public enum PriceCalculatorErrorForTest implements PriceCalculatorError {
    PRICE_CALCULATOR_ERROR;

    @Override
    public String getMessage() {
        return "price-calculator-error";
    }
}
