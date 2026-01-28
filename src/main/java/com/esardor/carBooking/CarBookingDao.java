package com.esardor.carBooking;

import java.util.Arrays;
import java.util.Objects;

public class CarBookingDao {
    private static CarBooking[] carBookings;
    private static int nextAvailableSlot = 0;
    private static final int CAPACITY = 1;

    static {
        carBookings = new CarBooking[CAPACITY];
    }

    public void saveCarBooking(CarBooking carBooking) {
        if (nextAvailableSlot >= CAPACITY) {
            increaseCapacity();
        }

        carBookings[nextAvailableSlot++] = carBooking;
    }

    private void increaseCapacity() {
        CarBooking[] newCarBookings = new CarBooking[CAPACITY + 1];
        System.arraycopy(carBookings, 0, newCarBookings, 0, CAPACITY);

        carBookings = newCarBookings;
    }

    public CarBooking[] getCarBookings() {
        return carBookings;
    }

    public CarBooking getCarBookingByBookingId(String bookingId) {
        for (CarBooking carBooking : carBookings) {
            if (Objects.equals(carBooking.getBookingId(), bookingId)) {
                return carBooking;
            }
        }
        return null;
    }

    public CarBooking[] getCarBookingsByUserId(String userId) {
        long counted = Arrays.stream(carBookings).filter(s -> s != null && Objects.equals(s.getUser().getId(), userId)).count();
        if (counted > 0) {
            var result = new CarBooking[Math.toIntExact(counted)];
            for (int i = 0, k = 0; i < carBookings.length; i++) {
                if (Objects.equals(carBookings[i].getUser().getId(), userId)) {
                    result[k++] = carBookings[i];
                }
            }
            return result;
        }
        return null;
    }
}