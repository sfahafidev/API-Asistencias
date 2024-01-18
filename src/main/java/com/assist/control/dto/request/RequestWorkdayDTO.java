package com.assist.control.dto.request;

import com.assist.control.domain.Workday;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class RequestWorkdayDTO {

    @NotNull(message = "El ID es obligatorio")
    private Long idEmployee;
    @NotBlank(message = "El tipo de jornada es obligatoria")
    private String kindOkShift;
    @NotNull(message = "La fecha es obligatoria")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern="HH:mm")
    private LocalTime timeOfArrival;
    @JsonFormat(pattern="HH:mm")
    private LocalTime departureTime;

    public static Workday workdayMap(RequestWorkdayDTO requestWorkdayDTO){
        Workday workday = new Workday();

        //workday.setShift();
        workday.setDate(requestWorkdayDTO.getDate());
        workday.setTimeOfArrival(requestWorkdayDTO.getTimeOfArrival());
        workday.setDepartureTime(requestWorkdayDTO.getDepartureTime());

        return  workday;
    }

}
