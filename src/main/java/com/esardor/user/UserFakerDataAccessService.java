package com.esardor.user;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserFakerDataAccessService implements UserDao {
    private static final List<User> users = new ArrayList<>();

    static {
        for (int i = 0; i < 20; i++) {
            users.add(new User(UUID.randomUUID(), Faker.instance().name().name()));
        }
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
