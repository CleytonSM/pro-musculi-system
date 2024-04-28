package com.cleyton.promusculisystem.repository;

import com.cleyton.promusculisystem.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    @Query("SELECT i FROM Instructor i WHERE i.cpf = :cpf AND i.active = TRUE")
    Optional<Instructor> findByCpf(@Param("cpf") String cpf);
}
