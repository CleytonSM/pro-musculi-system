package com.cleyton.promusculisystem.model.dto;

import lombok.Data;

@Data
public class UserDto {

    private String email;
    private String password;
    private RoleDto role;
}
