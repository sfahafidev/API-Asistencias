package com.assist.control.repository;

import com.assist.control.domain.KindOfShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KindOfShiftRepository extends JpaRepository<KindOfShift, String> {

    Optional<KindOfShift> findByCode(String code);

}
