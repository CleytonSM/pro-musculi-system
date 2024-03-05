package com.cleyton.promusculisystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "authorities")
@Data
public class Authority {

    @Id
    @GeneratedValue(generator = "native", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native")
    private Integer id;
    @Column(length = 20, nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
