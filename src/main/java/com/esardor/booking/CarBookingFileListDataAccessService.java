package com.esardor.booking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarBookingFileListDataAccessService implements CarBookingListDao {
    private static final String FILE_URL = "src/main/java/com/esardor/bookings.ser";
    private static List<CarBooking> carBookings = new ArrayList<>();

    // Read all bookings from file
    @Override
    public List<CarBooking> getCarBookings() {
        File file = new File(FILE_URL);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            while (true) {
                try {
                    CarBooking carBooking = (CarBooking) objectInputStream.readObject();
                    carBookings.add(carBooking);
                } catch (EOFException e) {
                    // End of file reached - break the loop
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }

        return carBookings;
    }

    @Override
    public void saveBooking(CarBooking carBooking) {
        File file = new File(FILE_URL);

        try {
            if (file.exists()) {
                // Append to existing file
                FileOutputStream fos = new FileOutputStream(file, true);
                AppendableObjectOutputStream oos = new AppendableObjectOutputStream(fos);
                oos.writeObject(carBooking);
                oos.close();
                fos.close();
            } else {
                // Create new file
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(carBooking);
                oos.close();
                fos.close();
            }
            System.out.println("Booking saved: " + carBooking.getBookingId());
        } catch (IOException e) {
            System.err.println("Error saving booking: " + e.getMessage());
        }
    }

    // Custom class to handle appending without writing header again
    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset(); // Don't write header when appending
        }
    }
}