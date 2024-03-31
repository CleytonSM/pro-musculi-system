package com.cleyton.promusculisystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_gym_plan")
public class GymPlan {

    @Id
    @GeneratedValue(generator = "native", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native")
    private Integer id;
    @Column(nullable = false)
    private Integer clientId;
    @Column(length = 40, nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    private LocalDateTime expireAt;
    private LocalDateTime createdAt;
}
