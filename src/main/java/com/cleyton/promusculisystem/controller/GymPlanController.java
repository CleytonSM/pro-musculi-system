package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.GymPlan;
import com.cleyton.promusculisystem.model.dto.GymPlanDto;
import com.cleyton.promusculisystem.model.dto.PageResponse;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.services.GymPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gymplan")
public class GymPlanController {
    @Autowired
    private GymPlanService service;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createGymPlan(@RequestBody GymPlanDto gymPlanDto) {
        service.createGymPlan(gymPlanDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResponse<GymPlan>> findAllGymPlans(@RequestBody PaginationDto paginationDto){
        return new ResponseEntity<>(service.findAllGymPlans(paginationDto), HttpStatus.OK);
    }

    @GetMapping("/find/")
    public ResponseEntity<GymPlan> findByName(@RequestParam("name") String name){
        return new ResponseEntity<>(service.findByName(name), HttpStatus.OK);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<HttpStatus> deleteGymPlan(@RequestParam("name") String name) {
        service.delete(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
