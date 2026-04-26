package ru.fastdelivery.domain.common.width;

import java.math.BigInteger;

public record Width(BigInteger width) implements Comparable<Width> {

    public Width {
        if (isLessThanZero(width)) {
            throw new IllegalArgumentException("Width cannot be below Zero!");
        }
    }

    private static boolean isLessThanZero(BigInteger price) {
        return BigInteger.ZERO.compareTo(price) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Width widthOb = (Width) o;
        return width.compareTo(widthOb.width) == 0;
    }

    @Override
    public int compareTo(Width w) {
        return width().compareTo(w.width());
    }

    public boolean greaterThan(Width maxWidth) {
        return width().compareTo(maxWidth.width()) > 0;
    }

    public BigInteger round(BigInteger width) {
        System.out.println("Ширина до округления: " + width);
        int intWidth = Integer.parseInt(String.valueOf(width));
        while (intWidth % 50 != 0) {
            intWidth++;
        }
        System.out.println("Ширина после округления: " + intWidth);
        return BigInteger.valueOf(intWidth);
    }
}
