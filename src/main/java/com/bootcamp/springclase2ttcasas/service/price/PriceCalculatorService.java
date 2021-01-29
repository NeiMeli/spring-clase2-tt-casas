package com.bootcamp.springclase2ttcasas.service.price;

import com.bootcamp.springclase2ttcasas.util.RoundUtil;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculatorService {
    private static final double SQR_METER_PRICE = 800;

    public double calculatePrice(double sqrMeters) throws PriceCalculatorServiceException {
        validate(sqrMeters);
        return RoundUtil.roundOneDecimal(sqrMeters * SQR_METER_PRICE);
    }

    private void validate(double sqrMeters) throws PriceCalculatorServiceException {
        if (sqrMeters <= 0) {
            throw new PriceCalculatorServiceException(PriceCalculatorErrorImpl.INVALID_METERS);
        }
    }
}
