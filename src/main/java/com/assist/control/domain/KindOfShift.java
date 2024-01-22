package com.assist.control.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter
@Entity(name = "kind_of_shift")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "description_en"})
})
public class KindOfShift {

    @Id
    private String code;
    @Column(name = "description_en")
    private String descriptionEn;
    @Column(name = "description_es")
    private String descriptionEs;
    @Column(name = "is_working")
    private boolean isWorking;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KindOfShift that = (KindOfShift) o;
        return Objects.equals(code, that.code) && Objects.equals(descriptionEn, that.descriptionEn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, descriptionEn);
    }
}
