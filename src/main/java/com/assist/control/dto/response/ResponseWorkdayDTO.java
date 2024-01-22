package com.assist.control.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ResponseWorkdayDTO {

    private String name;
    private String kindOfShift;
    private LocalDate date;
    private Double totalHours;

}
