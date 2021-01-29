package com.bootcamp.springclase2ttcasas.service.sqrmeter;

public enum SqrMeterCalculatorErrorForTest implements SqrMeterCalculatorError {
    SQR_METER_CALCULATOR_ERROR;

    @Override
    public String getMessage() {
        return "sqr-meter-calculator-error";
    }
}
