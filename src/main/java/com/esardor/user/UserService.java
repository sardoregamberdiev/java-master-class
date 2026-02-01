package com.esardor.user;

import java.util.UUID;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public void showAllUser() {
        System.out.println("Available users:");
        User[] users = userDao.getUsers();
        for (User user : users) {
            System.out.println(user.toString());
        }
    }

    public User getUserById(UUID id) {
        return userDao.getUserById(id);
    }
}