package com.lucas.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class ClientModel {
    @Id
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Date birthdate;
    private Timestamp created_at;
    private Timestamp update_at;
}