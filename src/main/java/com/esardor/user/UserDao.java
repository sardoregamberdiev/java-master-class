package com.esardor.user;

import java.util.UUID;

public interface UserDao {
    User[] getUsers();

    User getUserById(UUID id);
}
