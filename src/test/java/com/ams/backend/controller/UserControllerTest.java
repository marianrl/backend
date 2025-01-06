package com.ams.backend.controller;

import com.ams.backend.entity.AuthenticateRequest;
import com.ams.backend.entity.User;
import com.ams.backend.repository.UserRepository;
import com.ams.backend.security.JwtTokenUtil;
import com.ams.backend.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
    }

    @Test
    public void getAllUserTest() throws Exception {
        List<User> users = Collections.singletonList(user);
        when(userService.getAllUsers()).thenReturn(users);

        List<User> result = userController.getAllUsers();

        assertEquals(users, result);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void getUserByIdTest() throws Exception {
        when(userService.getUserById(1)).thenReturn(user);

        ResponseEntity<User> result = userController.getUserById(1);

        assertEquals(user, result.getBody());
        verify(userService, times(1)).getUserById(1);
    }

    @Test
    public void authenticateUserTest() throws Exception {
        // Datos de prueba
        String mail = "juan.perez@mail.com";
        String password = "1234";
        AuthenticateRequest request = new AuthenticateRequest(mail, password);
        String token = "mockToken"; // Token de prueba

        // Caso 1: Usuario encontrado
        when(userRepository.findByMailAndPassword(mail, password)).thenReturn(user);
        when(userService.getUserByMailAndPassword(mail, password)).thenReturn(user);
        when(jwtTokenUtil.generateToken(user.getMail())).thenReturn(token); // Mock del token

        ResponseEntity<Map<String, String>> response = userController.authenticate(request);

        // Verificar respuesta exitosa
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(token, response.getBody().get("token")); // Verificar el token en la respuesta
        verify(userService, times(1)).getUserByMailAndPassword(mail, password);
        verify(jwtTokenUtil, times(1)).generateToken(user.getMail());

        // Caso 2: Usuario no encontrado
        Mockito.reset(userService, jwtTokenUtil); // Reiniciar mocks
        when(userRepository.findByMailAndPassword(mail, password)).thenReturn(null);
        when(userService.getUserByMailAndPassword(mail, password)).thenReturn(null);

        ResponseEntity<Map<String, String>> notFoundResponse = userController.authenticate(request);

        // Verificar respuesta 401 (no autorizado)
        assertEquals(HttpStatus.UNAUTHORIZED, notFoundResponse.getStatusCode());
        assertNull(notFoundResponse.getBody());
        verify(userService, times(1)).getUserByMailAndPassword(mail, password);
        verify(jwtTokenUtil, times(0)).generateToken( anyString()); // No debe generar token
    }



    @Test
    public void createUserTest() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user);

        User result = userController.createUser(user);

        assertEquals(user, result);
        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    public void updateUserTest() throws Exception {
        User user = new User();
        when(userService.updateUser(eq(1), any(User.class))).thenReturn(user);

        ResponseEntity<User> result = userController.updateUser(1, user);

        assertEquals(user, result.getBody());
        verify(userService, times(1)).updateUser(eq(1), any(User.class));
    }

    @Test
    public void deleteUserTest() throws Exception {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(userService).deleteUser(1);

        ResponseEntity<Void> result = userController.deleteUser(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(userService, times(1)).deleteUser(1);
    }
}
