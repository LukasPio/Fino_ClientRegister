package com.lucas.clientregister.Controller;

import com.lucas.clientregister.DTO.ClientRequestDTO;
import com.lucas.clientregister.Service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/clients")
public class ClientController {
    private final ClientService clientService;
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    @PostMapping
    public ResponseEntity<String> saveClient(@RequestBody ClientRequestDTO clientData) {
        return clientService.saveClient(clientData);
    }
    @PutMapping
    public ResponseEntity<String> updateClient(@RequestParam String email, @RequestBody ClientRequestDTO clientData) {
        return clientService.updateClient(clientData, email);
    }
    @DeleteMapping ("/{email}")
    public ResponseEntity<String> deleteClient(@PathVariable String email) {
        return clientService.deleteClient(email);
    }
}
