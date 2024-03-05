package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
    }
}
