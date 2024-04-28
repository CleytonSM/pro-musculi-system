package com.cleyton.promusculisystem.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InstructorDto {

    private String name;
    private BigDecimal salary;
    private String cpf;
    private String phone;
}
