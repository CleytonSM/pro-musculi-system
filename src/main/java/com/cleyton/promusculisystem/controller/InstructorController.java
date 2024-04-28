package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.dto.InstructorDto;
import com.cleyton.promusculisystem.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorService service;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerInstructor(@RequestBody InstructorDto instructorDto) {
        service.createInstructor(instructorDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/find/")
    public ResponseEntity<?> findInstructorByCpf(@RequestParam("cpf") String cpf) {
        return new ResponseEntity<>(service.findInstructorByCpf(cpf), HttpStatus.OK);
    }
}
