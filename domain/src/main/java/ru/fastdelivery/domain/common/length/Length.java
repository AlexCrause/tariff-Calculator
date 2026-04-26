package ru.fastdelivery.domain.common.length;

import java.math.BigInteger;


public record Length(BigInteger length) implements Comparable<Length> {

    public Length {
        if (isLessThanZero(length)) {
            throw new IllegalArgumentException("Length cannot be below Zero!");
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
        Length lengthOb = (Length) o;
        return length.compareTo(lengthOb.length) == 0;
    }

    @Override
    public int compareTo(Length l) {
        return length().compareTo(l.length());
    }

    public boolean greaterThan(Length maxLength) {
        return length().compareTo(maxLength.length()) > 0;
    }

    public BigInteger round(BigInteger length) {
        System.out.println("Длина до округления: " + length);
        int intLength = Integer.parseInt(String.valueOf(length));
        while (intLength % 50 != 0) {
            intLength++;
        }
        System.out.println("Длина после округления: " + intLength);
        return BigInteger.valueOf(intLength);
    }
}
