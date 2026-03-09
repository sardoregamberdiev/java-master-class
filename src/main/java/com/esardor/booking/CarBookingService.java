package com.esardor.booking;

import com.esardor.car.Car;
import com.esardor.car.CarService;
import com.esardor.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CarBookingService {
    private final CarBookingDao carBookingDao;
    private final CarService carService;

    public CarBookingService(
            CarBookingDao carBookingDao,
            CarService carService
    ) {
        this.carBookingDao = carBookingDao;
        this.carService = carService;
    }

    public UUID saveBooking(User user, String regNumber) {
        List<Car> availableCars = getAvailableCars();

        if (availableCars.isEmpty()) {
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

    public List<CarBooking> getCarBookings() {
        return carBookingDao.getCarBookings();
    }

    public List<CarBooking> getCarBookingsByUser(UUID userId) {
        return carBookingDao.getCarBookings().stream()
                .filter(carBooking -> Objects.equals(carBooking.getUser().getId(), userId))
                .toList();
    }

    public List<Car> getAvailableCars() {
        return getCars(carService.getCars());
    }

    public List<Car> getAvailableElectricCars() {
        return getCars(carService.getElectricCar());
    }

    private List<Car> getCars(List<Car> cars) {
        if (cars.isEmpty()) {
            return new ArrayList<>();
        }

        List<CarBooking> bookedCars = carBookingDao.getCarBookings();
        if (bookedCars.isEmpty()) {
            return cars;
        }

        List<Car> availableCars = new ArrayList<>();

        for (Car car : cars) {
            var founded = false;
            for (CarBooking bookedCar : bookedCars) {
                if (bookedCar == null || !bookedCar.getCar().equals(car)) {
                    continue;
                }
                founded = true;
            }

            if (!founded) {
                availableCars.add(car);
            }
        }

        return availableCars;
    }
}