package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.coordinates.Departure;
import ru.fastdelivery.domain.coordinates.Destination;

import javax.inject.Named;
import java.math.BigDecimal;

@Named
public class DistanceCalculate {

    public BigDecimal calculateInKm(Departure departure, Destination destination) {
        //pi - число pi, rad - радиус сферы (Земли)
        long rad = 6372795;
        double hemisphere = 180.;

        //координаты двух точек
        double llat1 = departure.latitude().doubleValue();
        double llong1 = departure.longitude().doubleValue();

        double llat2 = destination.latitude().doubleValue();
        double llong2 = destination.longitude().doubleValue();

        //в радианах
        double lat1 = llat1 * Math.PI / hemisphere;
        double lat2 = llat2 * Math.PI / hemisphere;
        double long1 = llong1 * Math.PI / hemisphere;
        double long2 = llong2 * Math.PI / hemisphere;

        //косинусы и синусы широт и разницы долгот
        double cl1 = Math.cos(lat1);
        double cl2 = Math.cos(lat2);
        double sl1 = Math.sin(lat1);
        double sl2 = Math.sin(lat2);
        double delta = long2 - long1;
        double cdelta = Math.cos(delta);
        double sdelta = Math.sin(delta);

        //вычисления длины большого круга
        double y = Math.sqrt(Math.pow(cl2 * sdelta, 2) + Math.pow(cl1 * sl2 - sl1 * cl2 * cdelta, 2));
        double x = sl1 * sl2 + cl1 * cl2 * cdelta;
        double ad = Math.atan2(y, x);
        double dist = ad * rad;

        //вычисление начального азимута
        x = (cl1 * sl2) - (sl1 * cl2 * cdelta);
        y = sdelta * cl2;
        double z = Math.toDegrees(Math.atan(-y / x));

        if (x < 0) z = z + 180.;

        double z2 = (z + 180.) % 360. - 180.;
        z2 = -Math.toRadians(z2);
        double anglerad2 = z2 - ((2 * Math.PI) * Math.floor((z2 / (2 * Math.PI))));
        double angledeg = (anglerad2 * 180.) / Math.PI;

        System.out.printf("Distance >> %.0f [meters]%n", dist);
        System.out.println("Initial bearing >> " + angledeg + " [degrees]");

        double distInKm = dist / 1000;
        System.out.println(distInKm + " км");

        return new BigDecimal(distInKm);
    }
}
