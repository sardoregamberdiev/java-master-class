package com.esardor.booking;

import java.util.List;

public interface CarBookingListDao {
    List<CarBooking> getCarBookings();

    void saveBooking(CarBooking carBooking);
}