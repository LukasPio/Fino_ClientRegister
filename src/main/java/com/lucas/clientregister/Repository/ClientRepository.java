package com.lucas.clientregister.Repository;

import com.lucas.clientregister.Model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientModel, Integer> {
    public boolean existsByEmail(String email);
    public Optional<ClientModel> findByEmail(String email);
    public void deleteByEmail(String email);
}
