package com.esardor.car;

import java.math.BigDecimal;

public class CarArrayDataAccessService implements CarArrayDao {
    private static final Car[] cars;

    static {
        cars = new Car[]{
                new Car("017", new BigDecimal("89.00"), Brand.BYD, true),
                new Car("777", new BigDecimal("125.00"), Brand.BYD, true),
                new Car("001", new BigDecimal("120.00"), Brand.MERCEDES),
                new Car("7001", new BigDecimal("100.00"), Brand.TOYOTA),
                new Car("1000", new BigDecimal("55.00"), Brand.AUDI)
        };
    }

    @Override
    public Car[] getCars() {
        return cars;
    }
}