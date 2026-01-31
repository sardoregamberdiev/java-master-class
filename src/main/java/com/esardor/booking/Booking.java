package com.esardor.booking;

import com.esardor.car.Car;
import com.esardor.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {
    private UUID bookingId;
    private User user;
    private Car car;
    private LocalDateTime bookingTime;
    private boolean isCancelled;

    public Booking(UUID bookingId, User user, Car car, LocalDateTime bookingTime) {
        this.bookingId = bookingId;
        this.user = user;
        this.car = car;
        this.bookingTime = bookingTime;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", user='" + user +
                ", car='" + car +
                ", bookingTime=" + bookingTime +
                ", isCancelled=" + isCancelled +
                '}';
    }
}
