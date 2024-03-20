package com.cleyton.promusculisystem.repository;

import com.cleyton.promusculisystem.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    @Query("SELECT a FROM Authority a WHERE a.user.id = :id")
    Optional<Authority> findByUser(@Param("id") Integer id);
}
