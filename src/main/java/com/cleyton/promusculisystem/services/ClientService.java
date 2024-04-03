package com.cleyton.promusculisystem.services;


import com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper;
import com.cleyton.promusculisystem.model.Client;
import com.cleyton.promusculisystem.model.dto.ClientDto;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.model.response.PageResponse;
import com.cleyton.promusculisystem.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.isEntityAlreadyInUse;
import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.verifyOptionalEntity;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;
    @Autowired
    private ModelAttributeSetterHelper modelAttributeSetterHelper;

    public void createClient (ClientDto clientDto){
        isEntityAlreadyInUse(repository.findByEmail(clientDto.getEmail()));
        repository.save(modelAttributeSetterHelper.postClientAttributeSetter(clientDto));
    }

    public PageResponse<Client> findClients(PaginationDto paginationDto) {
        Page<Client> clients = repository.findAllActive(modelAttributeSetterHelper.setupPageable(paginationDto));
        return modelAttributeSetterHelper.setupPageResponse(clients);
    }

    public PageResponse<Client> findInactiveClients(PaginationDto paginationDto) {
        Page<Client> clients = repository.findAllInactive(modelAttributeSetterHelper.setupPageable(paginationDto));

        return modelAttributeSetterHelper.setupPageResponse(clients);
    }

    public void updateClient(String name, ClientDto clientDto) {
        Client client = verifyOptionalEntity(repository.findByName(name));
        if (!client.getEmail().equals(clientDto.getEmail())) {
            isEntityAlreadyInUse(repository.findByEmail(clientDto.getEmail()));
        }

        save(modelAttributeSetterHelper.updateClientAttributeSetter(client, clientDto));
    }

    public void patchClient(String name, ClientDto clientDto) {
        Client client = verifyOptionalEntity(repository.findByName(name));
        if (!client.getEmail().equals(clientDto.getEmail())) {
            isEntityAlreadyInUse(repository.findByEmail(clientDto.getEmail()));
        }
        save(modelAttributeSetterHelper.patchClientAttributeSetter(client, clientDto));
    }

    private void save(Client client) {
        repository.save(client);
    }

    public void deleteClient(String name) {
        Client client = verifyOptionalEntity(repository.findByName(name));

        save(modelAttributeSetterHelper.deleteClientAttributeSetter(client));
    }

    public void reactivateClient(String name) {
        Client client = verifyOptionalEntity(repository.findByName(name));

        save(modelAttributeSetterHelper.reactivateClientAttributeSetter(client));
    }

    public Client findClientByEmail(String email) {
        return verifyOptionalEntity(repository.findByEmail(email));
    }

    public Client findClientByName(String name) {
        return verifyOptionalEntity(repository.findByName(name));
    }
}
