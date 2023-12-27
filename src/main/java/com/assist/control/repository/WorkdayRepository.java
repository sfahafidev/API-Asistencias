package com.assist.control.repository;

import com.assist.control.domain.Employee;
import com.assist.control.domain.Workday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkdayRepository extends JpaRepository<Workday, Long> {

    List<Employee> findByEmployeeId(Long idEmployee);

}
