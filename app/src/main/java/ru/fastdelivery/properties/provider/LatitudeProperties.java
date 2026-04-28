package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.fastdelivery.usecase.LatitudeProvider;

import java.math.BigDecimal;

@ConfigurationProperties("coordinates.latitude")
@Setter
public class LatitudeProperties implements LatitudeProvider {

    private BigDecimal min;
    private BigDecimal max;


    @Override
    public BigDecimal minLatitude() {
        return min;
    }

    @Override
    public BigDecimal maxLatitude() {
        return max;
    }
}
