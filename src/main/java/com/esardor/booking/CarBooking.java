package com.esardor.booking;

import com.esardor.car.Car;
import com.esardor.user.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class CarBooking {
    private UUID bookingId;
    private User user;
    private Car car;
    private LocalDateTime bookingTime;
    private boolean isCancelled;

    public CarBooking(UUID bookingId, User user, Car car, LocalDateTime bookingTime, boolean isCancelled) {
        this.bookingId = bookingId;
        this.user = user;
        this.car = car;
        this.bookingTime = bookingTime;
        this.isCancelled = isCancelled;
    }

    public CarBooking(UUID bookingId, User user, Car car, LocalDateTime bookingTime) {
        this(bookingId, user, car, bookingTime, false);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarBooking carBooking = (CarBooking) o;
        return isCancelled == carBooking.isCancelled && Objects.equals(bookingId, carBooking.bookingId) && Objects.equals(user, carBooking.user) && Objects.equals(car, carBooking.car) && Objects.equals(bookingTime, carBooking.bookingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, user, car, bookingTime, isCancelled);
    }

    @Override
    public String toString() {
        return "CarBooking{" +
                "bookingId=" + bookingId +
                ", user='" + user +
                ", car='" + car +
                ", bookingTime=" + bookingTime +
                ", isCancelled=" + isCancelled +
                "}";
    }
}