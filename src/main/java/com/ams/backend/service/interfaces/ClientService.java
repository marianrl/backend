package com.ams.backend.service.interfaces;

import java.util.List;

import com.ams.backend.entity.Client;
import com.ams.backend.exception.ResourceNotFoundException;

public interface ClientService {
  List<Client> getAllClients();
  Client getClientById(int id) throws ResourceNotFoundException;
  Client createClient(Client client);
  Client updateClient(int id, Client providedClient) throws ResourceNotFoundException;
  void deleteClient(int id) throws ResourceNotFoundException;
}

