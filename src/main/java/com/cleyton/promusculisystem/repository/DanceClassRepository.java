package com.cleyton.promusculisystem.repository;

import com.cleyton.promusculisystem.model.DanceClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface DanceClassRepository extends JpaRepository<DanceClass, Integer> {

    @Query("SELECT dc FROM DanceClass dc WHERE dc.active = TRUE")
    Optional<DanceClass> findByStartAndEnd(LocalDateTime start, LocalDateTime end);
    @Query("SELECT dc FROM DanceClass dc WHERE dc.active = TRUE")
    Optional<DanceClass> findByName(String name);
    @Query("SELECT dc FROM DanceClass dc WHERE dc.instructor.name = :name AND dc.active = TRUE")
    Page<DanceClass> findAllByInstructorName(@Param("name") String name, Pageable pageable);
    @Query("SELECT dc FROM DanceClass dc WHERE dc.active = FALSE")
    Optional<DanceClass> findInactiveById(Integer id);
    @Query("SELECT dc FROM DanceClass dc WHERE dc.active = FALSE")
    Page<DanceClass> findAllInactiveByInstructor(String instructorName, Pageable pageable);
}
