package com.assist.control.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "workdays")
@Getter @Setter
public class Workday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWorkday;

    private String type;
    private LocalDate date;
    private LocalTime timeOfEntry;
    private LocalTime timeOfExit;
    private Double totalTimeDay;

}
