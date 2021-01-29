package com.bootcamp.springclase2ttcasas.dto;

public class SqrMetersByRoomDTO {
    private String name;
    private double sqrMeters;

    public SqrMetersByRoomDTO(String name, double sqrMeters) {
        this.name = name;
        this.sqrMeters = sqrMeters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSqrMeters() {
        return sqrMeters;
    }

    public void setSqrMeters(double sqrMeters) {
        this.sqrMeters = sqrMeters;
    }
}
