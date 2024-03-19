package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.LoginDto;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        service.createUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<User> createAdmin(@RequestBody UserDto userDto) {
        service.createAdmin(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Stream<User>> findUsers(@RequestBody PaginationDto paginationDto) {
        return new ResponseEntity<>(service.getUsers(paginationDto), HttpStatus.OK);
    }

    @GetMapping("/auth")
    public ResponseEntity<HttpStatus> login(@RequestBody LoginDto logInDto) {
        return new ResponseEntity<>(service.login(logInDto));
    }

    @PutMapping("/admin/update/")
    public ResponseEntity<?> updateUser(@RequestParam(name = "id") Integer id, @RequestBody UserDto userDto) {
        return new ResponseEntity<>(service.updateUser(id, userDto), HttpStatus.OK);
    }

    @PatchMapping("/admin/update/partial/")
    public ResponseEntity<?> patchUser(@RequestParam(name = "id") Integer id, @RequestBody UserDto userDto) {
        return new ResponseEntity<>(service.patchUser(id, userDto), HttpStatus.OK);
    }
}
