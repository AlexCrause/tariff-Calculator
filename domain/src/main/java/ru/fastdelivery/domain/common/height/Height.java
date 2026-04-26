package ru.fastdelivery.domain.common.height;

import java.math.BigInteger;

public record Height(BigInteger height) implements Comparable<Height> {

    public Height {
        if (isLessThanZero(height)) {
            throw new IllegalArgumentException("Height cannot be below Zero!");
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
        Height heightOb = (Height) o;
        return height.compareTo(heightOb.height) == 0;
    }

    @Override
    public int compareTo(Height h) {
        return height().compareTo(h.height());
    }

    public boolean greaterThan(Height maxHeight) {
        return height().compareTo(maxHeight.height()) > 0;
    }

    public BigInteger round(BigInteger height) {
        System.out.println("Высота до округления: " + height);
        int intheight = Integer.parseInt(String.valueOf(height));
        while (intheight % 50 != 0) {
            intheight++;
        }
        System.out.println("Высота после округления: " + intheight);
        return BigInteger.valueOf(intheight);
    }
}
