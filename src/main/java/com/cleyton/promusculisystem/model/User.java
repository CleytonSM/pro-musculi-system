package com.cleyton.promusculisystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(generator = "native", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native")
    private Integer id;
    @Column(length = 100, nullable = false, unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 100, nullable = false)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime createdAt;

    @OneToMany
    private Set<Authority> authorities;
}
