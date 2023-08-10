package com.ams.backend.service;

import com.ams.backend.entity.User;
import com.ams.backend.entity.Role;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    final private Role role = new Role(1, "admin");
    final private User user = new User(
            1,
            "Juan",
            "Perez",
            "juan.perez@mail.com",
            "1234",
            role
    );

    @Before
    public void setup() {
        userService = new UserService(userRepository);
    }

    @Test
    public void testGetAllUserService() {
        List<User> users = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<User> actualUser = userService.getAllUsers();

        assertEquals(users, actualUser);
    }

    @Test
    public void testGetUserById() throws ResourceNotFoundException {

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User actualUser = userService.getUserById(user.getId());

        assertEquals(user, actualUser);
    }

    @Test
    public void testCreateUser() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User actualUser = userService.createUser(user);

        assertEquals(user, actualUser);
    }

    @Test
    public void testUpdateUser() throws ResourceNotFoundException {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User actualUser = userService
                .updateUser(user.getId(), user);

        assertEquals("Juan", actualUser.getName());
        assertEquals("Perez", actualUser.getLastName());
        assertEquals("juan.perez@mail.com", actualUser.getMail());
        assertEquals("1234", actualUser.getPassword());
        assertEquals("admin", actualUser.getRole().getRole());
    }

    @Test
    public void testDeleteUser() throws ResourceNotFoundException {

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        userService.deleteUser(user.getId());

        verify(userRepository).deleteById(1);
    }
}

