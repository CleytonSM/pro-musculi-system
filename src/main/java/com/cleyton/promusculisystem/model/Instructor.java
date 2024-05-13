package com.cleyton.promusculisystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tb_instructors")
@Data
public class Instructor{

    @Id
    @GeneratedValue(generator = "native", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native")
    @JsonIgnore
    private Integer id;
    @Column(length = 60, nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal salary;
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
    @JsonIgnore
    private Boolean active;
    private String phone;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "instructor")
    @JsonIgnore
    private Set<DanceClass> danceClasses;
    @OneToMany(mappedBy = "instructor")
    @JsonIgnore
    private Set<WorkoutClass> workoutClasses;
}
