package com.esardor.car;

import com.esardor.booking.Booking;
import com.esardor.booking.BookingDao;

public class CarService {
    private final CarDao carDao;
    /***
     * when I used BookingService here, it caused a circular dependency problem! This is causing a StackOverflowError
     * that's why I used BookingDao to avoid this porblem
     */
//    private final BookingService bookingService;
    private final BookingDao bookingDao;

    public CarService() {
        this.carDao = new CarDao();
        /***
         * I used BookingService instead of BookingDao.
         */
        this.bookingDao = new BookingDao();
    }

    public Car getCarByRegNumber1(String regNumber) {
        return carDao.getCarById(regNumber);
    }

    public boolean getCarByRegNumber2(String regNumber) {
        return carDao.getCarByRegNumber(regNumber);
    }

    public Car[] showAllAvailableCars() {
        Car[] cars = carDao.getCars();
        Booking[] bookings = bookingDao.getAllBookedCars();
        Car[] result = new Car[cars.length];
        var nextItem = 0;
        if (bookings == null) {
            return cars;
        } else {
            var founded = false;
            for (Car car : cars) {
                for (Booking booking : bookings) {
                    if (booking != null && booking.getCar() == car) {
                        founded = true;
                        break;
                    }
                }
                if (!founded) {
                    result[nextItem++] = car;
                } else {
                    founded = false;
                }
            }
        }
        return result;
    }

    public Car[] showAllAvailableElectricCars() {
        Car[] cars = carDao.getCars();
        Booking[] bookings = bookingDao.getAllBookedCars();
        Car[] result = new Car[cars.length];
        var nextItem = 0;
        if (bookings == null) {
            for (Car car : cars) {
                if (car.isElectric()) {
                    result[nextItem++] = car;
                }
            }
        } else {
            var founded = false;
            for (Car car : cars) {
                if (car.isElectric()) {
                    for (Booking booking : bookings) {
                        if (booking != null && booking.getCar() == car) {
                            founded = true;
                            break;
                        }
                    }
                    if (!founded) {
                        result[nextItem++] = car;
                    } else {
                        founded = false;
                    }
                }
            }
        }
        return result;
    }
}