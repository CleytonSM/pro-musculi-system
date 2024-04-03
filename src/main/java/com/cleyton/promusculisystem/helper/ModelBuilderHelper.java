package com.cleyton.promusculisystem.helper;

import com.cleyton.promusculisystem.model.Client;
import com.cleyton.promusculisystem.model.GymPlan;
import com.cleyton.promusculisystem.model.response.GymPlanClientsResponse;
import com.cleyton.promusculisystem.model.response.PageResponse;
import org.springframework.stereotype.Component;

@Component
public class ModelBuilderHelper {

    public GymPlanClientsResponse gymPlanClientsResponseBuilder(GymPlan gymPlan, PageResponse<Client> clients) {
        return GymPlanClientsResponse.builder()
                .name(gymPlan.getName())
                .price(gymPlan.getPrice())
                .clients(clients)
                .build();
    }
}
