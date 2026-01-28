package com.esardor.car;

public class CarService {
    private final CarDao carDao;

    public CarService() {
        this.carDao = new CarDao();
    }

    public Car[] getCars() {
        return carDao.getCars();
    }

    public Car getCarByRegNumber(String regNumber) {
        return carDao.getCarById(regNumber);
    }
}
