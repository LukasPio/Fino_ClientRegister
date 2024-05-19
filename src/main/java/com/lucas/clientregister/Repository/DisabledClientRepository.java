package com.lucas.clientregister.Repository;

import com.lucas.clientregister.Model.DisabledClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisabledClientRepository extends JpaRepository<DisabledClientModel, Integer> {
    boolean existsByEmail(String email);
    Optional<DisabledClientModel> findByEmail(String email);
    void deleteByEmail(String email);
}
