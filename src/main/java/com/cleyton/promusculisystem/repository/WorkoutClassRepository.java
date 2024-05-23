package com.cleyton.promusculisystem.repository;

import com.cleyton.promusculisystem.model.WorkoutClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface WorkoutClassRepository extends JpaRepository<WorkoutClass, Integer> {
    @Query("SELECT wc FROM WorkoutClass wc WHERE wc.active = TRUE")
    Optional<WorkoutClass> findByDateClass(LocalDateTime dateClass);

    @Query("SELECT wc FROM WorkoutClass wc WHERE wc.active = TRUE")
    Optional<WorkoutClass> findActiveById(Integer id);

    @Query("SELECT wc FROM WorkoutClass wc WHERE wc.active = FALSE")
    Optional<WorkoutClass> findInactiveById(Integer id);
}

