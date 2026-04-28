package ru.fastdelivery.presentation.api.request;

import java.math.BigDecimal;

public record DestinationDto(

        BigDecimal latitude,

        BigDecimal longitude
) {
}
