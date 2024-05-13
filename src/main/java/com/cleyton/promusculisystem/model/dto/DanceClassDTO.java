package com.cleyton.promusculisystem.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DanceClassDTO {
    private String instructorName;
    private String clientEmail;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
}
