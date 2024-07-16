package com.ams.backend.controller;

import com.ams.backend.entity.AuthenticateRequest;
import com.ams.backend.entity.Role;
import com.ams.backend.entity.User;
import com.ams.backend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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

    @Test
    public void getAllUserTest() throws Exception {

        List<User> users = new ArrayList<>();
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        Mockito.when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Perez"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail").value("juan.perez@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role.role").value("admin"));
    }

    @Test
    public void authenticateUserTest() throws Exception {
        String mail = "juan.perez@mail.com";
        String password = "1234";
        AuthenticateRequest request = new AuthenticateRequest(mail, password);

        // Simular el usuario encontrado
        Mockito.when(userService.getUserByMailAndPassword(mail, password)).thenReturn(user);

        // Realizar la solicitud cuando se encuentra el usuario
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Juan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Perez"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail").value("juan.perez@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role.role").value("admin"));

        // Verificar que se llamó al método del servicio con los parámetros correctos
        verify(userService, times(1)).getUserByMailAndPassword(mail, password);
        verifyNoMoreInteractions(userService);

        // Reiniciar las interacciones del servicio para simular un usuario no encontrado
        Mockito.reset(userService);

        // Simular el usuario no encontrado
        Mockito.when(userService.getUserByMailAndPassword(mail, password)).thenReturn(null);

        // Realizar la solicitud cuando no se encuentra el usuario
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Verificar que se llamó al método del servicio con los parámetros correctos
        verify(userService, times(1)).getUserByMailAndPassword(mail, password);
        verifyNoMoreInteractions(userService);
    }


    @Test
    public void createUserTest() throws Exception {
        Mockito.when(userService.createUser(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals("Juan", user.getName());
        assertEquals("Perez", user.getLastName());
        assertEquals("juan.perez@mail.com", user.getMail());
        assertEquals("1234", user.getPassword());
        assertEquals("admin", user.getRole().getRole());
    }

    @Test
    public void updateUserTest() throws Exception {

        Mockito.when(userService.updateUser(1, user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1))
                .updateUser(ArgumentMatchers.anyInt(), ArgumentMatchers.any(User.class));

        assertEquals("Juan", user.getName());
        assertEquals("Perez", user.getLastName());
        assertEquals("juan.perez@mail.com", user.getMail());
        assertEquals("1234", user.getPassword());
        assertEquals("admin", user.getRole().getRole());
    }

    @Test
    public void deleteUserTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/user/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(userService, times(1)).deleteUser(id);
    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
