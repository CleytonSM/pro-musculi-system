package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.User;
import com.cleyton.promusculisystem.model.dto.LoginDto;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.model.response.PageResponse;
import com.cleyton.promusculisystem.model.dto.UserDto;
import com.cleyton.promusculisystem.services.UserService;
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
    public ResponseEntity<PageResponse<User>> findUsers(@RequestBody PaginationDto paginationDto) {
        return new ResponseEntity<>(service.getUsers(paginationDto), HttpStatus.OK);
    }

    @GetMapping("/auth")
    public ResponseEntity<HttpStatus> login(@RequestBody LoginDto logInDto) {
        return new ResponseEntity<>(service.login(logInDto));
    }

    @PutMapping("/admin/update/")
    public ResponseEntity<?> updateUser(@RequestParam(name = "id") Integer id, @RequestBody UserDto userDto) {
        service.updateUser(id, userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/admin/update/partial/")
    public ResponseEntity<?> patchUser(@RequestParam(name = "id") Integer id, @RequestBody UserDto userDto) {
        service.patchUser(id, userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam(name = "id") Integer id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/reactive/")
    public ResponseEntity<HttpStatus> reactivateUser(@RequestParam(name = "id") Integer id) {
        service.reactiveUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/inactive/users")
    public ResponseEntity<PageResponse<User>> findInactiveUsers(@RequestBody PaginationDto paginationDto) {
        return new ResponseEntity<>(service.getInactiveUsers(paginationDto), HttpStatus.OK);
    }

    @GetMapping("/admin/find/")
    public ResponseEntity<User> findUserByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<>(service.findUserByEmail(email), HttpStatus.OK);
    }
}
