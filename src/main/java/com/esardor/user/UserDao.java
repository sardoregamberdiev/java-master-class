package com.esardor.user;

import java.util.Objects;
import java.util.UUID;

public class UserDao {
    private static final User[] users;

    static {
        users = new User[]{
                new User(UUID.randomUUID(), "James"),
                new User(UUID.randomUUID(), "Leyla"),
                new User(UUID.randomUUID(), "Brain")
        };
    }

    public User[] getUsers() {
        return users;
    }

    public User getUserById(UUID id) {
        for (User user : users) {
            if (Objects.equals(user.getId(), id)) {
                return user;
            }
        }
        return null;
    }
}