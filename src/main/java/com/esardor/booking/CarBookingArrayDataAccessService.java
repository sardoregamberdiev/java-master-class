package com.esardor.booking;

import java.io.*;

public class CarBookingArrayDataAccessService implements CarBookingDao {
    private static final String FILE_URL = "src/main/java/com/esardor/bookings.ser";
    private static CarBooking[] carBookings;

    static {
        carBookings = new CarBooking[10];
    }

    // Read all bookings from file
    @Override
    public CarBooking[] getCarBookings() {
        File file = new File(FILE_URL);

        if (!file.exists()) {
            return new CarBooking[0];
        }

        // initialize size
        int nextIndex = 0;

        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            while (true) {
                try {
                    CarBooking carBooking = (CarBooking) objectInputStream.readObject();

                    // Check if array is full
                    if (nextIndex >= carBookings.length) {
                        // Expand array by 10
                        CarBooking[] newCarBookings = new CarBooking[carBookings.length + 10];
                        for (int i = 0; i < carBookings.length; i++) {
                            newCarBookings[i] = carBookings[i];
                        }
                        carBookings = newCarBookings;
                    }

                    // Add booking to array
                    carBookings[nextIndex++] = carBooking;

                } catch (EOFException e) {
                    // End of file reached - break the loop
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return new CarBooking[0];
        }

        // Trim array to actual size (remove null elements)
        CarBooking[] result = new CarBooking[nextIndex];
        for (int i = 0; i < nextIndex; i++) {
            result[i] = carBookings[i];
        }

        return result;
    }

    // Save one booking to a file
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
            e.printStackTrace();
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