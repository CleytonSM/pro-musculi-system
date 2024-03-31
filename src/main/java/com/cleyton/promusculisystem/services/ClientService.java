package com.cleyton.promusculisystem.services;


import com.cleyton.promusculisystem.helper.ModelHelper;
import com.cleyton.promusculisystem.model.Client;
import com.cleyton.promusculisystem.model.dto.ClientDto;
import com.cleyton.promusculisystem.model.dto.PageResponse;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.repository.ClientRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.cleyton.promusculisystem.helper.ModelHelper.verifyEmptyOptionalEntity;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;
    @Autowired
    private ModelHelper modelHelper;

    public void createClient (ClientDto clientDto){
        isEmailAlreadyInUse(clientDto.getEmail());

        repository.save(modelHelper.postClientAttributeSetter(clientDto));
    }

    private void isEmailAlreadyInUse(String email) {
        if(repository.findByEmail(email).isPresent()) {
            throw new EntityExistsException("This email is already in use!");
        }
    }

    public PageResponse<Client> findClients(PaginationDto paginationDto) {
        Page<Client> clients = repository.findAllActive(modelHelper.setupPageable(paginationDto));

        return modelHelper.setupPageResponse(clients, paginationDto);
    }

    public PageResponse<Client> findInactiveClients(PaginationDto paginationDto) {
        Page<Client> clients = repository.findAllInactive(modelHelper.setupPageable(paginationDto));

        return modelHelper.setupPageResponse(clients, paginationDto);
    }

    public void updateClient(Integer id, ClientDto clientDto) {
        Client client = verifyEmptyOptionalEntity(repository.findById(id));

        save(modelHelper.updateClientAttributeSetter(client, clientDto));
    }

    public void patchClient(Integer id, ClientDto clientDto) {
        Client client = verifyEmptyOptionalEntity(repository.findById(id));

        save(modelHelper.patchClientAttributeSetter(client, clientDto));
    }

    private void save(Client client) {
        repository.save(client);
    }

    public void deleteClient(Integer id) {
        Client client = verifyEmptyOptionalEntity(repository.findById(id));

        save(modelHelper.deleteClientAttributeSetter(client));
    }

    public void reactivateClient(Integer id) {
        Client client = verifyEmptyOptionalEntity(repository.findById(id));

        save(modelHelper.reactivateClientAttributeSetter(client));
    }

    public Client findClientByEmail(String email) {
        return verifyEmptyOptionalEntity(repository.findByEmail(email));
    }

    public Client findClientByName(String name) {
        return verifyEmptyOptionalEntity(repository.findByName(name));
    }
}
