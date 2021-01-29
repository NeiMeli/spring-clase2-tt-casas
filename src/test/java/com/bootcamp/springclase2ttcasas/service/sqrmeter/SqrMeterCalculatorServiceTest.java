package com.bootcamp.springclase2ttcasas.service.sqrmeter;

import com.bootcamp.springclase2ttcasas.dto.HouseDTO;
import com.bootcamp.springclase2ttcasas.dto.RoomDTO;
import com.bootcamp.springclase2ttcasas.dto.SqrMetersByRoomDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static com.bootcamp.springclase2ttcasas.common.TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SqrMeterCalculatorServiceTest {
    @Autowired
    SqrMeterCalculatorService service;

    @Test
    void testGetSqrMetersByRoomInDescendingOrder() throws SqrMeterCalculatorServiceException {
        assertThat(service.getSqrMetersByRoomInDescendingOrder(HOUSE_DTO_1.get().getRooms()).stream().map(SqrMetersByRoomDTO::getName))
                .containsExactly(ROOM_DTO_2.getName(), ROOM_DTO_1.getName());
        assertThat(service.getSqrMetersByRoomInDescendingOrder(HOUSE_DTO_2.get().getRooms()).stream().map(SqrMetersByRoomDTO::getName))
                .containsExactly(ROOM_DTO_1.getName(), ROOM_DTO_3.getName());
        assertThat(service.getSqrMetersByRoomInDescendingOrder(HOUSE_DTO_3.get().getRooms()).stream().map(SqrMetersByRoomDTO::getName))
                .containsExactly(ROOM_DTO_2.getName(), ROOM_DTO_1.getName(), ROOM_DTO_3.getName());
    }

    @Test
    void testCalculateSqrMeters() {
        assertEquals(47, service.calculateRoomSqrMeters(ROOM_DTO_1.getWidth(), ROOM_DTO_1.getLength()));
        assertEquals(56.7d, service.calculateRoomSqrMeters(ROOM_DTO_2.getWidth(), ROOM_DTO_2.getLength()));
        assertEquals(6, service.calculateRoomSqrMeters(ROOM_DTO_3.getWidth(), ROOM_DTO_3.getLength()));
    }

    @Test
    void testValidateFails() {
        // room without name
        HouseDTO houseDTO1 = HOUSE_GENERATOR.apply(new RoomDTO("", 5,5));
        assertThatExceptionOfType(SqrMeterCalculatorServiceException.class)
                .isThrownBy(() -> service.getSqrMetersByRoomInDescendingOrder(houseDTO1.getRooms()))
                .withMessageContaining(SqrMeterCalculatorErrorImpl.ROOM_WITHOUT_NAME.getMessage());

        // room with 0 meters
        HouseDTO houseDTO2 = HOUSE_GENERATOR.apply(new RoomDTO("room", 0,5));
        assertThatExceptionOfType(SqrMeterCalculatorServiceException.class)
                .isThrownBy(() -> service.getSqrMetersByRoomInDescendingOrder(houseDTO2.getRooms()))
                .withMessageContaining(SqrMeterCalculatorErrorImpl.INVALID_METERS_VALUE.getMessage());
        HouseDTO houseDTO3 = HOUSE_GENERATOR.apply(new RoomDTO("room", 3,0));
        assertThatExceptionOfType(SqrMeterCalculatorServiceException.class)
                .isThrownBy(() -> service.getSqrMetersByRoomInDescendingOrder(houseDTO3.getRooms()))
                .withMessageContaining(SqrMeterCalculatorErrorImpl.INVALID_METERS_VALUE.getMessage());

        // room with negative meters
        HouseDTO houseDTO4 = HOUSE_GENERATOR.apply(new RoomDTO("room", -3,5));
        assertThatExceptionOfType(SqrMeterCalculatorServiceException.class)
                .isThrownBy(() -> service.getSqrMetersByRoomInDescendingOrder(houseDTO4.getRooms()))
                .withMessageContaining(SqrMeterCalculatorErrorImpl.INVALID_METERS_VALUE.getMessage());
        HouseDTO houseDTO5 = HOUSE_GENERATOR.apply(new RoomDTO("room", 3,-5));
        assertThatExceptionOfType(SqrMeterCalculatorServiceException.class)
                .isThrownBy(() -> service.getSqrMetersByRoomInDescendingOrder(houseDTO5.getRooms()))
                .withMessageContaining(SqrMeterCalculatorErrorImpl.INVALID_METERS_VALUE.getMessage());

        // House with no rooms
        HouseDTO houseDTO6 = new HouseDTO("house", "addres", new ArrayList<>());
        assertThatExceptionOfType(SqrMeterCalculatorServiceException.class)
                .isThrownBy(() -> service.getSqrMetersByRoomInDescendingOrder(houseDTO6.getRooms()))
                .withMessageContaining(SqrMeterCalculatorErrorImpl.NO_ROOMS.getMessage());
    }
}