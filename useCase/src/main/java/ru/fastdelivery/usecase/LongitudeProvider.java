package ru.fastdelivery.usecase;

import java.math.BigDecimal;

public interface LongitudeProvider {

    BigDecimal minLongitude();
    BigDecimal maxLongitude();
}
