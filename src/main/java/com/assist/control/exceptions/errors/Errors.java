package com.assist.control.exceptions.errors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Errors {

    public static final ApiError REGULAR_SHIFT_ERROR_1 = new ApiError
            ("ExcesoDeHorasTN","No puedes cargar mas de 8 horas por turno normal");

    public static final ApiError REGULAR_SHIFT_ERROR_2 = new ApiError
            ("HorasMalCargadasTN","Las horas cargadas no coinciden con el turno normal");

    public static final ApiError OVERTIME_ERROR = new ApiError
            ("HorasMalCargadasTE","Las horas cargadas no coinciden con el turno extra");

    public static final ApiError REPEATED_SHIFT_DAYS_OFF_ERROR = new ApiError
            ("YaExisteUnDiaNoLaboral","No se puede repetir vacaciones y dia libre el mismo dia");

    public static final ApiError TOTAL_HOURS_SHIFT_MIX_ERROR = new ApiError
            ("TotalHorasTurnoMixto","Excediste el total de 12 horas por dia");

    public static final ApiError TOTAL_DAYS_OFF_ERROR = new ApiError
            ("TotalDiasLibres","No se pueden cargar mas de dos dias libres por semana");

    public static final ApiError MAX_TOTAL_HOURS_WEEK = new ApiError
            ("MaximoHorasSemana","Excediste el total de 48 horas semanales");

    public static final ApiError MIN_TOTAL_HOURS_WEEK = new ApiError
            ("MinimoHorasSemana","Debes cargar un mínimo de 30 horas por semana");

    public static final ApiError TYPE_SHIFT_ERROR = new ApiError
            ("TipoJornadaIncorrecto","El tipo de jornada indicado no existe");

    public static final ApiError HOURS_SHIFT_WORKING_ERROR = new ApiError
            ("FaltanHorasJornada","Debe completar las horas de entrada y salida");

    public static final ApiError EMPLOYEE_ERROR = new ApiError
            ("EmpleadoNoExiste","El empleado indicado no existe");

}
