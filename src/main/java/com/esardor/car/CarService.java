package com.esardor.car;

import java.util.List;
import java.util.Objects;

public class CarService {
    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public List<Car> getCars() {
        return carDao.getCars();
    }

    public List<Car> getElectricCar() {
        return carDao.getCars().stream()
                .filter(Car::isElectric)
                .toList();
    }

    public Car getCarByRegNumber(String regNumber) {
        return getCars().stream()
                .filter(car -> Objects.equals(car.getRegNumber(), regNumber))
                .findFirst()
                .orElse(null);
    }
}