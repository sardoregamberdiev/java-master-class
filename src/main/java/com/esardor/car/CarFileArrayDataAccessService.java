package com.esardor.car;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class CarFileArrayDataAccessService implements CarArrayDao {
    private static final String FILE_URL = "src/main/java/com/esardor/cars.csv";
    private static Car[] cars;

    static {
        cars = new Car[5];
    }

    @Override
    public Car[] getCars() {
        File file = new File(FILE_URL);
        if (!file.exists()) {
            return new Car[0];
        }

        // initialize size
        int index = 0;
        try (
                FileReader fileReader = new FileReader(FILE_URL);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String header = bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                String regNumber = parts[0].trim();
                BigDecimal rentalPricePerDay = BigDecimal.valueOf(Double.parseDouble(parts[1].trim()));
                Brand brand = Brand.valueOf(parts[2].trim().toUpperCase());
                boolean isElectric = parts[3].trim().equalsIgnoreCase("true");

                // Check if array is full
                if (index >= cars.length) {
                    Car[] newArrayCars = new Car[cars.length];
                    for (int i = 0; i < cars.length; i++) {
                        newArrayCars[i] = cars[i];
                    }
                    cars = newArrayCars;
                }

                // Add booking to array
                cars[index++] = new Car(regNumber, rentalPricePerDay, brand, isElectric);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Trim array to actual size (remove null elements)
        Car[] result = new Car[index];
        for (int i = 0; i < index; i++) {
            result[i] = cars[i];
        }

        return result;
    }
}