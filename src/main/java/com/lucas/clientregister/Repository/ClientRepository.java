package com.lucas.clientregister.Repository;

import com.lucas.clientregister.Model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientModel, Integer> {
    public boolean existsByEmail(String email);
    public ClientModel findByEmail(String email);
}
