package com.esardor;

import com.esardor.car.Car;
import com.esardor.car.CarService;
import com.esardor.carBooking.CarBooking;
import com.esardor.carBooking.CarBookingService;
import com.esardor.user.User;
import com.esardor.user.UserService;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserService();
        CarService carService = new CarService();
        CarBookingService carBookingService = new CarBookingService();

        User[] users = userService.getUsers();
        Car[] cars = carService.getCars();
        CarBooking[] carBookings = carBookingService.getCarBookings();


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
                        if (carBookings.length == 1 && carBookings[0] == null) {
                            for (Car car : cars) {
                                System.out.println(car);
                            }
                        } else {
                            for (Car car : cars) {
                                if (Arrays.stream(carBookings).noneMatch(c -> c.getCar() == car)) {
                                    System.out.println(car);
                                }
                            }
                        }
                        System.out.println("➡️ select car reg number");
                        String regNumber = scanner.nextLine();
                        for (User user : users) {
                            System.out.println(user);
                        }
                        System.out.println("➡️ select user id");
                        String id = scanner.nextLine();

                        User userById = userService.getUserById(id);
                        Car carByRegNumber = carService.getCarByRegNumber(regNumber);

                        String carBookingId = carBookingService.saveCarBooking(userById, carByRegNumber);

                        System.out.println("🎉 Successfully booked car with reg number " + regNumber + " for user " + userById.toString());
                        System.out.println("Booking ref: " + carBookingId);
                        break;

                    case 2:
                        for (User user : users) {
                            System.out.println(user);
                        }
                        System.out.println("➡️ select user id");
                        String userId = scanner.nextLine();
                        CarBooking[] carBookingsByUserId = carBookingService.getCarBookingsByUserId(userId);
                        if (carBookingsByUserId != null) {
                            for (CarBooking carBooking : carBookingsByUserId) {
                                if (carBooking != null) {
                                    System.out.println("booking = " + carBooking);
                                }
                            }
                        } else {
                            User user = userService.getUserById(userId);
                            System.out.println("❌ user " + user.toString() + " has no cars booked");
                        }
                        break;

                    case 3:
                        if (carBookings.length == 1 && carBookings[0] == null) {
                            System.out.println("No booking available");
                        } else {
                            for (CarBooking carBooking : carBookings) {
                                if (carBooking != null) {
                                    System.out.println(carBooking);
                                }
                            }
                        }
                        break;

                    case 4:
                        if (carBookings.length == 1 && carBookings[0] == null) {
                            for (Car car : cars) {
                                System.out.println(car);
                            }
                        } else {
                            for (Car car : cars) {
                                if (Arrays.stream(carBookings).noneMatch(c -> c.getCar() == car)) {
                                    System.out.println(car);
                                }
                            }
                        }
                        break;

                    case 5:
                        if (carBookings.length == 1 && carBookings[0] == null) {
                            for (Car car : cars) {
                                if (car.isElectric()) {
                                    System.out.println(car);
                                }
                            }
                        } else {
                            for (Car car : cars) {
                                if (car.isElectric() && Arrays.stream(carBookings).noneMatch(c -> c.getCar() == car)) {
                                    System.out.println(car);
                                }
                            }
                        }
                        break;

                    case 6:
                        for (User user : users) {
                            System.out.println(user);
                        }
                        break;

                    case 7:
                        isNotFinished = false;
                        break;

                    default:
                        System.out.println(number + " is not a valid option");
                }
            } catch (NumberFormatException e) {
                System.out.println("'" + input + "' is not a valid number!\n");
            }
        } while (isNotFinished);
    }
}