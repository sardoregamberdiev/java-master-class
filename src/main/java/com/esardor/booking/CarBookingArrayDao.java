package com.esardor.booking;

public interface CarBookingArrayDao {
    CarBooking[] getCarBookings();

    void saveBooking(CarBooking carBooking);
}