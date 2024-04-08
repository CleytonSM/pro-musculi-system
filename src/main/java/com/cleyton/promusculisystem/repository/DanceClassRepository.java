package com.cleyton.promusculisystem.repository;

import com.cleyton.promusculisystem.model.DanceClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DanceClassRepository extends JpaRepository<DanceClass, Integer> {

    Optional<DanceClass> findByStartAndEnd(LocalDateTime start, LocalDateTime end);

}
