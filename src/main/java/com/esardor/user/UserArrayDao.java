package com.esardor.user;

import java.util.UUID;

public interface UserArrayDao {
    User[] getUsers();

    User getUserById(UUID id);
}
