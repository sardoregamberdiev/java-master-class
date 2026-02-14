package com.esardor;

import com.esardor.booking.CarBooking;
import com.esardor.booking.CarBookingListDao;
import com.esardor.booking.CarBookingListDataAccessService;
import com.esardor.booking.CarBookingService;
import com.esardor.car.Car;
import com.esardor.car.CarListDao;
import com.esardor.car.CarListDataAccessService;
import com.esardor.car.CarService;
import com.esardor.user.User;
import com.esardor.user.UserListDao;
import com.esardor.user.UserListDataAccessService;
import com.esardor.user.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        // dependencies
        UserListDao userListDao = new UserListDataAccessService();
        CarBookingListDao carBookingArrayDao = new CarBookingListDataAccessService();
        CarListDao carListDao = new CarListDataAccessService();
        CarService carService = new CarService(carListDao);

        //inject
        UserService userService = new UserService(userListDao);
        CarBookingService carBookingService = new CarBookingService(carBookingArrayDao, carService);

        boolean isNotFinished = true;

        do {
            showMenu();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try {
                int number = Integer.parseInt(input);

                switch (number) {
                    case 1:
                        bookCar(scanner, userService, carBookingService);
                        break;
                    case 2:
                        displayUserBookedCars(scanner, userService, carBookingService);
                        break;
                    case 3:
                        displayAllBookings(carBookingService);
                        break;
                    case 4:
                        displayAvailableCars(carBookingService, false);
                        break;
                    case 5:
                        displayAvailableCars(carBookingService, true);
                        break;
                    case 6:
                        displayUsers(userService);
                        break;
                    case 7:
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

    private static void showMenu() {
        String menu = """
                
                1️⃣ - Book Car
                2️⃣ - View All User Booked Cars
                3️⃣ - View All Bookings
                4️⃣ - View Available Cars
                5️⃣ - View Available Electric Cars
                6️⃣ - View all users
                7️⃣ - Exit
                """;
        System.out.println(menu);
    }

    private static void displayUsers(UserService userService) {
        List<User> users = userService.getUsers();
        if (users.isEmpty()) {
            System.err.println("No users saved in the systems");
            return;
        }
        for (User user : users) {
            System.out.println(user);
        }

    }

    private static void displayAvailableCars(CarBookingService carBookingService, boolean isElectric) {
        List<Car> availableCars;
        if (isElectric) {
            availableCars = carBookingService.getAvailableElectricCars();
        } else {
            availableCars = carBookingService.getAvailableCars();
        }

        if (availableCars.isEmpty()) {
            System.err.printf("No available %s cars now!%n", isElectric ? "electric" : "");
        }

        for (Car availableCar : availableCars) {
            System.out.println(availableCar);
        }

    }

    private static void displayUserBookedCars(Scanner scanner, UserService userService, CarBookingService carBookingService) {
        displayUsers(userService);

        System.out.println("➡️ select user id");
        String userId = scanner.nextLine();

        User user = userService.getUserById(UUID.fromString(userId));
        if (user == null) {
            System.out.printf("User with id %s not found%n", userId);
            return;
        }

        try {
            List<CarBooking> bookingsByUser = carBookingService.getCarBookingsByUser(user.getId());
            if (bookingsByUser.isEmpty()) {
                System.err.println("❌ user " + user + " has no cars booked");
                return;
            }
            for (CarBooking carBooking : bookingsByUser) {
                System.out.println("carBooking = " + carBooking);
            }
        } catch (IllegalArgumentException e) {
            System.err.println(userId + " is not valid UUID");
        }
    }

    private static void displayAllBookings(CarBookingService carBookingService) {
        List<CarBooking> bookedCars = carBookingService.getCarBookings();
        if (bookedCars.isEmpty()) {
            System.err.println("Not booked cars yet!");
        }
        for (CarBooking bookedCar : bookedCars) {
            System.out.println("Booking = " + bookedCar);
        }
    }

    private static void bookCar(Scanner scanner, UserService userService, CarBookingService carBookingService) {
        var availableCars = carBookingService.getAvailableCars();
        if (availableCars.isEmpty()) {
            System.err.println("Not available cars. All booked");
            return;
        }

        for (Car car : availableCars) {
            System.out.println(car);
        }

        System.out.println("➡️ select car reg number");
        String regNumber = scanner.nextLine();

        displayUsers(userService);

        System.out.println("➡️ select user id");
        String userId = scanner.nextLine();

        try {
            User user = userService.getUserById(UUID.fromString(userId));
            if (user == null) {
                System.out.printf("User with id %s not found%n", userId);
                return;
            }

            UUID bookingId = carBookingService.bookingCar(user, regNumber);

            String formattedMessage = """
                    🎉 Successfully booked car with reg number %s for user %s
                    Booking ref: %s
                    """.formatted(regNumber, user, bookingId);
            System.out.println(formattedMessage);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}