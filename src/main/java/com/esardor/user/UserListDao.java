package com.esardor.user;

import java.util.List;
import java.util.UUID;

public interface UserListDao {
    List<User> getUsers();

    User getUserById(UUID id);
}
