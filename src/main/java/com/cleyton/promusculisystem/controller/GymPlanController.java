package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.GymPlan;
import com.cleyton.promusculisystem.model.dto.GymPlanDto;
import com.cleyton.promusculisystem.services.GymPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gymplan/")
public class GymPlanController {
    @Autowired
    private GymPlanService service;

    @PostMapping("/register")
    public ResponseEntity<?> createGymPlan(@RequestBody GymPlanDto gymPlanDto) {
        service.createGymPlan(gymPlanDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
