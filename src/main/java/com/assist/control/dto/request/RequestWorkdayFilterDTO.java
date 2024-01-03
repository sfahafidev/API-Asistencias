package com.assist.control.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

@Getter @Setter
public class RequestWorkdayFilterDTO {

    private String type;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern="HH:mm")
    private LocalTime timeOfEntry;
    @JsonFormat(pattern="HH:mm")
    private LocalTime timeOfExit;

    //boolean formatoValido = Pattern.matches("\\d{4}-\\d{2}-\\d{2}", request.getFecha());

}
