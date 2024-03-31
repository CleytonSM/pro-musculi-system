package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.Client;
import com.cleyton.promusculisystem.model.dto.ClientDto;
import com.cleyton.promusculisystem.model.dto.PageResponse;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.services.ClientService;
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
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerClient(@RequestBody ClientDto clientDto) {
        service.createClient(clientDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResponse<Client>> findClients(@RequestBody PaginationDto paginationDto) {
        return new ResponseEntity<>(service.findClients(paginationDto), HttpStatus.OK);
    }

    @GetMapping("/inactive")
    public ResponseEntity<PageResponse<Client>> findInactiveClients(@RequestBody PaginationDto paginationDto) {
        return new ResponseEntity<>(service.findInactiveClients(paginationDto), HttpStatus.OK);
    }

    @GetMapping("/findByEmail/")
    public ResponseEntity<?> findClientByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<>(service.findClientByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/findByName/")
    public ResponseEntity<?> findClientByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(service.findClientByName(name), HttpStatus.OK);
    }

    @PutMapping("/update/")
    public ResponseEntity<HttpStatus> updateClient(@RequestParam(name = "id") Integer id, @RequestBody ClientDto clientDto) {
        service.updateClient(id, clientDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/partial/")
    public ResponseEntity<HttpStatus> partialClient(@RequestParam(name = "id") Integer id, @RequestBody ClientDto clientDto) {
        service.patchClient(id, clientDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<HttpStatus> deleteClient(@RequestParam(name = "id") Integer id) {
        service.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reactivate/")
    public ResponseEntity<HttpStatus> reactivateClient(@RequestParam(name = "id") Integer id) {
        service.reactivateClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
