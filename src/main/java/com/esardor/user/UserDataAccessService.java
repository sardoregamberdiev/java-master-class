package com.esardor.user;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDataAccessService implements UserDao {
    private static final List<User> users;

    static {
        users = Stream.of(
                new User(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "James"),
                new User(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"), "Andre"),
                new User(UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8"), "Nancy")
        ).collect(Collectors.toList());
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User getUserById(UUID id) {
        return users.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst()
                .orElse(null);
    }
}