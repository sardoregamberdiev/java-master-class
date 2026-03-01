package com.esardor.booking;

import java.util.ArrayList;
import java.util.List;

public class CarBookingDataAccessService implements CarBookingDao {
    private static List<CarBooking> carBookings = new ArrayList<>();

    @Override
    public List<CarBooking> getCarBookings() {
        return carBookings;
    }

    @Override
    public void saveBooking(CarBooking carBooking) {
        carBookings.add(carBooking);
    }
}