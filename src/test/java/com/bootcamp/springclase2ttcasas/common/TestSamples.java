package com.bootcamp.springclase2ttcasas.common;

import com.bootcamp.springclase2ttcasas.dto.HouseDTO;
import com.bootcamp.springclase2ttcasas.dto.RoomDTO;
import com.bootcamp.springclase2ttcasas.dto.SqrMetersByRoomDTO;
import net.minidev.json.JSONValue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestSamples {
    public static final RoomDTO ROOM_DTO_1 = new RoomDTO("room-dto-1", 10, 4.7);
    public static final RoomDTO ROOM_DTO_2 = new RoomDTO("room-dto-2", 6.3, 9);
    public static final RoomDTO ROOM_DTO_3 = new RoomDTO("room-dto-3", 3, 2);
    public static final Supplier<List<RoomDTO>> ROOM_DTOs = () -> Arrays.asList(ROOM_DTO_1, ROOM_DTO_2, ROOM_DTO_3);
    public static final Supplier<List<SqrMetersByRoomDTO>> SQR_METERS_BY_ROOM_DTOs = () -> Arrays.asList(new SqrMetersByRoomDTO("room-1", 50), new SqrMetersByRoomDTO("room-2", 33));
    public static final Supplier<HouseDTO> HOUSE_DTO_1 = () -> new HouseDTO("house-dto-1", "address-1", Arrays.asList(ROOM_DTO_1, ROOM_DTO_2));
    public static final Supplier<HouseDTO> HOUSE_DTO_2 = () -> new HouseDTO("house-dto-2", "address-2", Arrays.asList(ROOM_DTO_3, ROOM_DTO_1));
    public static final Supplier<HouseDTO> HOUSE_DTO_3 = () -> new HouseDTO("house-dto-3", "address-3", ROOM_DTOs.get());
    public static final Function<RoomDTO, HouseDTO> HOUSE_GENERATOR = roomDTO -> new HouseDTO("house", "addres", Arrays.asList(ROOM_DTO_1, ROOM_DTO_2, roomDTO));
    public static final Function<Object, String> JSON_GENERATOR = JSONValue::toJSONString;
}
