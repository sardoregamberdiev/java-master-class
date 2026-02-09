package com.esardor.user;

import java.util.UUID;

public class UserService {
    private final UserArrayDataAccessService userArrayDataAccessService;

    public UserService() {
        this.userArrayDataAccessService = new UserArrayDataAccessService();
    }

    public User[] getUsers() {
        return userArrayDataAccessService.getUsers();
    }

    public User getUserById(UUID id) {
        for (User user : getUsers()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new IllegalStateException(String.format("User with id %s not found", id));
    }
}