package com.esardor.car;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarFileDataAccessService implements CarDao {

    @Override
    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();

        String path = Objects.requireNonNull(getClass().getClassLoader().getResource("users.csv")).getPath();
        File file = new File(path);
        if (!file.exists()) {
            return cars;
        }

        try (
                FileReader fileReader = new FileReader(path);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Car car = parseLine(line);
                cars.add(car);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return cars;
    }

    private Car parseLine(String line) {
        String[] parts = line.split(",");
        String regNumber = parts[0].trim();
        BigDecimal rentalPricePerDay = BigDecimal.valueOf(Double.parseDouble(parts[1].trim()));
        Brand brand = Brand.valueOf(parts[2].trim().toUpperCase());
        boolean isElectric = parts[3].trim().equalsIgnoreCase("true");
        return new Car(regNumber, rentalPricePerDay, brand, isElectric);
    }
}