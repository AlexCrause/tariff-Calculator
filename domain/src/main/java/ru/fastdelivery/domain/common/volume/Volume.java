package ru.fastdelivery.domain.common.volume;

import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public record Volume() {


    public BigDecimal calcVolumePack(Pack normalizedPack) {
        BigInteger length = normalizedPack.length().length();
        BigInteger height = normalizedPack.height().height();
        BigInteger width = normalizedPack.width().width();
        BigInteger multiplyLenAndHei = length.multiply(height);
        BigInteger resMultiply = multiplyLenAndHei.multiply(width);
        return new BigDecimal(resMultiply).divide(BigDecimal.valueOf(1_000_000_000));
    }
}
