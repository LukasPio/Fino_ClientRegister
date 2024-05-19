package com.lucas.clientregister.Controller;

import com.lucas.clientregister.DTO.ClientRequestDTO;
import com.lucas.clientregister.Json;
import com.lucas.clientregister.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping(path = "/disabled")
    public ResponseEntity<Json> getAllDisabledClients() {
        return clientService.getAllDisabledClients();
    }
    @GetMapping
    public ResponseEntity<Json> getAllClients() {
        return clientService.getAllClients();
    }
    @GetMapping(path = "/{email}")
    public ResponseEntity<Json> getClientByEmail(@PathVariable String email) {
        return clientService.getClientByEmail(email);
    }
    @PostMapping(path = "/recovery")
    private ResponseEntity<Json> recoveryClient(@RequestBody String email) {
        return clientService.recoveryClient(email);
    }
    @PostMapping
    public ResponseEntity<Json> saveClient(@RequestBody ClientRequestDTO clientData) {
        return clientService.saveClient(clientData);
    }
    @PutMapping(path = "/{email}")
    public ResponseEntity<Json> updateClient(@PathVariable String email, @RequestBody ClientRequestDTO clientData) {
        return clientService.updateClient(clientData, email);
    }
    @DeleteMapping ("/{email}")
    public ResponseEntity<Json> deleteClient(@PathVariable String email) {
        return clientService.deleteClient(email);
    }
}
