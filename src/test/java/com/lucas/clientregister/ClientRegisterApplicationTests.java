package com.lucas.clientregister;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import com.lucas.clientregister.DTO.ClientRequestDTO;
import com.lucas.clientregister.Model.ClientModel;
import com.lucas.clientregister.Repository.ClientRepository;
import com.lucas.clientregister.Service.ClientService;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientRegisterApplicationTests.class)
class ClientRegisterApplicationTests {


    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void testSaveClient() {
        ClientRequestDTO clientData = new ClientRequestDTO("John", "Doe", "john.doe@example.com", null);

        when(clientRepository.existsByEmail(clientData.email())).thenReturn(false);

        ResponseEntity<String> response = clientService.saveClient(clientData);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Client was registered successfully", response.getBody());
        verify(clientRepository, times(1)).save(any(ClientModel.class));
    }

    @Test
    public void testUpdateClient() {
        String email = "john.doe@example.com";
        ClientRequestDTO clientData = new ClientRequestDTO("John", "Doe", "john.doe@example.com", null);
        ClientModel existingClient = new ClientModel();
        existingClient.setEmail(email);

        when(clientRepository.existsByEmail(email)).thenReturn(true);
        when(clientRepository.findByEmail(email)).thenReturn(existingClient);

        ResponseEntity<String> response = clientService.updateClient(clientData, email);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User was updated successfully", response.getBody());
        verify(clientRepository, times(1)).save(existingClient);
    }

    @Test
    public void testDeleteClient() {
        String email = "john.doe@example.com";
        ClientModel existingClient = new ClientModel();
        existingClient.setEmail(email);

        when(clientRepository.existsByEmail(email)).thenReturn(true);
        doNothing().when(clientRepository).deleteByEmail(email);

        ResponseEntity<String> response = clientService.deleteClient(email);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Client with email: " + email + " was deleted successfully", response.getBody());
        verify(clientRepository, times(1)).deleteByEmail(email);
    }


}
