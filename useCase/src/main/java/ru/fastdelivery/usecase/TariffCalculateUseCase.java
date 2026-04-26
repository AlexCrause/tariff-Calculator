package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;

    public Price calc(Shipment shipment) {
        BigDecimal volumeAllPackages = shipment.volumeAllPackages();
        Price cubeMeterCost = volumePriceProvider.cubeMeterCost();
        System.out.println("Цена за кубический метр: " + cubeMeterCost.amount() + " руб.");
        Price totalCostByVolume = cubeMeterCost.multiply(volumeAllPackages);
        System.out.println("Общая стоимость всех посылок, (цена * объем): " + totalCostByVolume.amount() + " руб.");

        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        System.out.println("Вес всех посылок в КГ: " + weightAllPackagesKg);
        Price costPerKg = weightPriceProvider.costPerKg();
        System.out.println("Цена на 1кг веса посылки: " + costPerKg.amount());
        Price totalCostByWeight = costPerKg.multiply(weightAllPackagesKg);
        System.out.println("Общая стоимость всех посылок: " + totalCostByWeight.amount() + " руб");

        Price maxed = totalCostByVolume.max(totalCostByWeight);
        System.out.println("Максимальная цена: " + maxed.amount());
        return maxed;

        //var minimalPrice = weightPriceProvider.minimalPrice();


//        return weightPriceProvider
//                .costPerKg()
//                .multiply(weightAllPackagesKg)
//                .max(minimalPrice);
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}
