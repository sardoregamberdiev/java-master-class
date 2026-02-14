package com.esardor.car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarListDataAccessService implements CarListDao {
    private static final List<Car> cars;

    static {
        cars = new ArrayList<>();
        cars.add(new Car("017", new BigDecimal("89.00"), Brand.BYD, true));
        cars.add(new Car("777", new BigDecimal("125.00"), Brand.BYD, true));
        cars.add(new Car("001", new BigDecimal("120.00"), Brand.MERCEDES));
        cars.add(new Car("7001", new BigDecimal("100.00"), Brand.TOYOTA));
        cars.add(new Car("1000", new BigDecimal("55.00"), Brand.AUDI));
    }

    @Override
    public List<Car> getCars() {
        return cars;
    }
}