package com.lucas.clientregister.Service;

import com.lucas.clientregister.ClientRegisterApplication;
import com.lucas.clientregister.DTO.ClientRequestDTO;
import com.lucas.clientregister.DTO.ClientResponseDTO;
import com.lucas.clientregister.Model.ClientModel;
import com.lucas.clientregister.Repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.extern.flogger.Flogger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public ResponseEntity<String> saveClient(ClientRequestDTO clientData) {
        if (clientRepository.existsByEmail(clientData.email())){
            ClientRegisterApplication.logger.info(
                    "Trying save a user with email: {} but already is registered.",
                    clientData.email()
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email: "+clientData.email()+" already is registered.");
        }
        clientRepository.save(new ClientModel(clientData));
        ClientRegisterApplication.logger.info("Client with email: {} was registered successfully.", clientData.email());
        return ResponseEntity.status(HttpStatus.CREATED).body("Client was registered successfully.");
    }
    public ResponseEntity<String> updateClient(ClientRequestDTO clientData, String email) {
        if (!clientRepository.existsByEmail(email))
        {
            ClientRegisterApplication.logger.warn("Trying update client with email: {} but is not registered", email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email: "+email+" is not registered.");
        }
        ClientModel client = clientRepository.findByEmail(email);
        client.setName(clientData.name());
        client.setSurname(clientData.surname());
        client.setEmail(clientData.email());
        client.setBirthdate(clientData.birthdate());
        client.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        clientRepository.save(client);
        ClientRegisterApplication.logger.info(
                "User with email: {} was updated successfully. new email: {}",
                email,
                clientData.email());
        return ResponseEntity.status(HttpStatus.OK).body("User was updated successfully.");
    }
    @Transactional
    public ResponseEntity<String> deleteClient(String email) {
        if (!clientRepository.existsByEmail(email))
        {
            ClientRegisterApplication.logger.warn("Trying delete client with email: {} but not registered", email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email: "+email+" is not registered.");
        }
        clientRepository.deleteByEmail(email);
        ClientRegisterApplication.logger.info("Client with email: {} was deleted successfully.", email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Client with email: "+email+" was deleted successfully.");
    }

    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<ClientModel> clientsModels = clientRepository.findAll();
        if (clientsModels.isEmpty()) {
            ClientRegisterApplication.logger.warn("Trying to view all clients, but none registered.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<ClientResponseDTO> clients = clientsModels.stream().map(ClientResponseDTO::new).toList();
        ClientRegisterApplication.logger.info("Get all clients of database.");
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }
}
