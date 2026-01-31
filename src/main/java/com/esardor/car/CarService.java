package com.esardor.car;

import com.esardor.booking.Booking;
import com.esardor.booking.BookingDao;

import java.util.Arrays;

public class CarService {
    private final CarDao carDao;
    private final BookingDao bookingDao;

    public CarService() {
        this.carDao = new CarDao();
        this.bookingDao = new BookingDao();
    }

    public Car[] getCars() {
        return carDao.getCars();
    }

    public Car getCarByRegNumber(String regNumber) {
        return carDao.getCarById(regNumber);
    }

    public void showAllAvailableCars() {
        System.out.println("All available cars:");
        Car[] cars = carDao.getCars();
        Booking[] bookings = bookingDao.getAllBooking();
        if (bookings == null) {
            for (Car car : cars) {
                System.out.println(car);
            }
        } else {
            for (Car car : cars) {
                if (Arrays.stream(bookings).noneMatch(c -> c != null && c.getCar() == car)) {
                    System.out.println(car);
                }
            }
        }
    }

    public void showAllAvailableElectricCars() {
        System.out.println("All available electric cars:");
        Car[] cars = carDao.getCars();
        Booking[] bookings = bookingDao.getAllBooking();
        if (bookings == null) {
            for (Car car : cars) {
                if (car.isElectric()) {
                    System.out.println(car);
                }
            }
        } else {
            for (Car car : cars) {
                if (car.isElectric() && Arrays.stream(bookings).noneMatch(c -> c != null && c.getCar() == car)) {
                    System.out.println(car);
                }
            }
        }
    }
}