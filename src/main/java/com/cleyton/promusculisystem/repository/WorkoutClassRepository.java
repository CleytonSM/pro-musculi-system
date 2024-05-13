package com.cleyton.promusculisystem.repository;

import com.cleyton.promusculisystem.model.WorkoutClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutClassRepository extends JpaRepository<WorkoutClass, Integer> {
}
