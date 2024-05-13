package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.dto.WorkoutClassDTO;
import com.cleyton.promusculisystem.services.WorkoutClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workoutclass")
public class WorkoutClassController {

    @Autowired
    private WorkoutClassService service;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerWorkoutClass(@RequestBody WorkoutClassDTO workoutClassDTO) {
        service.createWorkoutClass(workoutClassDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
