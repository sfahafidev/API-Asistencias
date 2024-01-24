package com.assist.control.domain;

import com.assist.control.dto.request.RequestWorkdayDTO;
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
    private boolean approved; //TODO: Analizar si es conveniente manejar estados para ver en el front

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Employee employee;

    public static Workday isWorking(RequestWorkdayDTO request) {
        Workday workday = new Workday();
        workday.setDate(request.getDate());
        workday.setTimeOfArrival(request.getTimeOfArrival());
        workday.setDepartureTime(request.getDepartureTime());
        workday.setApproved(false);
        return  workday;
    }

    public static Workday nonWorking(RequestWorkdayDTO request) {
        Workday workday = new Workday();
        workday.setDate(request.getDate());
        workday.setApproved(false);
        return  workday;
    }

    public static Workday update(RequestWorkdayDTO request) {
        Workday workday = new Workday();
        workday.setId(request.getIdWorkday());
        workday.setTimeOfArrival(request.getTimeOfArrival());
        workday.setDepartureTime(request.getDepartureTime());
        workday.setApproved(false);
        return  workday;
    }

}
