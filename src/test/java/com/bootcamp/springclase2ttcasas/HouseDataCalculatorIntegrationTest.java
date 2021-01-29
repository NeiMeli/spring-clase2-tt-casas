package com.bootcamp.springclase2ttcasas;

import com.bootcamp.springclase2ttcasas.dto.HouseDTO;
import com.bootcamp.springclase2ttcasas.dto.ResponseDTO;
import com.bootcamp.springclase2ttcasas.service.price.PriceCalculatorService;
import com.bootcamp.springclase2ttcasas.service.sqrmeter.SqrMeterCalculatorErrorImpl;
import com.bootcamp.springclase2ttcasas.service.sqrmeter.SqrMeterCalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import static com.bootcamp.springclase2ttcasas.common.TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HouseDataCalculatorIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    SqrMeterCalculatorService sqrMeterCalculatorService;

    @Autowired
    PriceCalculatorService priceCalculatorServiceService;

    @Test
    void testHappy() throws Exception {
        final HouseDTO houseDTO = HOUSE_DTO_1.get();
        MvcResult mvcResult = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON_GENERATOR.apply(houseDTO)))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        ResponseDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseDTO.class);
        assertEquals(ROOM_DTO_2.getName(), responseDTO.getBiggestRoom());
        assertEquals(103.7d, responseDTO.getTotalSquareMeters());
        assertEquals(82960d, responseDTO.getPrice());
        assertEquals(ROOM_DTO_2.getName(), responseDTO.getSqrMetersByRoom().get(0).getName());
        assertEquals(56.7d, responseDTO.getSqrMetersByRoom().get(0).getSqrMeters());
        assertEquals(ROOM_DTO_1.getName(), responseDTO.getSqrMetersByRoom().get(1).getName());
        assertEquals(47d, responseDTO.getSqrMetersByRoom().get(1).getSqrMeters());
    }

    @Test
    void testBadRequest() throws Exception {
        HouseDTO houseWithNoRooms = new HouseDTO("house", "addres", new ArrayList<>());
        MvcResult mvcResult = mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON_GENERATOR.apply(houseWithNoRooms)))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult.getResolvedException()).isInstanceOf(ResponseStatusException.class).hasMessageContaining(SqrMeterCalculatorErrorImpl.NO_ROOMS.getMessage());
    }
}
