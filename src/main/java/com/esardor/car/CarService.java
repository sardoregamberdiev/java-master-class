package com.esardor.car;

import com.esardor.booking.Booking;
import com.esardor.booking.BookingDao;

public class CarService {
    private final CarDao carDao;
    private final BookingDao bookingDao;

    public CarService() {
        this.carDao = new CarDao();
        this.bookingDao = new BookingDao();
    }

    public Car getCarByRegNumber(String regNumber) {
        return carDao.getCarById(regNumber);
    }

    public void showAllAvailableCars() {
        Car[] cars = carDao.getCars();
        Booking[] bookings = bookingDao.getAllBooking();
        if (bookings == null) {
            for (Car car : cars) {
                System.out.println(car);
            }
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
                    System.out.println(car);
                } else {
                    founded = false;
                }
            }
        }
    }

    public void showAllAvailableElectricCars() {
        Car[] cars = carDao.getCars();
        Booking[] bookings = bookingDao.getAllBooking();
        if (bookings == null) {
            for (Car car : cars) {
                if (car.isElectric()) {
                    System.out.println(car);
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
                        System.out.println(car);
                    } else {
                        founded = false;
                    }
                }
            }
        }
    }
}