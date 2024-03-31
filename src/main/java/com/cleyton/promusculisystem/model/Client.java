package com.cleyton.promusculisystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tb_clients")
public class Client {

    @Id
    @GeneratedValue(generator = "native", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native")
    private Integer id;
    @Column(nullable = false, length = 60)
    private String name;
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    @Column(length = 11, unique = true)
    private String phone;
    @Column(nullable = false)
    @JsonIgnore
    private Boolean active;
    private LocalDateTime createdAt;
}
