package com.lucas.clientregister.Service;

import com.lucas.clientregister.DTO.ClientRequestDTO;
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
    void saveClient01() {
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
    @DisplayName("Return code 401 when client's email is disabled")
    void saveClient02(){
        ClientRequestDTO clientData = new ClientRequestDTO(
                "lucas",
                "pio",
                "lucas@gmail.com",
                new Date(9999999)
        );

        when(clientRepository.existsByEmail(clientData.email())).thenReturn(false);
        when(disabledClientRepository.existsByEmail(clientData.email())).thenReturn(true);

        Assertions.assertEquals(clientService.saveClient(clientData).getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    @DisplayName("Return code 409 when client's email already is registered")
    void saveClient03(){
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
}