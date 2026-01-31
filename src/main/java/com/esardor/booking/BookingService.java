package com.esardor.booking;

import com.esardor.car.Car;
import com.esardor.car.CarService;
import com.esardor.user.User;
import com.esardor.user.UserService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;
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

    public void saveBookingCar(Scanner scanner) {

        carService.showAllAvailableCars();

        System.out.println("➡️ select car reg number");
        String regNumber = scanner.nextLine();
        if (Objects.equals(regNumber, "")) {
            System.err.println("Reg number should not be null!");
            return;
        }

        userService.showAllUser();

        System.out.println("➡️ select user id");
        String id = scanner.nextLine();

        try {
            Car carByRegNumber = carService.getCarByRegNumber(regNumber);
            UUID userId = UUID.fromString(id);
            User userById = userService.getUserById(userId);

            UUID carBookingId = saveBooking(userById, carByRegNumber);

            System.out.println("🎉 Successfully booked car with reg number " + regNumber + " for user " + userById.toString());
            System.out.println("Booking ref: " + carBookingId);

        } catch (IllegalArgumentException e) {
            System.err.println("'" + id + "' is not valid UUID");
        }
    }

    private UUID saveBooking(User user, Car car) {
        UUID bookingId = UUID.randomUUID();
        LocalDateTime bookingTime = LocalDateTime.now();
        Booking booking = new Booking(bookingId, user, car, bookingTime);
        bookingDao.saveBooking(booking);
        return booking.getBookingId();
    }

    public Booking[] getCarBookingsByUserId(UUID userId) {
        return bookingDao.getCarBookingsByUserId(userId);
    }

    public void getAllBookings() {
        Booking[] bookings = bookingDao.getAllBooking();
        if (bookings == null) {
            System.out.println("No booking available");
        } else {
            for (Booking booking : bookings) {
                if (booking != null) {
                    System.out.println(booking);
                }
            }
        }
    }

    public void showAllUserBookedCars(Scanner scanner) {
        userService.showAllUser();

        System.out.println("➡️ select user id");
        String userId = scanner.nextLine();
        try {
            UUID userIdFromString = UUID.fromString(userId);

            Booking[] bookingsByUserId = bookingDao.getCarBookingsByUserId(userIdFromString);
            if (bookingsByUserId != null) {
                for (Booking booking : bookingsByUserId) {
                    if (booking != null) {
                        System.out.println("booking = " + booking);
                    }
                }
            } else {
                User user = userService.getUserById(userIdFromString);
                System.out.println("❌ user " + user.toString() + " has no cars booked");
            }

        } catch (IllegalArgumentException e) {
            System.err.println(userId + " is not valid UUID");
        }
    }
}