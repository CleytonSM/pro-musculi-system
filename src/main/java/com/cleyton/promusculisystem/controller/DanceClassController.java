package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.DanceClass;
import com.cleyton.promusculisystem.model.dto.DanceClassDto;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.model.response.PageResponse;
import com.cleyton.promusculisystem.services.DanceClassService;
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
@RequestMapping("/danceclass")
public class DanceClassController {

    @Autowired
    private DanceClassService service;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createDanceClass(@RequestBody DanceClassDto danceClassDto) {
        service.createDanceClass(danceClassDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/find/")
    public ResponseEntity<DanceClass> findDanceClassByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(service.findDanceClassByName(name), HttpStatus.OK);
    }

    @GetMapping("/find/all/instructor/")
    public ResponseEntity<PageResponse<?>> findAllDanceClassesByInstructor(@RequestParam("instructor_name") String instructorName,
                                                                           @RequestBody PaginationDto paginationDto) {
        return new ResponseEntity<>(service.findAllDanceClassesByInstructor(instructorName, paginationDto), HttpStatus.OK);
    }

    @PutMapping("/update/")
    public ResponseEntity<HttpStatus> updateDanceClassById(@RequestParam("id") Integer id,
                                                           @RequestBody DanceClassDto danceClassDto) {
        service.updateDanceClassById(id, danceClassDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/partial/")
    public ResponseEntity<HttpStatus> patchDanceClassById(@RequestParam("id") Integer id,
                                                          @RequestBody DanceClassDto danceClassDto) {
        service.patchDanceClassById(id, danceClassDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<HttpStatus> deleteDanceClassById(@RequestParam("id") Integer id) {
        service.deleteDanceClassById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reactive/")
    public ResponseEntity<HttpStatus> reactivateDanceClassById(@RequestParam("id") Integer id) {
        service.reactivateDanceClassById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
