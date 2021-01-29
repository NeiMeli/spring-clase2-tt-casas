package com.bootcamp.springclase2ttcasas.service.sqrmeter;

public enum SqrMeterCalculatorErrorImpl implements SqrMeterCalculatorError {
    ROOM_WITHOUT_NAME ("Habitación sin nombre"),
    INVALID_METERS_VALUE ("Valor de metros inválido"),
    NO_ROOMS("No hay habitaciones");

    private final String message;

    SqrMeterCalculatorErrorImpl(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
