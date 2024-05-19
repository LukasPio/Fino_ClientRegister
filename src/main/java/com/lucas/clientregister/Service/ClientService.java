package com.lucas.clientregister.Service;

import com.lucas.clientregister.DTO.ClientListResponseDTO;
import com.lucas.clientregister.DTO.ClientRequestDTO;
import com.lucas.clientregister.DTO.ClientResponseDTO;
import com.lucas.clientregister.DTO.DisabledClientListResponseDTO;
import com.lucas.clientregister.Json;
import com.lucas.clientregister.Model.ClientModel;
import com.lucas.clientregister.Model.DisabledClientModel;
import com.lucas.clientregister.Repository.ClientRepository;
import com.lucas.clientregister.Repository.DisabledClientRepository;
import com.lucas.clientregister.logs.Logger;
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

    public ResponseEntity<Json> saveClient(ClientRequestDTO clientData) {
        long elapsedTime = System.currentTimeMillis();
        if (clientRepository.existsByEmail(clientData.email())){
            Logger.warn("Trying save a user with email: "+clientData.email()+" but already is registered");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new Json(
                            "Email: "+clientData.email()+" already is registered",
                            String.valueOf(System.currentTimeMillis() - elapsedTime),
                            "409 - Conflict"
                            )
            );
        }

        if (disabledClientRepository.existsByEmail(clientData.email()))
                disabledClientRepository.deleteByEmail(clientData.email());

        clientRepository.save(new ClientModel(clientData));
        Logger.info("Client with email: "+clientData.email()+" was registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new Json(
                        "Client was registered successfully",
                        String.valueOf(System.currentTimeMillis() - elapsedTime)
                )
        );
    }

    public ResponseEntity<Json> updateClient(ClientRequestDTO clientData, String email) {
        long elapsedTime = System.currentTimeMillis();
        Optional<ClientModel> optionalClient = clientRepository.findByEmail(email);
        if (optionalClient.isEmpty())
        {
            Logger.warn("Trying update client with email: "+email+" but is not registered");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Json(
                            "Email: "+email+" is not registered",
                            String.valueOf(System.currentTimeMillis() - elapsedTime),
                            "404 - Not Found"
                    )
            );
        }
        ClientModel client = optionalClient.get();
        client.update(clientData);
        clientRepository.save(client);
        Logger.info("User with email: "+email+" was updated successfully. new email: "+clientData.email());
        return ResponseEntity.status(HttpStatus.OK).body(
                new Json(
                        "User was updated successfully",
                        String.valueOf(System.currentTimeMillis() - elapsedTime)
                )
        );
    }

    @Transactional
    public ResponseEntity<Json> deleteClient(String email) {
        long elapsedTime = System.currentTimeMillis();
        Optional<ClientModel> clientModel = clientRepository.findByEmail(email);
        if (clientModel.isEmpty()) {
            Logger.warn("Trying delete client with email: " + email + " but not registered");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Json(
                            "Email: " + email + " is not registered",
                            String.valueOf(System.currentTimeMillis() - elapsedTime),
                            "404 - Not Found"
                    )
            );
        }
        clientRepository.deleteByEmail(email);
        disabledClientRepository.save(new DisabledClientModel(clientModel.get()));
        Logger.info("Client with email: "+email+" was deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(
                new Json(
                        "Client with email: "+email+" was deleted successfully",
                        String.valueOf(System.currentTimeMillis() - elapsedTime)
                )
        );
    }

    public ResponseEntity<Json> getAllClients() {
        long elapsedTime = System.currentTimeMillis();
        List<ClientModel> clientsModels = clientRepository.findAll();
        if (clientsModels.isEmpty()) {
            Logger.warn("Trying to view all clients but none registered");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Json(
                            "None clients are registered",
                            String.valueOf(System.currentTimeMillis() - elapsedTime),
                            "404 - Not Found"
                    )
            );
        }
        Logger.info("Getting all clients of database");
        return ResponseEntity.status(HttpStatus.OK).body(
                new Json(
                        new ClientListResponseDTO(clientsModels),
                        "All clients get successfully",
                        String.valueOf(System.currentTimeMillis() - elapsedTime)
                )
        );
    }

    public ResponseEntity<Json> getAllDisabledClients() {
        long elapsedTime = System.currentTimeMillis();
        List<DisabledClientModel> disabledClients = disabledClientRepository.findAll();
        if (disabledClients.isEmpty()) {
            Logger.warn("Trying to view all disabled clients but not have disabled clients");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Json(
                            "None clients are disabled",
                            String.valueOf(System.currentTimeMillis() - elapsedTime),
                            "404 - Not Found"
                    )
            );
        }
        Logger.info("Getting all disable clients of database");
        return ResponseEntity.status(HttpStatus.OK).body(
                new Json(
                        new DisabledClientListResponseDTO(disabledClients),
                        "Getting all clients successfully",
                        String.valueOf(System.currentTimeMillis() - elapsedTime)
                )
        );
    }

    public ResponseEntity<Json> getClientByEmail(String email) {
        long elapsedTime = System.currentTimeMillis();
        Optional<ClientModel> client = clientRepository.findByEmail(email);
        if (client.isEmpty()) {
            Logger.warn("Trying to get client with email: " + email + " but is not registered.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Json(
                            "None client registered with email: " + email,
                            String.valueOf(System.currentTimeMillis() - elapsedTime),
                            "404 - Not Found"
                    )
            );
        }
        ClientResponseDTO clientToReturn = new ClientResponseDTO(client.get());
        Logger.info("Get client with email: " + email + " successfully");
        return ResponseEntity.status(HttpStatus.OK).body(
                new Json(
                        clientToReturn,
                        "Get client with email: " + email + " successfully",
                        String.valueOf(System.currentTimeMillis() - elapsedTime)
                )
        );
    }
    @Transactional
    public ResponseEntity<Json> recoveryClient(String email) {
        long elapsedTime = System.currentTimeMillis();
        Optional<DisabledClientModel> client = disabledClientRepository.findByEmail(email);
        if (client.isEmpty()) {
            Logger.warn("Trying to recovery client with email: "+ email +" but is not disabled.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Json(
                            "None client disabled with email: " + email,
                            String.valueOf(System.currentTimeMillis() - elapsedTime),
                            "404 - Not Found"
                    )
            );
        }
        ClientModel clientModel = new ClientModel(client.get());
        disabledClientRepository.deleteByEmail(email);
        clientRepository.save(clientModel);

        Logger.info("Recovery client with email: " + email + " successfully");
        return ResponseEntity.status(HttpStatus.OK).body(
                new Json(
                        "Recovery client disabled at: "+ client.get().getDisabled_at() +" with email: " + email + " successfully",
                        String.valueOf(System.currentTimeMillis() - elapsedTime)
                )
        );
    }
}
