package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.dto.DanceClassDto;
import com.cleyton.promusculisystem.services.DanceClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/danceclass")
public class DanceClassController {

    @Autowired
    private DanceClassService service;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createDanceClass(@RequestBody DanceClassDto danceClassDto) {
        service.createDanceClass(danceClassDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
