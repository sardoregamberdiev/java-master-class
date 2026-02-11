package com.esardor.car;

public class CarService {
    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    public Car[] getCars() {
        return carDao.getCars();
    }

    public Car[] getElectricCar() {
        Car[] cars = carDao.getCars();
        int count = 0;
        for (Car car : cars) {
            if (car.isElectric()) {
                count++;
            }
        }
        if (count == 0) {
            return new Car[0];
        }

        Car[] electricCars = new Car[count];
        int nextItem = 0;
        for (Car car : cars) {
            if (car.isElectric()) {
                electricCars[nextItem++] = car;
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