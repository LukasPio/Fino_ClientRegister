package clientregister.com.lucas.DTO;

import clientregister.com.lucas.Model.ClientModel;

import java.sql.Date;

public record ClientRequestDTO( String name, String surname, String email, Date birthdate) {
    public ClientRequestDTO(ClientModel clientModel) {
        this(
                clientModel.getName(),
                clientModel.getSurname(),
                clientModel.getEmail(),
                clientModel.getBirthdate()
        );
    }
}
