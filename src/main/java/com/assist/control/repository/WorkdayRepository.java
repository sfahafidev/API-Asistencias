package com.assist.control.repository;

import com.assist.control.domain.Workday;
import com.assist.control.dto.request.RequestWorkdayFilterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkdayRepository extends JpaRepository<Workday, Long> {

    List<Workday> findByEmployeeId(Long idEmployee);

    List<Workday> findByEmployeeIdAndDate(Long idEmployee, LocalDate date);

    List<Workday> findByDateBetween(LocalDate firsDayWeek, LocalDate lastDayWeek);

    default List<Workday> queryDslFilterWorkdays(RequestWorkdayFilterDTO requestWorkdayFilter){
        return null;
    }

}
