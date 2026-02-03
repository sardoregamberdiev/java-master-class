package com.esardor;

import com.esardor.booking.Booking;
import com.esardor.booking.BookingService;
import com.esardor.car.Car;
import com.esardor.car.CarService;
import com.esardor.user.User;
import com.esardor.user.UserService;

import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        CarService carService = new CarService();
        BookingService bookingService = new BookingService();

        /**
         * Calling these method from service is correct, or I have to use them where them are needed
         * */
        User[] users = userService.showAllUser();
        Car[] availableCars = null;

        String menu = """
                
                1️⃣ - Book Car
                2️⃣ - View All User Booked Cars
                3️⃣ - View All Bookings
                4️⃣ - View Available Cars
                5️⃣ - View Available Electric Cars
                6️⃣ - View all users
                7️⃣ - Exit
                
                """;

        boolean isNotFinished = true;

        do {
            System.out.println(menu);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try {
                int number = Integer.parseInt(input);

                switch (number) {
                    case 1:
                        availableCars = carService.showAllAvailableCars();
                        if (availableCars[0] != null) {
                            for (Car car : availableCars) {
                                if (car != null) {
                                    System.out.println(car);
                                }
                            }
                            System.out.println("➡️ select car reg number");
                            String regNumber = scanner.nextLine();

                            if (!Objects.equals(regNumber, "") && carService.getCarByRegNumber2(regNumber)) {
                                for (User user : users) {
                                    System.out.println(user.toString());
                                }

                                System.out.println("➡️ select user id");
                                String id = scanner.nextLine();
                                try {
                                    UUID userId = UUID.fromString(id);
                                    Booking booking = bookingService.bookingCar(userId, regNumber);

                                    System.out.println("🎉 Successfully booked car with reg number: " + regNumber + " for user: " + booking.getUser().getName() + " which user id is " + booking.getUser().getId());
                                    System.out.println("Booking reference number: " + booking.getBookingId());
                                } catch (IllegalArgumentException e) {
                                    System.err.println("'" + id + "' is not valid UUID");
                                }
                            } else {
                                if (Objects.equals(regNumber, "")) {
                                    System.err.println("Reg number should not be null!");
                                } else if (!carService.getCarByRegNumber2(regNumber)) {
                                    System.err.println("Reg number: " + regNumber + " not found");
                                }
                            }
                        } else {
                            System.err.println("Not available cars. All booked");
                        }
                        break;
                    case 2:
                        for (User user : users) {
                            System.out.println(user.toString());
                        }

                        System.out.println("➡️ select user id");
                        String inline = scanner.nextLine();
                        try {
                            UUID userId = UUID.fromString(inline);

                            Booking[] bookingsByUserId = bookingService.showAllUserBookedCars(userId);
                            if (bookingsByUserId != null) {
                                for (Booking booking : bookingsByUserId) {
                                    if (booking != null) {
                                        System.out.println("booking = " + booking);
                                    }
                                }
                            } else {
                                User user = userService.getUserById(userId);
                                if (user == null) {
                                    System.out.println("User by id: " + userId + " not found");
                                } else {
                                    System.err.println("❌ user: " + user + " has no cars booked");
                                }
                            }

                        } catch (IllegalArgumentException e) {
                            System.err.println(inline + " is not valid UUID");
                        }
                        break;
                    case 3:
                        Booking[] bookedCars = bookingService.getAllBookedCars();
                        if (bookedCars != null) {
                            for (Booking bookedCar : bookedCars) {
                                if (bookedCar != null) {
                                    System.out.println(bookedCar);
                                }
                            }
                        } else {
                            System.err.println("Not booked cars yet!");
                        }
                        break;
                    case 4:
                        availableCars = carService.showAllAvailableCars();
                        if (availableCars[0] != null) {
                            for (Car car : availableCars) {
                                if (car != null) {
                                    System.out.println(car);
                                }
                            }
                        } else {
                            System.err.println("Not available cars. All booked");
                        }
                        break;
                    case 5:
                        Car[] availableElectricCars = carService.showAllAvailableElectricCars();
                        if (availableElectricCars[0] != null) {
                            for (Car car : availableElectricCars) {
                                if (car != null) {
                                    System.out.println(car);
                                }
                            }
                        } else {
                            System.err.println("Not available electric cars. All booked");
                        }
                        break;
                    case 6:
                        for (User user : users) {
                            System.out.println(user);
                        }
                        break;
                    case 7:
                        System.out.println("System stopped");
                        isNotFinished = false;
                        break;
                    default:
                        System.err.println(number + " is not a valid option");
                }
            } catch (NumberFormatException e) {
                System.err.println("'" + input + "' is not a valid number!\n");
            }
        } while (isNotFinished);
    }
}