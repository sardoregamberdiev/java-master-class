package com.esardor.user;

import java.util.UUID;

public class UserService {
    private final UserArrayDataAccessService userArrayDataAccessService;

    public UserService(UserArrayDataAccessService userArrayDataAccessService) {
        this.userArrayDataAccessService = userArrayDataAccessService;
    }

    public User[] getUsers() {
        return userArrayDataAccessService.getUsers();
    }

    public User getUserById(UUID id) {
        return userArrayDataAccessService.getUserById(id);
    }
}