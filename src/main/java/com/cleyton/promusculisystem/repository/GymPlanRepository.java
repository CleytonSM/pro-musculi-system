package com.cleyton.promusculisystem.repository;

import com.cleyton.promusculisystem.model.GymPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GymPlanRepository extends JpaRepository<GymPlan, Integer> {

    Optional<GymPlan> findByName(String name);

    @Query("SELECT gp FROM GymPlan gp")
    Page<GymPlan> findAllGymPlans(Pageable pageable);
}
