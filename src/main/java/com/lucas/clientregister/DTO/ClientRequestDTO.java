package com.lucas.clientregister.DTO;

import com.lucas.clientregister.Model.ClientModel;

import java.sql.Date;

public record ClientRequestDTO( String name, String surname, String email, Date birthdate) {
}
