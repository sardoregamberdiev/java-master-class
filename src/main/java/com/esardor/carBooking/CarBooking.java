package com.esardor.carBooking;

import com.esardor.car.Car;
import com.esardor.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class CarBooking {
    private String bookingId;
    private User user;
    private Car car;
    private LocalDateTime bookingTime;
    private boolean isCancelled;

    {
        this.bookingId = UUID.randomUUID().toString();
        this.bookingTime = LocalDateTime.now();
        this.isCancelled = false;
    }

    public CarBooking() {
    }

    public CarBooking(User user, Car car) {
        this.user = user;
        this.car = car;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
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
        return "CarBooking{" +
                "bookingId=" + bookingId +
                ", user=" + user + '\'' +
                ", car=" + car + '\'' +
                ", bookingTime=" + bookingTime + '\'' +
                ", isCancelled=" + isCancelled + '\'' +
                '}';
    }
}
