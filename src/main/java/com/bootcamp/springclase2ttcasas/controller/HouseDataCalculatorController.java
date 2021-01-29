package com.bootcamp.springclase2ttcasas.controller;

import com.bootcamp.springclase2ttcasas.dto.HouseDTO;
import com.bootcamp.springclase2ttcasas.dto.ResponseDTO;
import com.bootcamp.springclase2ttcasas.dto.SqrMetersByRoomDTO;
import com.bootcamp.springclase2ttcasas.service.*;
import com.bootcamp.springclase2ttcasas.service.price.PriceCalculatorService;
import com.bootcamp.springclase2ttcasas.service.sqrmeter.SqrMeterCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class HouseDataCalculatorController {
    @Autowired
    SqrMeterCalculatorService sqrMeterCalculatorService;

    @Autowired
    PriceCalculatorService priceCalculatorService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ResponseDTO> calculateHouseData(@RequestBody HouseDTO house) {
        try {
            List<SqrMetersByRoomDTO> sqrMetersByRoomInDescendingOrder = sqrMeterCalculatorService.getSqrMetersByRoomInDescendingOrder(house.getRooms());
            final double totalSqrMeters = sqrMetersByRoomInDescendingOrder.stream()
                    .map(SqrMetersByRoomDTO::getSqrMeters)
                    .reduce(0d, Double::sum);
            double price = priceCalculatorService.calculatePrice(totalSqrMeters);
            final String biggestRoom = sqrMetersByRoomInDescendingOrder.get(0).getName();
            final ResponseDTO responseDTO = new ResponseDTO(totalSqrMeters, price, biggestRoom, sqrMetersByRoomInDescendingOrder);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (final Exception e) {
            HttpStatus status;
            if (e instanceof BadRequestException) {
                status = HttpStatus.BAD_REQUEST;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            throw new ResponseStatusException(status, e.getMessage());
        }
    }
}
