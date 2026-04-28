package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.fastdelivery.usecase.LongitudeProvider;

import java.math.BigDecimal;

@ConfigurationProperties("coordinates.longitude")
@Setter
public class LongitudeProperties implements LongitudeProvider {

    private BigDecimal min;
    private BigDecimal max;

    @Override
    public BigDecimal minLongitude() {
        return min;
    }

    @Override
    public BigDecimal maxLongitude() {
        return max;
    }
}
