package com.esardor.user;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User getUserById(UUID id) {
        return userDao.getUserById(id);
    }
}