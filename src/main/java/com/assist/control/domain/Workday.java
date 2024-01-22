package com.assist.control.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @ManyToOne
    private KindOfShift shift;
    private LocalDate date;
    private LocalTime timeOfArrival;
    private LocalTime departureTime;
    private Double totalHours;
    private boolean approved;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Employee employee;

}
