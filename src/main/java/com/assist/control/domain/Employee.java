package com.assist.control.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity(name = "employees")
@Getter @Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployee;

    private String name;
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Workday> workdays;

}
