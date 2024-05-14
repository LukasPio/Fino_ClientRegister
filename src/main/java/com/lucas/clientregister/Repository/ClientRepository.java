package com.lucas.clientregister.Repository;

import com.lucas.clientregister.Model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientModel, Integer> {
    boolean existsByEmail(String email);
    Optional<ClientModel> findByEmail(String email);
    void deleteByEmail(String email);
}
