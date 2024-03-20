package com.cleyton.promusculisystem.repository;

import com.cleyton.promusculisystem.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    //TODO create a query in JPQL to filter find all not including deleted users
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.active != false")
    List<User> findAllActive();
}
