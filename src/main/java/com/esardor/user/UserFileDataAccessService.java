package com.esardor.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserFileDataAccessService implements UserDao {
    private static final String FILE_URL = "src/main/java/com/esardor/users.csv";

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        File file = new File(FILE_URL);
        if (!file.exists()) {
            return users;
        }

        try (
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String header = bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                User user = parseLine(line);
                users.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return users;
    }

    @Override
    public User getUserById(UUID id) {
        List<User> users = getUsers();
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