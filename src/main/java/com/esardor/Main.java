package com.esardor;

import com.esardor.booking.BookingService;
import com.esardor.car.CarService;
import com.esardor.user.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        CarService carService = new CarService();
        BookingService bookingService = new BookingService();

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
                        bookingService.saveBookingCar(scanner);
                        break;
                    case 2:
                        bookingService.showAllUserBookedCars(scanner);
                        break;
                    case 3:
                        bookingService.getAllBookings();
                        break;
                    case 4:
                        carService.showAllAvailableCars();
                        break;
                    case 5:
                        carService.showAllAvailableElectricCars();
                        break;
                    case 6:
                        userService.showAllUser();
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