package com.cleyton.promusculisystem.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkoutClassDTO {
    private String clientEmail;
    private String instructorName;
    private String name;
    private LocalDateTime dateClass;
    private String description;
}
