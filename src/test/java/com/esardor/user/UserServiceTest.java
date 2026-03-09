package com.esardor.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// REQUIRED: enable @Mock, @InjectMocks, @Captor
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")
class UserServiceTest {

    // @Mock creates A FAKE UserDao - no real file or DB is touched
    @Mock
    UserDao userDao;

    // @InjectMocks creates UserService and passes our fake UserDao into its constructor
    @InjectMocks
    UserService userService;

    private User james;
    private User andre;
    private User nancy;
    private List<User> users;

    @BeforeEach
    void setUp() {
        james = new User(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "James");
        andre = new User(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"), "Andre");
        nancy = new User(UUID.fromString("6ba7b810-9dad-11d1-80b4-00c04fd430c8"), "Nancy");
        users = Stream.of(james, andre, nancy).toList();
    }

//  ----- getUsers() Tests -------------------------------------------------------------------------

    @Nested
    @DisplayName("getUsers() Tests")
    class GetUsers {

        @Test
        @DisplayName("Can return all users from DAO")
        void canReturnAllUsers() {
            // given
            when(userDao.getUsers()).thenReturn(users);

            // when
            List<User> result = userService.getUsers();

            // then
            assertNotNull(result);
            assertEquals(3, result.size());
            assertEquals(users, result);

            verify(userDao).getUsers();
        }

        @Test
        @DisplayName("Can return empty list when no users exist")
        void canReturnEmptyListWhenNoUsersExists() {
            // given
            when(userDao.getUsers()).thenReturn(Collections.emptyList());

            // when
            List<User> result = userDao.getUsers();

            // then
            assertNotNull(result);
            assertEquals(0, result.size());
            assertTrue(result.isEmpty());
        }
    }

    //    --- getUserById Tests -------------------------------------------------------------------------
    @Nested
    @DisplayName("GetUserById Tests")
    class GetUserById {

        @Test
        @DisplayName("Can return user by id")
        void canReturnUserById() {
            // given
            UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
            when(userDao.getUserById(userId)).thenReturn(james);
            // when
            User user = userService.getUserById(userId);
            // then
            assertNotNull(user);
            assertEquals(userId, user.getId());
            assertEquals("James", user.getName());
            verify(userDao).getUserById(userId);
        }

        @Test
        @DisplayName("Can return null when no user exists")
        void canReturnNullWhenNoUserExists() {
            // given
            UUID unknownId = UUID.randomUUID();
            when(userDao.getUserById(unknownId)).thenReturn(null);

            // when
            User result = userService.getUserById(unknownId);

            // then
            assertNull(result);
        }
    }
}