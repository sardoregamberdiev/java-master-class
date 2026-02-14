package com.esardor.car;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarFileListDataAccessService implements CarListDao {
    private static final String FILE_URL = "src/main/java/com/esardor/cars.csv";
    private static List<Car> cars = new ArrayList<>();

    @Override
    public List<Car> getCars() {
        File file = new File(FILE_URL);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (
                FileReader fileReader = new FileReader(FILE_URL);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                String regNumber = parts[0].trim();
                BigDecimal rentalPricePerDay = BigDecimal.valueOf(Double.parseDouble(parts[1].trim()));
                Brand brand = Brand.valueOf(parts[2].trim().toUpperCase());
                boolean isElectric = parts[3].trim().equalsIgnoreCase("true");
                cars.add(new Car(regNumber, rentalPricePerDay, brand, isElectric));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }
}