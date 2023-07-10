package com.ams.backend.controller;

import com.ams.backend.exception.ResourceNotFoundException;
import com.ams.backend.entity.Client;
import com.ams.backend.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/client")
    public List<Client> getAllClients() {

        return clientService.getAllClients();
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") Long clientId)
            throws ResourceNotFoundException{
        Client client = clientService.getClientById(clientId);

        return ResponseEntity.ok().body(client);
    }

    @PostMapping("/client")
    public Client createClient(@Valid @RequestBody Client client)
    {
        return clientService.createClient(client);
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<Client> updateClient(
            @PathVariable(value = "id") Long clientId,
            @Valid @RequestBody Client clientDetails) throws ResourceNotFoundException {
        final Client updatedClient = clientService.updateClient(clientId, clientDetails);

        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable(value = "id") Long clientId)
            throws ResourceNotFoundException {
        clientService.deleteClient(clientId);

        return ResponseEntity.noContent().build();
    }
}
