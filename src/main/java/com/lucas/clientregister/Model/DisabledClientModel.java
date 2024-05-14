package com.lucas.clientregister.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "disabled_clients")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DisabledClientModel {
    @Id
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private Date birthdate;
    private Timestamp disabled_at;

    public DisabledClientModel(ClientModel clientModel) {
        this.id = clientModel.getId();
        this.name = clientModel.getName();
        this.surname = clientModel.getSurname();
        this.email = clientModel.getEmail();
        this.birthdate = clientModel.getBirthdate();
        this.disabled_at = new Timestamp(System.currentTimeMillis());
    }
}
