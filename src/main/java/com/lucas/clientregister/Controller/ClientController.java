package com.lucas.clientregister.Controller;

import com.lucas.clientregister.ClientRegisterApplication;
import com.lucas.clientregister.DTO.ClientRequestDTO;
import com.lucas.clientregister.DTO.ClientResponseDTO;
import com.lucas.clientregister.Service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/clients")
public class ClientController {
    private final ClientService clientService;
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        long actualTime = System.currentTimeMillis();
        ResponseEntity<List<ClientResponseDTO>> response = clientService.getAllClients();
        ClientRegisterApplication.applicationLogger.info("Returned in {}ms", System.currentTimeMillis() - actualTime);
        return response;
    }
    @PostMapping
    public ResponseEntity<String> saveClient(@RequestBody ClientRequestDTO clientData) {
        long actualTime = System.currentTimeMillis();
        ResponseEntity<String> response = clientService.saveClient(clientData);
        ClientRegisterApplication.applicationLogger.info("Returned in {}ms", System.currentTimeMillis() - actualTime);
        return response;
    }
    @PutMapping
    public ResponseEntity<String> updateClient(@RequestParam String email, @RequestBody ClientRequestDTO clientData) {
        long actualTime = System.currentTimeMillis();
        ResponseEntity<String> response = clientService.updateClient(clientData, email);
        ClientRegisterApplication.applicationLogger.info("Returned in {}ms", System.currentTimeMillis() - actualTime);
        return response;
    }
    @DeleteMapping ("/{email}")
    public ResponseEntity<String> deleteClient(@PathVariable String email) {
        long actualTime = System.currentTimeMillis();
        ResponseEntity<String> response = clientService.deleteClient(email);
        ClientRegisterApplication.applicationLogger.info("Returned in {}ms", System.currentTimeMillis() - actualTime);
        return response;
    }
}
