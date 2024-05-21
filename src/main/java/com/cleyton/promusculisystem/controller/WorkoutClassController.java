package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.WorkoutClass;
import com.cleyton.promusculisystem.model.dto.WorkoutClassDTO;
import com.cleyton.promusculisystem.services.WorkoutClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/find/")
    public ResponseEntity<WorkoutClass> findWorkoutClassById(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(service.findWorkoutClassById(id), HttpStatus.OK);
    }

    @GetMapping("/find/inactive/")
    public ResponseEntity<WorkoutClass> findInactiveWorkoutClassById(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(service.findInactiveWorkoutClassById(id), HttpStatus.OK);
    }

    @PutMapping("/update/")
    public ResponseEntity<HttpStatus> updateWorkoutClassById(@RequestParam("id") Integer id,
                                                             @RequestBody WorkoutClassDTO workoutClassDTO) {
        service.updateWorkoutClassById(id, workoutClassDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/partial/")
    public ResponseEntity<HttpStatus> patchWorkoutClassById(@RequestParam("id") Integer id,
                                                            @RequestBody WorkoutClassDTO workoutClassDTO) {
        service.patchWorkoutClassById(id, workoutClassDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<HttpStatus> deleteWorkoutClassById(@RequestParam("id") Integer id) {
        service.deleteWorkoutClassById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reactivate/")
    public ResponseEntity<HttpStatus> reactivateWorkoutClassById(@RequestParam("id") Integer id) {
        service.reactivateWorkoutClassById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
