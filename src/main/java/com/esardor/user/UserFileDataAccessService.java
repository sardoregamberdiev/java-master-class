package com.esardor.user;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class UserFileDataAccessService implements UserDao {
    private static final String URL = "src/main/java/com/esardor/users.csv";
    private static final String CONTENT = """
            id					                    name
            550e8400-e29b-41d4-a716-446655440000,	James
            f47ac10b-58cc-4372-a567-0e02b2c3d479,	Andry
            6ba7b810-9dad-11d1-80b4-00c04fd430c8,	Nandy
            """;

    @Override
    public User[] getUsers() {
        User[] users = new User[3];
        try (
                FileWriter fileWriter = new FileWriter(URL);
                FileReader fileReader = new FileReader(URL);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            fileWriter.write(CONTENT);
            fileWriter.flush();

            String header = bufferedReader.readLine();
            String line;
            int index = 0;
            while ((line = bufferedReader.readLine()) != null) {
                User user = parseLine(line);
                users[index++] = user;
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return users;
    }

    @Override
    public User getUserById(UUID id) {
        User[] users = getUsers();
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    private User parseLine(String line) {
        String[] parts = line.split(",");
        UUID id = UUID.fromString(parts[0].trim());
        String name = parts[1].trim();

        return new User(id, name);
    }
}