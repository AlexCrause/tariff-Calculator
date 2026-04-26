package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,

        @Schema(description = "Длина в мм", example = "345")
        BigInteger length,

        @Schema(description = "Ширина в мм", example = "589")
        BigInteger width,

        @Schema(description = "Высота в мм", example = "234")
        BigInteger height
) {
}
