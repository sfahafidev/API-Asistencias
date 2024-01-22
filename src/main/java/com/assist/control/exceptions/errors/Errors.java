package com.assist.control.exceptions.errors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Errors {

    public static final ApiError REGULAR_SHIFT_ERROR_1 = new ApiError
            ("RegularShiftError1","No puedes cargar mas de 8 horas por turno normal");

    public static final ApiError REGULAR_SHIFT_ERROR_2 = new ApiError
            ("RegularShiftError2","Las horas cargadas no coinciden con el turno normal");

    public static final ApiError OVERTIME_ERROR = new ApiError
            ("RegularShiftError2","Las horas cargadas no coinciden con el turno extra");

}
