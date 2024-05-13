package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper;
import com.cleyton.promusculisystem.helper.ModelBuilderHelper;
import com.cleyton.promusculisystem.model.Client;
import com.cleyton.promusculisystem.model.GymPlan;
import com.cleyton.promusculisystem.model.response.GymPlanClientsResponse;
import com.cleyton.promusculisystem.model.dto.GymPlanDTO;
import com.cleyton.promusculisystem.model.response.PageResponse;
import com.cleyton.promusculisystem.model.dto.PaginationDTO;
import com.cleyton.promusculisystem.repository.GymPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.isEntityAlreadyInUse;
import static com.cleyton.promusculisystem.helper.ModelAttributeSetterHelper.verifyOptionalEntity;

@Service
public class GymPlanService {

    @Autowired
    private GymPlanRepository repository;
    @Autowired
    private ModelAttributeSetterHelper modelAttributeSetterHelper;
    @Autowired
    private ModelBuilderHelper modelBuilderHelper;
    @Autowired
    private ClientService clientService;

    public void save(GymPlan gymPlan) {
        repository.save(gymPlan);
    }

    public void createGymPlan(GymPlanDTO gymPlanDto) {
        isEntityAlreadyInUse(repository.findByName(gymPlanDto.getName()));
        save(modelAttributeSetterHelper.postGymPlanAttributeSetter(gymPlanDto));
    }

    public PageResponse<GymPlan> findAllGymPlans(PaginationDTO paginationDto) {
        Page<GymPlan> gymPlanPage = repository.findAllGymPlans(modelAttributeSetterHelper.setupPageable(paginationDto));

        return modelAttributeSetterHelper.setupPageResponse(gymPlanPage);
    }

    public GymPlan findByName(String name) {
        return verifyOptionalEntity(repository.findByName(name));
    }

    public void delete(String name) {
        GymPlan gymPlan = verifyOptionalEntity(repository.findByName(name));
        repository.deleteById(gymPlan.getId());
    }

    public GymPlanClientsResponse findActiveClientsFromPlan(String name, PaginationDTO paginationDto) {
        GymPlan gymPlan = verifyOptionalEntity(repository.findByName(name));
        PageResponse<Client> clients = clientService.findClients(paginationDto);

        return modelBuilderHelper.gymPlanClientsResponseBuilder(gymPlan, clients);
    }

    public GymPlanClientsResponse findInactiveClientsFromPlan(String name, PaginationDTO paginationDto) {
        GymPlan gymPlan = verifyOptionalEntity(repository.findByName(name));
        PageResponse<Client> clients = clientService.findInactiveClients(paginationDto);

        return modelBuilderHelper.gymPlanClientsResponseBuilder(gymPlan, clients);
    }

    public void update(String name, GymPlanDTO gymPlanDto) {
        GymPlan gymPlan = verifyOptionalEntity(repository.findByName(name));
        if(!gymPlanDto.getName().equals(gymPlan.getName())) {
            isEntityAlreadyInUse(repository.findByName(gymPlanDto.getName()));
        }

        save(modelAttributeSetterHelper.updateGymPlanAttributeSetter(gymPlan, gymPlanDto));
    }

    public void patch(String name, GymPlanDTO gymPlanDto) {
        GymPlan gymPlan = verifyOptionalEntity(repository.findByName(name));
        if(!gymPlanDto.getName().equals(gymPlan.getName())) {
            isEntityAlreadyInUse(repository.findByName(gymPlanDto.getName()));
        }

        save(modelAttributeSetterHelper.patchGymPlanAttributeSetter(gymPlan, gymPlanDto));
    }
}
