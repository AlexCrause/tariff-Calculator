package ru.fastdelivery.usecase;

import java.math.BigDecimal;

public interface LatitudeProvider {

    BigDecimal minLatitude();
    BigDecimal maxLatitude();
}
