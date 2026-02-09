package com.esardor.car;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class CarArrayDataAccessService implements CarDao {
    private static final String URL = "src/main/java/com/esardor/cars.csv";

    @Override
    public Car[] getCars() {
        Car[] cars = new Car[5];

        try (
                FileReader fileReader = new FileReader(URL);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String header = bufferedReader.readLine();
            String line;
            int index = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                String regNumber = parts[0].trim();
                BigDecimal rentalPricePerDay = BigDecimal.valueOf(Double.parseDouble(parts[1].trim()));
                Brand brand = Brand.valueOf(parts[2].trim().toUpperCase());
                boolean isElectric = parts[3].trim().equalsIgnoreCase("true");

                cars[index++] = new Car(regNumber, rentalPricePerDay, brand, isElectric);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }
}