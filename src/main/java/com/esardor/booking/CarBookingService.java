package com.esardor.booking;

import com.esardor.car.Car;
import com.esardor.car.CarService;
import com.esardor.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class CarBookingService {
    private final CarBookingDao carBookingDao;
    private final CarService carService;

    public CarBookingService() {
        this.carBookingDao = new CarBookingDao();
        this.carService = new CarService();
    }

    public UUID bookingCar(User user, String regNumber) {
        Car[] availableCars = getAvailableCars();

        if (availableCars.length == 0) {
            throw new IllegalStateException("❌ No cars available for renting 😕");
        }

        for (Car availableCar : availableCars) {
            if (availableCar.getRegNumber().equals(regNumber)) {
                Car car = carService.getCarByRegNumber(regNumber);
                UUID bookingId = UUID.randomUUID();
                carBookingDao.saveBooking(new CarBooking(bookingId, user, car, LocalDateTime.now()));
                return bookingId;
            }
        }
        throw new IllegalStateException(String.format("❌ The car with reg %s already booked", regNumber));

    }

    public CarBooking[] getCarBookings() {
        int count = 0;
        CarBooking[] carBookings = carBookingDao.getCarBookings();
        for (CarBooking carBooking : carBookings) {
            if (carBooking != null) {
                count++;
            }
        }
        if (count == 0) {
            return new CarBooking[0];
        }
        CarBooking[] newCarBookings = new CarBooking[count];
        int nextItem = 0;
        for (CarBooking carBooking : carBookings) {
            if (carBooking != null) {
                newCarBookings[nextItem++] = carBooking;
            }
        }
        return newCarBookings;
    }

    public CarBooking[] getCarBookingsByUser(UUID userId) {
        CarBooking[] carBookings = carBookingDao.getCarBookings();
        int count = 0;
        for (CarBooking carBooking : carBookings) {
            if (carBooking != null && carBooking.getUser().getId().equals(userId)) {
                count++;
            }
        }

        if (count == 0) {
            return new CarBooking[0];
        }

        CarBooking[] userCarBookings = new CarBooking[count];
        int nextItem = 0;
        for (CarBooking carBooking : carBookings) {
            if (carBooking != null && carBooking.getUser().getId().equals(userId)) {
                userCarBookings[nextItem++] = carBooking;
            }
        }
        return userCarBookings;
    }

    public Car[] getAvailableCars() {
        return getCars(carService.getCars());
    }

    public Car[] getAvailableElectricCars() {
        return getCars(carService.getElectricCar());
    }

    private Car[] getCars(Car[] cars) {
        if (cars.length == 0) {
            return new Car[0];
        }

        CarBooking[] bookedCars = carBookingDao.getCarBookings();
        if (bookedCars.length == 0) {
            return cars;
        }

        int availableCarsCount = 0;
        for (Car car : cars) {
            boolean isFound = false;
            for (CarBooking bookedCar : bookedCars) {
                if (bookedCar == null || !bookedCar.getCar().equals(car)) {
                    continue;
                }
                isFound = true;
            }
            if (!isFound) {
                ++availableCarsCount;
            }
        }

        Car[] availableCars = new Car[availableCarsCount];
        var nextItem = 0;

        for (Car car : cars) {
            var founded = false;
            for (CarBooking bookedCar : bookedCars) {
                if (bookedCar == null || !bookedCar.getCar().equals(car)) {
                    continue;
                }
                founded = true;
            }

            if (!founded) {
                availableCars[nextItem++] = car;
            }
        }

        return availableCars;
    }
}