package com.assist.control.dto.request;

import com.assist.control.domain.TypeWorkday;
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

    public static Workday workdayMap(RequestWorkdayDTO requestWorkdayDTO){
        Workday workday = new Workday();

        workday.setType(TypeWorkday.getType(requestWorkdayDTO.type).toString());
        workday.setDate(requestWorkdayDTO.getDate());
        workday.setTimeOfEntry(requestWorkdayDTO.getTimeOfEntry());
        workday.setTimeOfExit(requestWorkdayDTO.getTimeOfExit());

        return  workday;
    }

}
