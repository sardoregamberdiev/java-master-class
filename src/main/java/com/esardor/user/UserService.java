package com.esardor.user;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public User[] getUsers() {
        return userDao.getUsers();
    }

    public User getUserById(String id) {
        return userDao.getUserById(id);
    }
}