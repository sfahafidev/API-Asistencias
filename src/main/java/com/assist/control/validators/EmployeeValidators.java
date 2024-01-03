package com.assist.control.validators;

import com.assist.control.domain.Employee;
import com.assist.control.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidators {

    private final EmployeeRepository employeeRepository;

    public EmployeeValidators (EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee validateEmployee(Long idEmployee){
        return employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new RuntimeException("El empleado indicado no existe"));
    }

}
