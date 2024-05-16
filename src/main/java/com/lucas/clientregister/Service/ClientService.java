package com.lucas.clientregister.Service;

import com.lucas.clientregister.DTO.ClientRequestDTO;
import com.lucas.clientregister.DTO.ClientResponseDTO;
import com.lucas.clientregister.DTO.DisabledClientResponseDTO;
import com.lucas.clientregister.Model.DisabledClientModel;
import com.lucas.clientregister.Repository.DisabledClientRepository;
import com.lucas.clientregister.utils.Logger;
import com.lucas.clientregister.Model.ClientModel;
import com.lucas.clientregister.Repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DisabledClientRepository disabledClientRepository;

    public ResponseEntity<String> saveClient(ClientRequestDTO clientData) {
        if (clientRepository.existsByEmail(clientData.email())){
            Logger.warn("Trying save a user with email: "+clientData.email()+" but already is registered");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email: "+clientData.email()+" already is registered");
        }
        if (disabledClientRepository.existsByEmail(clientData.email())) {
            Logger.warn("Trying save a user with email: "+clientData.email()+" but is disabled");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email: "+clientData.email()+" is disabled contact our support.");
        }
        clientRepository.save(new ClientModel(clientData));
        Logger.info("Client with email: "+clientData.email()+" was registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body("Client was registered successfully");
    }

    public ResponseEntity<String> updateClient(ClientRequestDTO clientData, String email) {
        Optional<ClientModel> optionalClient = clientRepository.findByEmail(email);
        if (optionalClient.isEmpty())
        {
            Logger.warn("Trying update client with email: "+email+" but is not registered");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email: "+email+" is not registered");
        }
        ClientModel client = optionalClient.get();
        client.update(clientData);
        clientRepository.save(client);
        Logger.info("User with email: "+email+" was updated successfully. new email: "+clientData.email());
        return ResponseEntity.status(HttpStatus.OK).body("User was updated successfully");
    }

    @Transactional
    public ResponseEntity<String> deleteClient(String email) {
        Optional<ClientModel> clientModel = clientRepository.findByEmail(email);
        if (clientModel.isEmpty()) {
            Logger.warn("Trying delete client with email: " + email + "but not registered");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email: " + email + " is not registered");
        }
        clientRepository.deleteByEmail(email);
        disabledClientRepository.save(new DisabledClientModel(clientModel.get()));
        Logger.info("Client with email: "+email+" was deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body("Client with email: "+email+" was deleted successfully");
    }

    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<ClientModel> clientsModels = clientRepository.findAll();
        List<ClientResponseDTO> clients = clientsModels.stream().map(ClientResponseDTO::new).toList();
        if (clientsModels.isEmpty()) {
            Logger.warn("Trying to view all clients, but none registered.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(clients);
        }
        Logger.info("Getting all clients of database.");
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    public ResponseEntity<List<DisabledClientResponseDTO>> getAllDisabledClients() {
        List<DisabledClientModel> disabledClients = disabledClientRepository.findAll();
        List<DisabledClientResponseDTO> disabledClientsData = disabledClients.stream().map(DisabledClientResponseDTO::new).toList();
        if (disabledClientsData.isEmpty()) {
            Logger.warn("Trying to view all disabled clients, but not have disabled clients.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(disabledClientsData);
        }
        Logger.info("Getting all disable clients of database.");
        return ResponseEntity.status(HttpStatus.OK).body(disabledClientsData);
    }
}
