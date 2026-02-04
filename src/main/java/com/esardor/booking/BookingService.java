package com.esardor.booking;

import com.esardor.car.Car;
import com.esardor.car.CarService;
import com.esardor.user.User;
import com.esardor.user.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookingService {
    private final BookingDao bookingDao;
    /**
     * Using this service inside another service
     * or should I use DAO?
     */
    private final UserService userService;
    private final CarService carService;

    public BookingService() {
        this.bookingDao = new BookingDao();
        this.userService = new UserService();
        this.carService = new CarService();
    }

    public Booking[] getAllBookedCars() {
        return bookingDao.getAllBookedCars();
    }

    public Booking bookingCar(UUID userId, String regNumber) {
        Car car = carService.getCarByRegNumber1(regNumber);
        User user = userService.getUserById(userId);
        UUID bookingId = UUID.randomUUID();
        LocalDateTime bookingTime = LocalDateTime.now();

        Booking booking = new Booking(bookingId, user, car, bookingTime);
        bookingDao.saveBooking(booking);
        return booking;
    }

    public Booking[] showAllUserBookedCars(UUID userId) {
        return bookingDao.getCarBookingsByUserId(userId);
    }
}