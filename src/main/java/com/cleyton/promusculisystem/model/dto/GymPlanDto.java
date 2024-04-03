package com.cleyton.promusculisystem.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GymPlanDto {
    private String  name;
    private BigDecimal price;
    private Integer duration;
}
