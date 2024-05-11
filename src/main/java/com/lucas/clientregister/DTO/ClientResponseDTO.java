package com.lucas.clientregister.DTO;

import com.lucas.clientregister.Model.ClientModel;

import java.sql.Date;

public record ClientResponseDTO(String name, String surname, String email, Date birthdate) {
    public ClientResponseDTO(ClientModel clientModel) {
        this(
                clientModel.getName(),
                clientModel.getSurname(),
                clientModel.getEmail(),
                clientModel.getBirthdate()
        );
}
}
