package com.cleyton.promusculisystem.repository;

import com.cleyton.promusculisystem.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);
    Optional<Client> findByName(String name);

    @Query("SELECT c FROM Client c WHERE c.active != false")
    Page<Client> findAllActive(Pageable pageable);

    @Query("SELECT c FROM Client c WHERE c.active = false")
    Page<Client> findAllInactive(Pageable pageable);
}
