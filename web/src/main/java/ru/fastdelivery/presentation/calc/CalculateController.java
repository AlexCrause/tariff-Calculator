package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.height.Height;
import ru.fastdelivery.domain.common.length.Length;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.common.width.Width;
import ru.fastdelivery.domain.coordinates.Departure;
import ru.fastdelivery.domain.coordinates.Destination;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.presentation.validate.Validate;
import ru.fastdelivery.usecase.DistanceCalculate;
import ru.fastdelivery.usecase.LatitudeProvider;
import ru.fastdelivery.usecase.LongitudeProvider;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
    private final DistanceCalculate distanceCalculate;
    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final CurrencyFactory currencyFactory;
    private final LatitudeProvider latitudeProvider;
    private final LongitudeProvider longitudeProvider;

    @PostMapping
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesResponse calculate(
            @Valid @RequestBody CalculatePackagesRequest request) {
        Validate validate = new Validate(latitudeProvider, longitudeProvider);
        validate.validateCoordinates(request);
        Departure departure = new Departure(
                request.departure().latitude(), request.departure().longitude());
        Destination destination = new Destination(
                request.destination().latitude(), request.destination().longitude());
        BigDecimal distance = distanceCalculate.calculateInKm(departure, destination);

        List<Pack> packList = request.packages().stream()
                .map(cargoPackage -> {
                    Width widthR = new Width(cargoPackage.width());
                    Height heightR = new Height(cargoPackage.height());
                    Length lengthR = new Length(cargoPackage.length());
                    Weight weightR = new Weight(cargoPackage.weight());
                    return new Pack(weightR, heightR, lengthR, widthR);
                }).collect(Collectors.toList());

        var shipment = new Shipment(packList, currencyFactory.create(request.currencyCode()));
        var calculatedPrice = tariffCalculateUseCase.calc(shipment);
        var minimalPrice = tariffCalculateUseCase.minimalPrice();
        return new CalculatePackagesResponse(calculatedPrice, minimalPrice);
    }
}

