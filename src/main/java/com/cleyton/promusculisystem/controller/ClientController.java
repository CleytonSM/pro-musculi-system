package com.cleyton.promusculisystem.controller;

import com.cleyton.promusculisystem.model.Client;
import com.cleyton.promusculisystem.model.dto.ClientDto;
import com.cleyton.promusculisystem.model.response.PageResponse;
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

    @GetMapping("/find/all/")
    public ResponseEntity<PageResponse<Client>> findClients(@RequestBody PaginationDto paginationDto) {
        return new ResponseEntity<>(service.findClients(paginationDto), HttpStatus.OK);
    }

    @GetMapping("/find//inactive/all")
    public ResponseEntity<PageResponse<Client>> findInactiveClients(@RequestBody PaginationDto paginationDto) {
        return new ResponseEntity<>(service.findInactiveClients(paginationDto), HttpStatus.OK);
    }

    @GetMapping("/find/email/")
    public ResponseEntity<?> findClientByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<>(service.findClientByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/find/name/")
    public ResponseEntity<?> findClientByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(service.findClientByName(name), HttpStatus.OK);
    }

    @PutMapping("/update/")
    public ResponseEntity<HttpStatus> updateClient(@RequestParam(name = "name") String name, @RequestBody ClientDto clientDto) {
        service.updateClient(name, clientDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/partial/")
    public ResponseEntity<HttpStatus> patchClient(@RequestParam(name = "name") String name, @RequestBody ClientDto clientDto) {
        service.patchClient(name, clientDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<HttpStatus> deleteClient(@RequestParam(name = "name") String name) {
        service.deleteClient(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reactivate/")
    public ResponseEntity<HttpStatus> reactivateClient(@RequestParam(name = "name") String name) {
        service.reactivateClient(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
