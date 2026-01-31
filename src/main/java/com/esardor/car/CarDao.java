package com.esardor.car;

import java.util.Objects;

public class CarDao {
    private static final Car[] cars;

    static {
        cars = new Car[]{
                new Car("017", 89.00, CarBrand.BYD, true),
                new Car("777", 125.00, CarBrand.BYD, true),
                new Car("001", 120.00, CarBrand.MERCEDES),
                new Car("7001", 100.00, CarBrand.TOYOTA),
                new Car("1000", 55.00, CarBrand.AUDI),
        };
    }

    public Car[] getCars() {
        return cars;
    }

    public Car getCarById(String regNumber) {
        for (Car car : cars) {
            if (Objects.equals(car.getRegNumber(), regNumber)) {
                return car;
            }
        }
        return null;
    }
}