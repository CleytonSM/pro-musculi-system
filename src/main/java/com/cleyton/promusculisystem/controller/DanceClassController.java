package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.DanceClass;
import com.cleyton.promusculisystem.model.dto.DanceClassDTO;
import com.cleyton.promusculisystem.model.dto.PaginationDTO;
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
    public ResponseEntity<HttpStatus> createDanceClass(@RequestBody DanceClassDTO danceClassDto) {
        service.createDanceClass(danceClassDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/find/")
    public ResponseEntity<DanceClass> findDanceClassById(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(service.findDanceClassById(id), HttpStatus.OK);
    }

    @GetMapping("/find/inactive/")
    public ResponseEntity<DanceClass> findInactiveDanceClassById(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(service.findInactiveDanceClassById(id), HttpStatus.OK);
    }

    @GetMapping("/find/inactive/all/instructor/")
    public ResponseEntity<PageResponse<?>> findAllInactiveDanceClassByInstructor
            (@RequestParam("instructor_name") String instructorName, @RequestBody PaginationDTO paginationDto) {
        return new ResponseEntity<>(service.findAllInactiveDanceClassesByInstructor(instructorName, paginationDto), HttpStatus.OK);
    }

    @GetMapping("/find/all/instructor/")
    public ResponseEntity<PageResponse<?>> findAllDanceClassesByInstructor(@RequestParam("instructor_name") String instructorName,
                                                                           @RequestBody PaginationDTO paginationDto) {
        return new ResponseEntity<>(service.findAllDanceClassesByInstructor(instructorName, paginationDto), HttpStatus.OK);
    }

    @PutMapping("/update/")
    public ResponseEntity<HttpStatus> updateDanceClassById(@RequestParam("id") Integer id,
                                                           @RequestBody DanceClassDTO danceClassDto) {
        service.updateDanceClassById(id, danceClassDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/partial/")
    public ResponseEntity<HttpStatus> patchDanceClassById(@RequestParam("id") Integer id,
                                                          @RequestBody DanceClassDTO danceClassDto) {
        service.patchDanceClassById(id, danceClassDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<HttpStatus> deleteDanceClassById(@RequestParam("id") Integer id) {
        service.deleteDanceClassById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reactivate/")
    public ResponseEntity<HttpStatus> reactivateDanceClassById(@RequestParam("id") Integer id) {
        service.reactivateDanceClassById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
