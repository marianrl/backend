package com.ams.backend.controller;

import com.ams.backend.model.Client;
import com.ams.backend.service.ClientService;
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

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    public void getAllClientTest() throws Exception {

        List<Client> clients = new ArrayList<>();
        Mockito.when(clientService.getAllClients()).thenReturn(clients);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/client"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getClientByIdTest() throws Exception {
        Client client = new Client(1L, "Mariano");
        Mockito.when(clientService.getClientById(1L)).thenReturn(client);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/client/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.client").value("Mariano"));
    }

    @Test
    public void createClientTest() throws Exception {
        Client client = new Client(null, "Mariano");
        Client savedClient = new Client(1L, "Mariano");
        Mockito.when(clientService.createClient(client)).thenReturn(savedClient);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"branch\":\"Client\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateClientTest() throws Exception {
        Client updatedClient = new Client(1L, "Ariel");

        Mockito.when(clientService.updateClient(1L, updatedClient)).thenReturn(updatedClient);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/client/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"client\":\"Ariel\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(clientService, Mockito.times(1)).updateClient(ArgumentMatchers.any(), ArgumentMatchers.any(Client.class));
    }

    @Test
    public void deleteClientTest() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/client/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(clientService, Mockito.times(1)).deleteClient(id);
    }

}
