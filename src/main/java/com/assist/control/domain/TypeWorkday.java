package com.assist.control.domain;

import java.util.Arrays;

public enum TypeWorkday {

    TURNO_EXTRA("Turno Extra"),
    VACACIONES("Vacaciones"),
    DIA_LIBRE("Dia Libre"),
    TURNO_NORMAL("Turno Normal");

    private final String type;

    TypeWorkday(String type){
        this.type = type;
    }

    public static TypeWorkday getType(String type) {
        return Arrays.stream(values())
                .filter(motivoEnum -> motivoEnum.type.equalsIgnoreCase(type))
                .findFirst().get();
    }

    @Override
    public String toString(){
        return type;
    }

}
