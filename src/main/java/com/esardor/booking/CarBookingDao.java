package com.esardor.booking;

public class CarBookingDao {
    private static CarBooking[] carBookings;

    static {
        carBookings = new CarBooking[5];
    }

    public CarBooking[] getCarBookings() {
        return carBookings;
    }

    public void saveBooking(CarBooking carBooking) {
        int nextAvailableSlot = -1;
        for (int i = 0; i < carBookings.length; i++) {
            if (carBookings[i] == null) {
                nextAvailableSlot = i;
            }
        }

        if (nextAvailableSlot > -1) {
            carBookings[nextAvailableSlot] = carBooking;
            return;
        }

        CarBooking[] newCarBookings = new CarBooking[carBookings.length + 5];

        for (int i = 0; i < carBookings.length; i++) {
            newCarBookings[i] = carBookings[i];
        }

        newCarBookings[carBookings.length] = carBooking;
        carBookings = newCarBookings;
    }
}