package com.cleyton.promusculisystem.repository;

import com.cleyton.promusculisystem.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    Optional<Object> findByCpf(String cpf);
}
