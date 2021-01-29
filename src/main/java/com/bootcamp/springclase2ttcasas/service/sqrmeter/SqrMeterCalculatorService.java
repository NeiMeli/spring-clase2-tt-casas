package com.bootcamp.springclase2ttcasas.service.sqrmeter;

import com.bootcamp.springclase2ttcasas.dto.RoomDTO;
import com.bootcamp.springclase2ttcasas.dto.SqrMetersByRoomDTO;
import com.bootcamp.springclase2ttcasas.util.RoundUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SqrMeterCalculatorService {
    public List<SqrMetersByRoomDTO> getSqrMetersByRoomInDescendingOrder(List<RoomDTO> rooms) throws SqrMeterCalculatorServiceException {
        validate(rooms);
        Comparator<SqrMetersByRoomDTO> comparator = (r1, r2) -> (int) (r2.getSqrMeters() - r1.getSqrMeters());
        return rooms.stream().map(room -> {
            double sqrMeters = calculateRoomSqrMeters(room.getWidth(), room.getLength());
            return new SqrMetersByRoomDTO(room.getName(), sqrMeters);
        }).sorted(comparator).collect(Collectors.toList());
    }

    private void validate(List<RoomDTO> rooms) throws SqrMeterCalculatorServiceException {
        if (rooms.isEmpty()) {
            throw new SqrMeterCalculatorServiceException(SqrMeterCalculatorErrorImpl.NO_ROOMS);
        }
        if (rooms.stream().anyMatch(r -> Strings.isBlank(r.getName()))) {
            throw new SqrMeterCalculatorServiceException(SqrMeterCalculatorErrorImpl.ROOM_WITHOUT_NAME);
        }
        if (rooms.stream().anyMatch(r -> r.getLength() <= 0 || r.getWidth() <= 0)) {
            throw new SqrMeterCalculatorServiceException(SqrMeterCalculatorErrorImpl.INVALID_METERS_VALUE);
        }
    }

    public double calculateRoomSqrMeters(double width, double length) {
        return RoundUtil.roundOneDecimal(width * length);
    }
}
