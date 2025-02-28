package com.ams.backend.service;

import com.ams.backend.entity.User;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testGetAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.getAllUsers();
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testGetUserById() throws ResourceNotFoundException {
        int userId = 1;
        User expectedUser = new User();
        expectedUser.setId(userId);
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.getUserById(userId);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testGetUserByIdNotFound() {
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    public void testGetUserByMailAndPassword() {
        String mail = "test@mail.com";
        String password = "password123";
        User expectedUser = new User();
        expectedUser.setMail(mail);
        expectedUser.setPassword(password);
        
        when(userRepository.findByMailAndPassword(mail, password)).thenReturn(expectedUser);
        User actualUser = userService.getUserByMailAndPassword(mail, password);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);
        User actualUser = userService.createUser(user);
        assertEquals(user, actualUser);
    }

    @Test
    public void testUpdateUser() throws ResourceNotFoundException {
        int userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        
        User updatedUser = new User();
        updatedUser.setName("New Name");
        updatedUser.setLastName("New LastName");
        updatedUser.setMail("new@mail.com");
        updatedUser.setPassword("newpassword");
        updatedUser.setRole(null);
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        
        User actualUser = userService.updateUser(userId, updatedUser);
        assertEquals(updatedUser.getName(), actualUser.getName());
        assertEquals(updatedUser.getLastName(), actualUser.getLastName());
        assertEquals(updatedUser.getMail(), actualUser.getMail());
        assertEquals(updatedUser.getPassword(), actualUser.getPassword());
        assertEquals(updatedUser.getRole(), actualUser.getRole());
    }

    @Test
    public void testUpdateUserNotFound() {
        int userId = 1;
        User updatedUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userId, updatedUser));
    }

    @Test
    public void testDeleteUser() throws ResourceNotFoundException {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    public void testDeleteUserNotFound() {
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userId));
    }
}
