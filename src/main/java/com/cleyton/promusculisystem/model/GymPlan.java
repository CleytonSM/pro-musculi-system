package com.cleyton.promusculisystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table(name = "tb_gym_plan")
public class GymPlan {

    @Id
    @GeneratedValue(generator = "native", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native")
    @JsonIgnore
    private Integer id;
    @Column(length = 40, nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    private Integer duration;

    @OneToMany(mappedBy = "gymPlan", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Client> clients;
}
