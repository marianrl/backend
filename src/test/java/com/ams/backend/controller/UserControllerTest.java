package com.ams.backend.controller;

import com.ams.backend.entity.AuthenticateRequest;
import com.ams.backend.entity.User;
import com.ams.backend.entity.Role;
import com.ams.backend.repository.UserRepository;
import com.ams.backend.security.JwtTokenUtil;
import com.ams.backend.service.interfaces.UserService;
import com.ams.backend.request.UserMail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
    private AuthenticateRequest authRequest;
    private UserMail userMail;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setMail("juan.perez@mail.com");
        user.setPassword("1234");
        user.setName("Juan");
        user.setLastName("Perez");

        Role role = new Role();
        role.setId(1);
        role.setRole("USER");
        user.setRole(role);

        authRequest = new AuthenticateRequest();
        authRequest.setMail("juan.perez@mail.com");
        authRequest.setPassword("1234");

        userMail = new UserMail();
        userMail.setMail("juan.perez@mail.com");
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

        assertNotNull(result.getBody());
        assertEquals(user, result.getBody());
        verify(userService, times(1)).getUserById(1);
    }

    @Test
    public void testAuthenticate_UserValid() {
        // Configurar datos de prueba
        String mail = "juan.perez@mail.com";
        String password = "1234";
        String token = "mockToken";

        AuthenticateRequest request = new AuthenticateRequest();
        request.setMail(mail);
        request.setPassword(password);

        // Mockear dependencias
        when(userService.getUserByMailAndPassword(mail, password)).thenReturn(user);
        when(jwtTokenUtil.generateToken(mail, user.getName(), user.getLastName(), user.getRole().getId()))
                .thenReturn(token);

        // Llamar al método
        ResponseEntity<Map<String, String>> response = userController.authenticate(request);

        // Verificar resultado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<String, String> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(token, responseBody.get("token"));

        // Verificar interacción con dependencias
        verify(userService, times(1)).getUserByMailAndPassword(mail, password);
        verify(jwtTokenUtil, times(1)).generateToken(mail, user.getName(), user.getLastName(), user.getRole().getId());
    }

    @Test
    public void testAuthenticate_UserNotFound() {
        // Configurar datos de prueba
        String mail = "invalid@mail.com";
        String password = "wrongPassword";

        AuthenticateRequest request = new AuthenticateRequest();
        request.setMail(mail);
        request.setPassword(password);

        // Mockear dependencias
        when(userService.getUserByMailAndPassword(mail, password)).thenReturn(null);

        // Llamar al método
        ResponseEntity<Map<String, String>> response = userController.authenticate(request);

        // Verificar resultado
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<String, String> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Usuario o contraseña incorrecta", responseBody.get("error"));

        // Verificar interacción con dependencias
        verify(userService, times(1)).getUserByMailAndPassword(mail, password);
        verify(jwtTokenUtil, never()).generateToken(anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    public void testAuthenticate_UserUnauthorized() {
        // Configurar datos de prueba
        String mail = "juan.perez@mail.com";
        String password = "1234";
        User user = new User();
        user.setMail(null); // Usuario sin correo
        user.setName("Juan");
        user.setLastName("Perez");

        AuthenticateRequest request = new AuthenticateRequest();
        request.setMail(mail);
        request.setPassword(password);

        // Mockear dependencias
        when(userService.getUserByMailAndPassword(mail, password)).thenReturn(user);

        // Llamar al método
        ResponseEntity<Map<String, String>> response = userController.authenticate(request);

        // Verificar resultado
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<String, String> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Usuario o contraseña incorrecta", responseBody.get("error"));

        // Verificar interacción con dependencias
        verify(userService, times(1)).getUserByMailAndPassword(mail, password);
        verify(jwtTokenUtil, never()).generateToken(anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    public void testAuthenticate_Exception() {
        // Configurar datos de prueba
        String mail = "juan.perez@mail.com";
        String password = "1234";

        AuthenticateRequest request = new AuthenticateRequest();
        request.setMail(mail);
        request.setPassword(password);

        // Mockear dependencias
        when(userService.getUserByMailAndPassword(mail, password))
                .thenThrow(new RuntimeException("Test exception"));

        // Llamar al método
        ResponseEntity<Map<String, String>> response = userController.authenticate(request);

        // Verificar resultado
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        Map<String, String> responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("Error al procesar la solicitud", responseBody.get("error"));

        // Verificar interacción con dependencias
        verify(userService, times(1)).getUserByMailAndPassword(mail, password);
        verify(jwtTokenUtil, never()).generateToken(anyString(), anyString(), anyString(), anyInt());
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

        assertNotNull(result.getBody());
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

    @Test
    void userExists_True() {
        // Arrange
        when(userService.getUserByMail(any(UserMail.class))).thenReturn(user);

        // Act
        ResponseEntity<Boolean> response = userController.userExists(userMail);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional.ofNullable(response.getBody())
                .ifPresent(body -> assertTrue(body));
        verify(userService).getUserByMail(userMail);
    }

    @Test
    void userExists_False() {
        // Arrange
        when(userService.getUserByMail(any(UserMail.class))).thenReturn(null);

        // Act
        ResponseEntity<Boolean> response = userController.userExists(userMail);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional.ofNullable(response.getBody())
                .ifPresent(body -> assertFalse(body));
        verify(userService).getUserByMail(userMail);
    }
}
