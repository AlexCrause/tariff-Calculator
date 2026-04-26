package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.height.Height;
import ru.fastdelivery.domain.common.length.Length;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.common.width.Width;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency
) {
    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
    }
    
    public BigDecimal volumeAllPackages() {
        Volume volume = new Volume();
        BigDecimal totalVolume = new BigDecimal(BigInteger.ZERO);
        for (Pack aPackage : packages) {
            Pack normalizedPack = normalizeInputData(aPackage);
            BigDecimal volumePack = volume.calcVolumePack(normalizedPack);
            System.out.println("Объем упаковки: " + volumePack);
            totalVolume = totalVolume.add(volumePack);
        }
        System.out.println("Общий объем всех пакетов: " + totalVolume);
        return totalVolume;
    }

    private Pack normalizeInputData(Pack aPackage) {
        Length lengthR = aPackage.length();
        Height heightR = aPackage.height();
        Width widthR = aPackage.width();
        BigInteger roundLength = lengthR.round(lengthR.length());
        BigInteger roundHeight = heightR.round(heightR.height());
        BigInteger roundWidth = widthR.round(widthR.width());
        return new Pack(
                aPackage.weight(),
                new Height(roundHeight),
                new Length(roundLength),
                new Width(roundWidth));
    }
}
