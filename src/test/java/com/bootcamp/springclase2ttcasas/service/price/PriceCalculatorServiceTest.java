package com.bootcamp.springclase2ttcasas.service.price;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PriceCalculatorServiceTest {
    @Autowired
    PriceCalculatorService service;

    @Test
    void testCalculatePrice() throws PriceCalculatorServiceException {
        assertEquals(80000, service.calculatePrice(100));
        assertEquals(108000, service.calculatePrice(135));
        assertEquals(74080, service.calculatePrice(92.6));
        assertEquals(55120, service.calculatePrice(68.9));
    }

    @Test
    void testValidateFails() {
        assertThatExceptionOfType(PriceCalculatorServiceException.class)
                .isThrownBy(() -> service.calculatePrice(0))
                .withMessageContaining(PriceCalculatorErrorImpl.INVALID_METERS.getMessage());
        assertThatExceptionOfType(PriceCalculatorServiceException.class)
                .isThrownBy(() -> service.calculatePrice(-6))
                .withMessageContaining(PriceCalculatorErrorImpl.INVALID_METERS.getMessage());
    }
}