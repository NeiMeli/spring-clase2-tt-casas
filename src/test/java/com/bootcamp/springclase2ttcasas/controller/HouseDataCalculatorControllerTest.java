package com.bootcamp.springclase2ttcasas.controller;

import com.bootcamp.springclase2ttcasas.dto.ResponseDTO;
import com.bootcamp.springclase2ttcasas.dto.SqrMetersByRoomDTO;
import com.bootcamp.springclase2ttcasas.service.price.PriceCalculatorErrorForTest;
import com.bootcamp.springclase2ttcasas.service.sqrmeter.SqrMeterCalculatorErrorForTest;
import com.bootcamp.springclase2ttcasas.service.price.PriceCalculatorService;
import com.bootcamp.springclase2ttcasas.service.price.PriceCalculatorServiceException;
import com.bootcamp.springclase2ttcasas.service.sqrmeter.SqrMeterCalculatorService;
import com.bootcamp.springclase2ttcasas.service.sqrmeter.SqrMeterCalculatorServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.bootcamp.springclase2ttcasas.common.TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HouseDataCalculatorControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SqrMeterCalculatorService sqrMeterCalculatorService;

    @MockBean
    PriceCalculatorService priceCalculatorService;

    @Test
    void testCalculateHouseDataHappy() throws Exception {
        List<SqrMetersByRoomDTO> sqrMetersByRoomDTOS = SQR_METERS_BY_ROOM_DTOs.get();
        when(sqrMeterCalculatorService.getSqrMetersByRoomInDescendingOrder(anyList()))
                .thenReturn(sqrMetersByRoomDTOS);
        when(priceCalculatorService.calculatePrice(anyDouble()))
                .thenReturn(66400d);
        MvcResult mvcResult = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON_GENERATOR.apply(HOUSE_DTO_1.get())))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        ResponseDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseDTO.class);
        assertEquals(sqrMetersByRoomDTOS.get(0).getName(), responseDTO.getBiggestRoom());
        assertEquals(83, responseDTO.getTotalSquareMeters());
        assertEquals(66400d, responseDTO.getPrice());
        assertEquals(sqrMetersByRoomDTOS.get(0).getName(), responseDTO.getSqrMetersByRoom().get(0).getName());
        assertEquals(sqrMetersByRoomDTOS.get(0).getSqrMeters(), responseDTO.getSqrMetersByRoom().get(0).getSqrMeters());
        assertEquals(sqrMetersByRoomDTOS.get(1).getName(), responseDTO.getSqrMetersByRoom().get(1).getName());
        assertEquals(sqrMetersByRoomDTOS.get(1).getSqrMeters(), responseDTO.getSqrMetersByRoom().get(1).getSqrMeters());
    }

    @Test
    void testSqrMeterCalculatorServiceException() throws Exception {
        when(sqrMeterCalculatorService.getSqrMetersByRoomInDescendingOrder(anyList()))
                .thenThrow(new SqrMeterCalculatorServiceException(SqrMeterCalculatorErrorForTest.SQR_METER_CALCULATOR_ERROR));
        MvcResult mvcResult = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON_GENERATOR.apply(HOUSE_DTO_1.get())))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isInstanceOf(ResponseStatusException.class).hasMessageContaining(SqrMeterCalculatorErrorForTest.SQR_METER_CALCULATOR_ERROR.getMessage());
    }

    @Test
    void testPriceCalculatorServiceException() throws Exception {
        List<SqrMetersByRoomDTO> sqrMetersByRoomDTOS = SQR_METERS_BY_ROOM_DTOs.get();
        when(sqrMeterCalculatorService.getSqrMetersByRoomInDescendingOrder(anyList()))
                .thenReturn(sqrMetersByRoomDTOS);
        when(priceCalculatorService.calculatePrice(anyDouble()))
                .thenThrow(new PriceCalculatorServiceException(PriceCalculatorErrorForTest.PRICE_CALCULATOR_ERROR));
        MvcResult mvcResult2 = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON_GENERATOR.apply(HOUSE_DTO_1.get())))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult2.getResolvedException()).isInstanceOf(ResponseStatusException.class).hasMessageContaining(PriceCalculatorErrorForTest.PRICE_CALCULATOR_ERROR.getMessage());
    }

    @Test
    void testInternalServerErrorException() throws Exception {
        final String ERROR = "ERROR";
        // El error esta en PriceCalculatorService
        when(priceCalculatorService.calculatePrice(anyDouble()))
                .thenThrow(new RuntimeException(ERROR));
        MvcResult mvcResult = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON_GENERATOR.apply(HOUSE_DTO_1.get())))
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isInstanceOf(ResponseStatusException.class).hasMessageContaining(ERROR);

        // El error esta en SqrMeterCalculatorService
        when(sqrMeterCalculatorService.getSqrMetersByRoomInDescendingOrder(anyList()))
                .thenThrow(new RuntimeException(ERROR));
        MvcResult mvcResult2 = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON_GENERATOR.apply(HOUSE_DTO_1.get())))
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andReturn();
        assertThat(mvcResult2.getResolvedException()).isInstanceOf(ResponseStatusException.class).hasMessageContaining(ERROR);
    }
}