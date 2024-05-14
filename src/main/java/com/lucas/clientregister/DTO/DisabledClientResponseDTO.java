package com.lucas.clientregister.DTO;

import com.lucas.clientregister.Model.DisabledClientModel;

import java.sql.Timestamp;
import java.util.Date;

public record DisabledClientResponseDTO(String name, String surname, String email, Date birthdate, Timestamp disabled_at) {

    public DisabledClientResponseDTO(DisabledClientModel clientModel) {
        this(clientModel.getName(), clientModel.getSurname(), clientModel.getEmail(), clientModel.getBirthdate(), clientModel.getDisabled_at());
    }
}
