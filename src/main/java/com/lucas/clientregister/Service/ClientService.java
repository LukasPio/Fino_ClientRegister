package com.lucas.clientregister.Service;

import com.lucas.clientregister.DTO.ClientRequestDTO;
import com.lucas.clientregister.Model.ClientModel;
import com.lucas.clientregister.Repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public ResponseEntity<String> saveClient(ClientRequestDTO clientData) {
        if (clientRepository.existsByEmail(clientData.email()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email: "+clientData.email()+" already is registered.");
        clientRepository.save(new ClientModel(clientData));
        return ResponseEntity.status(HttpStatus.CREATED).body("Client was registered successfully.");
    }
    public ResponseEntity<String> updateClient(ClientRequestDTO clientData, String email) {
        if (!clientRepository.existsByEmail(email))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email: "+email+" is not registered.");
        ClientModel client = clientRepository.findByEmail(email);
        client.setName(clientData.name());
        client.setSurname(clientData.surname());
        client.setEmail(clientData.email());
        client.setBirthdate(clientData.birthdate());
        client.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        clientRepository.save(client);
        return ResponseEntity.status(HttpStatus.OK).body("User was updated successfully.");
    }
    @Transactional
    public ResponseEntity<String> deleteClient(String email) {
        if (!clientRepository.existsByEmail(email))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email: "+email+" is not registered.");
        clientRepository.deleteByEmail(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Client with email: "+email+" was deleted successfully.");
    }
}
