package com.esardor.user;

import java.util.UUID;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public User[] showAllUser() {
        return userDao.getUsers();
    }

    public User getUserById(UUID id) {
        return userDao.getUserById(id);
    }
}