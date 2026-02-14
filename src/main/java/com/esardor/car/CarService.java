package com.esardor.car;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private final CarListDao carListDao;

    public CarService(CarListDao carListDao) {
        this.carListDao = carListDao;
    }

    public List<Car> getCars() {
        return carListDao.getCars();
    }

    public List<Car> getElectricCar() {
        List<Car> electricCars = new ArrayList<>();
        for (Car car : carListDao.getCars()) {
            if (car.isElectric()) {
                electricCars.add(car);
            }
        }
        return electricCars;
    }

    public Car getCarByRegNumber(String regNumber) {
        for (Car car : getCars()) {
            if (car.getRegNumber().equals(regNumber)) {
                return car;
            }
        }
        throw new IllegalStateException(String.format("Car with reg %s not found", regNumber));
    }
}