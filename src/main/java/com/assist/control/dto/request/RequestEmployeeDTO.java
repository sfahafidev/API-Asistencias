package com.assist.control.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RequestEmployeeDTO {

    private Long idEmployee;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;

}
