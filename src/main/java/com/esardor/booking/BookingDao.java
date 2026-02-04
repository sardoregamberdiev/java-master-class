package com.esardor.booking;

import java.util.Objects;
import java.util.UUID;

public class BookingDao {
    private static Booking[] bookings;
    private static int nextAvailableSlot = 0;
    private static final int CAPACITY = 5;

    static {
        bookings = new Booking[CAPACITY];
    }

    public Booking[] getAllBookedCars() {
        return bookings[0] != null ? bookings : null;
    }

    public void saveBooking(Booking booking) {
        if (nextAvailableSlot >= CAPACITY) {
            increaseCapacity();
        }
        bookings[nextAvailableSlot++] = booking;
    }

    private void increaseCapacity() {
        Booking[] newBookings = new Booking[CAPACITY + 1];
        System.arraycopy(bookings, 0, newBookings, 0, CAPACITY);

        bookings = newBookings;
    }

    public Booking[] getCarBookingsByUserId(UUID userId) {
        /**
         * Doing this logic inside DAO, is it correct way or not?
         * should it be done inside Service?
         * DAO means DataBase, right?
         * */
        int counted = 0;
        for (Booking booking : bookings) {
            if (booking != null && booking.getUser().getId().equals(userId)) {
                counted++;
            }
        }
        if (counted > 0) {
            var result = new Booking[counted];
            for (int i = 0, k = 0; i < bookings.length; i++) {
                if (bookings[i] != null && Objects.equals(bookings[i].getUser().getId(), userId)) {
                    result[k++] = bookings[i];
                }
            }
            return result;
        }
        return null;
    }
}