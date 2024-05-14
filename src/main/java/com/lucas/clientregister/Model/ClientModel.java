package com.lucas.clientregister.Model;

import com.lucas.clientregister.DTO.ClientRequestDTO;
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
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientModel {
    @Id
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private Date birthdate;
    private Timestamp created_at;
    private Timestamp updated_at;

    public ClientModel (ClientRequestDTO clientRequestDTO) {
        this.name = clientRequestDTO.name();
        this.surname = clientRequestDTO.surname();
        this.email = clientRequestDTO.email();
        this.birthdate = clientRequestDTO.birthdate();
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }
}