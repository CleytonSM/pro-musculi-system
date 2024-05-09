package com.cleyton.promusculisystem.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DanceClassDto {
    private String clientName;
    private String instructorName;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
}
