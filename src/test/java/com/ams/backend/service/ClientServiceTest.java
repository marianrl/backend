package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Client;
import com.ams.backend.repository.ClientRepository;
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
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    @Before
    public void setup() {
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void testGetAllClientes() {
        List<Client> expectedClientes = new ArrayList<>();
        Mockito.when(clientRepository.findAll()).thenReturn(expectedClientes);
        List<Client> actualClientes = clientService.getAllClients();

        assertEquals(expectedClientes, actualClientes);
    }

    @Test
    public void testGetClientById() throws ResourceNotFoundException {
        int clientId = 1;
        Client expectedClient = new Client(clientId, "CABA");

        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(expectedClient));
        Client actualClient = clientService.getClientById(clientId);

        assertEquals(expectedClient, actualClient);
    }

    @Test
    public void testCreateClient() {
        int clientId = 1;
        Client client = new Client(clientId, "CABA");

        Mockito.when(clientRepository.save(client)).thenReturn(client);
        Client actualClient = clientService.createClient(client);

        assertEquals(actualClient, client);
    }

    @Test
    public void testUpdateClient() throws ResourceNotFoundException {
        int clientId = 1;
        Client client = new Client(clientId, "CABA");
        Client updatedClient = new Client(clientId, "GBA");

        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        Client actualClient = clientService.updateClient(clientId, updatedClient);

        assertEquals(updatedClient.getId(), actualClient.getId());
        assertEquals(updatedClient.getClient(), actualClient.getClient());
    }

    @Test
    public void testDeleteClient() throws ResourceNotFoundException {
        int clientId = 1;
        Client client = new Client(clientId, "CABA");

        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        clientService.deleteClient(clientId);

        verify(clientRepository).deleteById(1);
    }
}

