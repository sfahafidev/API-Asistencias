package com.assist.control.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter
@Entity(name = "kind_of_shift")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "description"})
})
public class KindOfShift {

    @Id
    private String code;
    private String description;
    @Column(name = "is_working")
    private boolean isWorking;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KindOfShift that = (KindOfShift) o;
        return Objects.equals(code, that.code) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description);
    }
}
