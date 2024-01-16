package com.assist.control.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class WorkdayDTO {

    @NotBlank(message = "El tipo de jornada es obligatoria")
    private String type;
    @NotNull(message = "La fecha es obligatoria")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    @NotNull(message = "La hora de entrada es obligatoria")
    @JsonFormat(pattern="HH:mm")
    private LocalTime timeOfEntry;
    //@NotNull(message = "La hora de salida es obligatoria")
    @JsonFormat(pattern="HH:mm")
    private LocalTime timeOfExit;

}
