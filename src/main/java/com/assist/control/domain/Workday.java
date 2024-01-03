package com.assist.control.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "workdays")
@Getter @Setter
public class Workday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private LocalDate date;
    private LocalTime timeOfEntry;
    private LocalTime timeOfExit;
    private Double totalTimeDay;
    private Boolean approved;

    @ManyToOne(cascade = CascadeType.ALL)
    private Employee employee;

}
