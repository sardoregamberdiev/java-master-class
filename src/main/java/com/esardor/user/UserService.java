package com.esardor.user;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserListDao userListDao;

    public UserService(UserListDao userListDao) {
        this.userListDao = userListDao;
    }

    public List<User> getUsers() {
        return userListDao.getUsers();
    }

    public User getUserById(UUID id) {
        return userListDao.getUserById(id);
    }
}