package com.cleyton.promusculisystem.model.response;

import com.cleyton.promusculisystem.model.Client;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GymPlanClientsResponse {
    private String name;
    private BigDecimal price;
    private PageResponse<Client> clients;
}
