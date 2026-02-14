package com.esardor.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserListDataAccessService implements UserListDao {
    private static final List<User> users;

    static {
        users = new ArrayList<>();
        users.add(new User(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "James"));
        users.add(new User(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"), "Andre"));
        users.add(new User(UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8"), "Nancy"));
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User getUserById(UUID id) {
        for (User user : users) {
            if (Objects.equals(user.getId(), id)) {
                return user;
            }
        }
        return null;
    }
}