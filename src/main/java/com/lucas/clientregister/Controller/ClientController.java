package com.lucas.clientregister.Controller;

import com.lucas.clientregister.DTO.ClientRequestDTO;
import com.lucas.clientregister.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/clients")
public class ClientController {
    @Autowired
    ClientService clientService;
    @PostMapping
    public ResponseEntity<String> saveClient(@RequestBody ClientRequestDTO clientData) {
        return clientService.saveClient(clientData);
    }
    @PutMapping
    public ResponseEntity<String> updateClient(@RequestParam String email, @RequestBody ClientRequestDTO clientData) {
        return clientService.updateClient(clientData, email);
    }
}
