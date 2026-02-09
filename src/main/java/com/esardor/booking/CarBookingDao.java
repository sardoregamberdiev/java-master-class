package com.esardor.booking;

public interface CarBookingDao {
    CarBooking[] getCarBookings();

    void saveBooking(CarBooking carBooking);
}