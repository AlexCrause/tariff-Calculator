package ru.fastdelivery.presentation.validate;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.usecase.LatitudeProvider;
import ru.fastdelivery.usecase.LongitudeProvider;

@RequiredArgsConstructor
public class Validate {

    private final LatitudeProvider latitudeProvider;
    private final LongitudeProvider longitudeProvider;

    public void validateCoordinates(CalculatePackagesRequest request) {
        if (request.departure().latitude().compareTo(latitudeProvider.minLatitude()) < 0 ||
                request.departure().longitude().compareTo(longitudeProvider.minLongitude()) < 0 ||
                request.destination().latitude().compareTo(latitudeProvider.minLatitude()) < 0 ||
                request.destination().longitude().compareTo(longitudeProvider.minLongitude()) < 0) {
            throw new IllegalArgumentException("The latitude/longitude cannot be less than the minimum value!");
        }

        if (request.departure().latitude().compareTo(latitudeProvider.maxLatitude()) > 0 ||
                request.departure().longitude().compareTo(longitudeProvider.maxLongitude()) > 0 ||
                request.destination().latitude().compareTo(latitudeProvider.maxLatitude()) > 0 ||
                request.destination().longitude().compareTo(longitudeProvider.maxLongitude()) > 0) {
            throw new IllegalArgumentException("The latitude/longitude cannot be greater than the maximum value!");
        }
    }
}
