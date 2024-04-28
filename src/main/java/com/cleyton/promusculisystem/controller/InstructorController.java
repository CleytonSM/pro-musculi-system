package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.dto.InstructorDto;
import com.cleyton.promusculisystem.services.InstructorService;
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

    @PutMapping("/update/")
    public ResponseEntity<HttpStatus> updateInstructorByCpf(@RequestParam("cpf") String cpf, @RequestBody InstructorDto instructorDto) {
        service.updateInstructorByCpf(cpf, instructorDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/partial_update/")
    public ResponseEntity<HttpStatus> patchInstructorByCpf(@RequestParam("cpf") String cpf, @RequestBody InstructorDto instructorDto) {
        service.patchInstructorByCpf(cpf, instructorDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<HttpStatus> deleteInstructorByCpf(@RequestParam("cpf") String cpf) {
        service.deleteInstructorByCpf(cpf);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/reactivate/")
    public ResponseEntity<HttpStatus> reactivateInstructorByCpf(@RequestParam("cpf") String cpf) {
        service.reactivateInstructByCpf(cpf);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
