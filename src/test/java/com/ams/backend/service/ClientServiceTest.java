package com.ams.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ams.backend.entity.Client;
import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImpl(clientRepository);

        // Crear un cliente de ejemplo
        client = new Client();
        client.setId(1);
        client.setClient("Test Client");
    }

    @Test
    public void testGetAllClients() {
        List<Client> clients = Arrays.asList(client);

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        assertEquals(1, result.size());
        assertEquals(client.getClient(), result.get(0).getClient());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void testGetClientById_ClientFound() throws ResourceNotFoundException {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));

        Client result = clientService.getClientById(1);

        assertNotNull(result);
        assertEquals(client.getClient(), result.getClient());
        verify(clientRepository, times(1)).findById(1);
    }

    @Test
    public void testGetClientById_ClientNotFound() {
        when(clientRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.getClientById(999));

        verify(clientRepository, times(1)).findById(999);
    }

    @Test
    public void testCreateClient() {
        Client newClient = new Client();
        newClient.setClient("New Client");

        when(clientRepository.save(newClient)).thenReturn(newClient);

        Client result = clientService.createClient(newClient);

        assertNotNull(result);
        assertEquals("New Client", result.getClient());
        verify(clientRepository, times(1)).save(newClient);
    }

    @Test
    public void testUpdateClient_ClientFound() throws ResourceNotFoundException {
        Client updatedClient = new Client();
        updatedClient.setClient("Updated Client");

        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        Client result = clientService.updateClient(1, updatedClient);

        assertNotNull(result);
        assertEquals("Updated Client", result.getClient());
        verify(clientRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateClient_ClientNotFound() {
        Client updatedClient = new Client();
        updatedClient.setClient("Updated Client");

        when(clientRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.updateClient(999, updatedClient));

        verify(clientRepository, times(1)).findById(999);
        verify(clientRepository, times(0)).save(updatedClient);  // No debe llamar a save
    }

    @Test
    public void testDeleteClient_ClientFound() throws ResourceNotFoundException {
        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).deleteById(1);

        clientService.deleteClient(1);

        verify(clientRepository, times(1)).findById(1);
        verify(clientRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteClient_ClientNotFound() {
        when(clientRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clientService.deleteClient(999));

        verify(clientRepository, times(1)).findById(999);
        verify(clientRepository, times(0)).deleteById(999);  // No debe eliminarse
    }
}
