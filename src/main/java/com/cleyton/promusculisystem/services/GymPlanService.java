package com.cleyton.promusculisystem.services;

import com.cleyton.promusculisystem.helper.ModelHelper;
import com.cleyton.promusculisystem.model.Client;
import com.cleyton.promusculisystem.model.GymPlan;
import com.cleyton.promusculisystem.model.response.GymPlanClientsResponse;
import com.cleyton.promusculisystem.model.dto.GymPlanDto;
import com.cleyton.promusculisystem.model.response.PageResponse;
import com.cleyton.promusculisystem.model.dto.PaginationDto;
import com.cleyton.promusculisystem.repository.GymPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.cleyton.promusculisystem.helper.ModelHelper.isEntityAlreadyInUse;
import static com.cleyton.promusculisystem.helper.ModelHelper.verifyOptionalEntity;

@Service
public class GymPlanService {

    @Autowired
    private GymPlanRepository repository;
    @Autowired
    private ModelHelper modelHelper;
    @Autowired
    private ClientService clientService;

    private void save(GymPlan gymPlan) {
        repository.save(gymPlan);
    }

    public void createGymPlan(GymPlanDto gymPlanDto) {
        isEntityAlreadyInUse(repository.findByName(gymPlanDto.getName()));
        save(modelHelper.postGymPlanAttributeSetter(gymPlanDto));
    }

    public PageResponse<GymPlan> findAllGymPlans(PaginationDto paginationDto) {
        Page<GymPlan> gymPlanPage = repository.findAllGymPlans(modelHelper.setupPageable(paginationDto));

        return modelHelper.setupPageResponse(gymPlanPage);
    }

    public GymPlan findByName(String name) {
        return verifyOptionalEntity(repository.findByName(name));
    }

    public void delete(String name) {
        GymPlan gymPlan = verifyOptionalEntity(repository.findByName(name));
        repository.deleteById(gymPlan.getId());
    }

    public GymPlanClientsResponse getActiveClientsFromPlan(String name, PaginationDto paginationDto) {
        GymPlan gymPlan = verifyOptionalEntity(repository.findByName(name));
        PageResponse<Client> clients = clientService.findClients(paginationDto);

        return modelHelper.gymPlanClientsResponseBuilder(gymPlan, clients);
    }
}
