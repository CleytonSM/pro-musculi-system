package com.cleyton.promusculisystem.model.dto;

import com.cleyton.promusculisystem.model.Authority;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserDto {

    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private RoleDto role;
}
