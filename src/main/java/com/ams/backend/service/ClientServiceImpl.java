package com.ams.backend.service;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Client;
import com.ams.backend.repository.ClientRepository;
import com.ams.backend.service.interfaces.ClientService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(int id) throws ResourceNotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + id));
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(int id, Client providedClient) throws ResourceNotFoundException {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + id));

        client.setClient(providedClient.getClient());
        clientRepository.save(client);
        return client;
    }

    public void deleteClient(int id) throws ResourceNotFoundException{
        clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + id));
        clientRepository.deleteById(id);
    }
}
