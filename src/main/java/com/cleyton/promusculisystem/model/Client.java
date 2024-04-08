package com.cleyton.promusculisystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Table(name = "tb_clients")
public class Client {

    @Id
    @GeneratedValue(generator = "native", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native")
    @JsonIgnore
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "gym_plan_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private GymPlan gymPlan;
    @Column(nullable = false, length = 60)
    private String name;
    @Column(length = 100, unique = true)
    private String email;
    @Column(length = 11, unique = true)
    private String phone;
    @Column(nullable = false)
    @JsonIgnore
    private Boolean active;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private Set<DanceClass> danceClasses;
}
