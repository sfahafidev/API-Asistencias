package com.assist.control.validators;

import com.assist.control.domain.Employee;
import com.assist.control.exceptions.BusinessRunTimeException;
import com.assist.control.exceptions.errors.Errors;
import com.assist.control.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidators {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeValidators (EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee validateEmployee(Long idEmployee){
        return employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new BusinessRunTimeException(Errors.EMPLOYEE_ERROR));
    }

}
