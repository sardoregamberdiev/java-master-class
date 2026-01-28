package com.esardor.carBooking;

import com.esardor.car.Car;
import com.esardor.user.User;

public class CarBookingService {
    private final CarBookingDao carBookingDao;

    public CarBookingService() {
        this.carBookingDao = new CarBookingDao();
    }

    public String saveCarBooking(User user, Car car) {
        CarBooking carBooking = new CarBooking(user, car);
        carBookingDao.saveCarBooking(carBooking);
        return carBooking.getBookingId();
    }

    public CarBooking[] getCarBookings() {
        return carBookingDao.getCarBookings();
    }

    public CarBooking[] getCarBookingsByUserId(String userId) {
        return carBookingDao.getCarBookingsByUserId(userId);
    }
}