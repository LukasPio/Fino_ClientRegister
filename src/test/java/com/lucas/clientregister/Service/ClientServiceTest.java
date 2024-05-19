package com.lucas.clientregister.Service;

import com.lucas.clientregister.DTO.ClientRequestDTO;
import com.lucas.clientregister.Model.ClientModel;
import com.lucas.clientregister.Model.DisabledClientModel;
import com.lucas.clientregister.Repository.ClientRepository;
import com.lucas.clientregister.Repository.DisabledClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DisabledClientRepository disabledClientRepository;

    @InjectMocks
    private final ClientService clientService;

    public ClientServiceTest() {
        clientService = new ClientService();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Return code 200 when a client is successfully registered")
    void saveClient01(){
        ClientRequestDTO clientData = new ClientRequestDTO(
                "lucas",
                "pio",
                "lucas@gmail.com",
                new Date(9999999)
        );

        when(clientRepository.existsByEmail(clientData.email())).thenReturn(false);
        when(disabledClientRepository.existsByEmail(clientData.email())).thenReturn(false);

        clientService.saveClient(clientData);

        verify(clientRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Return code 409 when client's email already is registered")
    void saveClient02(){
        ClientRequestDTO clientData = new ClientRequestDTO(
                "lucas",
                "pio",
                "lucas@gmail.com",
                new Date(9999999)
        );

        when(clientRepository.existsByEmail(clientData.email())).thenReturn(true);
        when(disabledClientRepository.existsByEmail(clientData.email())).thenReturn(false);

        Assertions.assertEquals(clientService.saveClient(clientData).getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    @DisplayName("Return code 200 when a client is updated successfully")
    void updateClient01(){
        ClientRequestDTO clientData = new ClientRequestDTO(
                "lucas",
                "pio",
                "lucas@gmail.com",
                new Date(9999999)
        );

        String originalClientEmail = "lucas@gmail.com";

        when(clientRepository.findByEmail(clientData.email())).thenReturn(Optional.of(new ClientModel()));

        Assertions.assertEquals(clientService.updateClient(clientData, originalClientEmail).getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Return code 404 when try update a client that is not registered")
    void updateClient02(){
        ClientRequestDTO clientData = new ClientRequestDTO(
                "lucas",
                "pio",
                "lucas@gmail.com",
                new Date(9999999)
        );

        String originalClientEmail = "lucas@gmail.com";

        when(clientRepository.findByEmail(clientData.email())).thenReturn(Optional.empty());

        Assertions.assertEquals(clientService.updateClient(clientData, originalClientEmail).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Return code 200 when a client is deleted successfully")
    void deleteClient01(){
        String emailToDelete = "lucas@gmail.com";
        ClientModel clientModel = new ClientModel();
        when(clientRepository.findByEmail(emailToDelete)).thenReturn(Optional.of(clientModel));
        when(disabledClientRepository.save(new DisabledClientModel(clientModel))).thenReturn(any());
        Assertions.assertEquals(clientService.deleteClient(emailToDelete).getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Return code 404 when try delete a client that is not registered")
    void deleteClient02(){
        String emailToDelete = "lucas@gmail.com";
        when(clientRepository.findByEmail(emailToDelete)).thenReturn(Optional.empty());
        Assertions.assertEquals(clientService.deleteClient(emailToDelete).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Return code 200 when a client is got successfully")
    void getClientByEmail01(){
        String email = "lucas@gmail.com";

        when(clientRepository.findByEmail(email)).thenReturn(Optional.of(new ClientModel()));

        Assertions.assertEquals(clientService.getClientByEmail(email).getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Return code 404 when try get a client that is not registered")
    void getClientByEmail02(){
        String email = "lucas@gmail.com";

        when(clientRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertEquals(clientService.getClientByEmail(email).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Return code 200 when get all registered clients successfully")
    void getAllClients01(){

        when(clientRepository.findAll()).thenReturn(List.of(new ClientModel()));

        Assertions.assertEquals(clientService.getAllClients().getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Return code 404 when try get all clients but none client is registered")
    void getAllClients02(){

        when(clientRepository.findAll()).thenReturn(List.of());

        Assertions.assertEquals(clientService.getAllClients().getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Return code 200 when get all disabled clients successfully")
    void getAllDisabledClients01(){

        when(disabledClientRepository.findAll()).thenReturn(List.of(new DisabledClientModel()));

        Assertions.assertEquals(clientService.getAllDisabledClients().getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Return code 404 when try get all clients but none client is disabled")
    void getAllDisabledClients02(){

        when(disabledClientRepository.findAll()).thenReturn(List.of());

        Assertions.assertEquals(clientService.getAllDisabledClients().getStatusCode(), HttpStatus.NOT_FOUND);
    }

}