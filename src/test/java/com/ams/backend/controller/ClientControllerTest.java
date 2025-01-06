package com.ams.backend.controller;

import com.ams.backend.entity.Client;
import com.ams.backend.service.ClientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private Client client;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setId(1);
    }

    @Test
    public void getAllClientTest() throws Exception {
        List<Client> clients = Collections.singletonList(client);
        when(clientService.getAllClients()).thenReturn(clients);

        List<Client> result = clientController.getAllClients();

        assertEquals(clients, result);
        verify(clientService, times(1)).getAllClients();
    }

    @Test
    public void getClientByIdTest() throws Exception {
        when(clientService.getClientById(1)).thenReturn(client);

        ResponseEntity<Client> result = clientController.getClientById(1);

        assertEquals(client, result.getBody());
        verify(clientService, times(1)).getClientById(1);
    }

    @Test
    public void createClientTest() throws Exception {
        when(clientService.createClient(any(Client.class))).thenReturn(client);

        Client result = clientController.createClient(client);

        assertEquals(client, result);
        verify(clientService, times(1)).createClient(any(Client.class));
    }

    @Test
    public void updateClientTest() throws Exception {
        Client client = new Client();
        when(clientService.updateClient(eq(1), any(Client.class))).thenReturn(client);

        ResponseEntity<Client> result = clientController.updateClient(1, client);

        assertEquals(client, result.getBody());
        verify(clientService, times(1)).updateClient(eq(1), any(Client.class));
    }

    @Test
    public void deleteClientTest() throws Exception {
        HttpStatusCode isNoContent = HttpStatusCode.valueOf(204);
        doNothing().when(clientService).deleteClient(1);

        ResponseEntity<Void> result = clientController.deleteClient(1);

        assertEquals(isNoContent, result.getStatusCode());
        verify(clientService, times(1)).deleteClient(1);;
    }

}
