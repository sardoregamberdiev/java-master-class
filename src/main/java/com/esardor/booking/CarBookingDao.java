package com.esardor.booking;

import java.util.List;

public interface CarBookingDao {
    List<CarBooking> getCarBookings();

    void saveBooking(CarBooking carBooking);
}