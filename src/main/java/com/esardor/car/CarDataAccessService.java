package com.esardor.car;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarDataAccessService implements CarDao {
    private static final List<Car> cars;

    static {
        cars = Stream.of(
                new Car("017", new BigDecimal("89.00"), Brand.BYD, true),
                new Car("777", new BigDecimal("125.00"), Brand.BYD, true),
                new Car("001", new BigDecimal("120.00"), Brand.MERCEDES),
                new Car("7001", new BigDecimal("100.00"), Brand.TOYOTA),
                new Car("1000", new BigDecimal("55.00"), Brand.AUDI)
        ).collect(Collectors.toList());
    }

    @Override
    public List<Car> getCars() {
        return cars;
    }
}