package com.bootcamp.springclase2ttcasas.dto;

import java.util.List;

public class ResponseDTO {
    private double totalSquareMeters;
    private double price;
    private String biggestRoom;
    private List<SqrMetersByRoomDTO> sqrMetersByRoom;

    public ResponseDTO(double totalSquareMeters, double price, String biggestRoom, List<SqrMetersByRoomDTO> sqrMetersByRoom) {
        this.totalSquareMeters = totalSquareMeters;
        this.price = price;
        this.biggestRoom = biggestRoom;
        this.sqrMetersByRoom = sqrMetersByRoom;
    }

    public double getTotalSquareMeters() {
        return totalSquareMeters;
    }

    public void setTotalSquareMeters(double totalSquareMeters) {
        this.totalSquareMeters = totalSquareMeters;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBiggestRoom() {
        return biggestRoom;
    }

    public void setBiggestRoom(String biggestRoom) {
        this.biggestRoom = biggestRoom;
    }

    public List<SqrMetersByRoomDTO> getSqrMetersByRoom() {
        return sqrMetersByRoom;
    }

    public void setSqrMetersByRoom(List<SqrMetersByRoomDTO> sqrMetersByRoom) {
        this.sqrMetersByRoom = sqrMetersByRoom;
    }
}
