package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(service.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<User> createAdmin(@RequestBody User user) {
        return new ResponseEntity<>(service.createAdmin(user), HttpStatus.CREATED);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<?> findUsers(@RequestBody PaginationDto paginationDto) {
        return new ResponseEntity<>(service.getUsers(paginationDto), HttpStatus.OK);
    }
}
