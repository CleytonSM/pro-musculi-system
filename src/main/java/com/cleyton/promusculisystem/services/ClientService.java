package com.cleyton.promusculisystem.services;


import com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper;
import com.cleyton.promusculisystem.model.Client;
import com.cleyton.promusculisystem.model.dto.ClientDTO;
import com.cleyton.promusculisystem.model.dto.PaginationDTO;
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

    public void createClient (ClientDTO clientDto){
        isEntityAlreadyInUse(repository.findByEmail(clientDto.getEmail()));
        repository.save(modelAttributeSetterHelper.postClientAttributeSetter(clientDto));
    }

    public PageResponse<Client> findClients(PaginationDTO paginationDto) {
        Page<Client> clients = repository.findAllActive(modelAttributeSetterHelper.setupPageable(paginationDto));
        return modelAttributeSetterHelper.setupPageResponse(clients);
    }

    public PageResponse<Client> findInactiveClients(PaginationDTO paginationDto) {
        Page<Client> clients = repository.findAllInactive(modelAttributeSetterHelper.setupPageable(paginationDto));

        return modelAttributeSetterHelper.setupPageResponse(clients);
    }

    public void updateClient(String name, ClientDTO clientDto) {
        Client client = verifyOptionalEntity(repository.findByName(name));
        if (!client.getEmail().equals(clientDto.getEmail())) {
            isEntityAlreadyInUse(repository.findByEmail(clientDto.getEmail()));
        }

        save(modelAttributeSetterHelper.updateClientAttributeSetter(client, clientDto));
    }

    public void patchClient(String name, ClientDTO clientDto) {
        Client client = verifyOptionalEntity(repository.findByName(name));
        if (!client.getEmail().equals(clientDto.getEmail())) {
            isEntityAlreadyInUse(repository.findByEmail(clientDto.getEmail()));
        }
        save(modelAttributeSetterHelper.patchClientAttributeSetter(client, clientDto));
    }

    public void save(Client client) {
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
