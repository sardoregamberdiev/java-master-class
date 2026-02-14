package com.esardor.booking;

import java.util.ArrayList;
import java.util.List;

public class CarBookingListDataAccessService implements CarBookingListDao {
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