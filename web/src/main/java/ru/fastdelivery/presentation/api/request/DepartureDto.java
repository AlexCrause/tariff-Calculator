package ru.fastdelivery.presentation.api.request;

import java.math.BigDecimal;

public record DepartureDto(

        BigDecimal latitude,

        BigDecimal longitude
) {
}
