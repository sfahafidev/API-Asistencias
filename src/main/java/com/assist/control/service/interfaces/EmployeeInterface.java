package com.assist.control.service.interfaces;

import com.assist.control.domain.Employee;
import com.assist.control.dto.request.RequestEmployeeDTO;

import java.util.List;

public interface EmployeeInterface {

    Employee saveEmployee(RequestEmployeeDTO requestEmployee);
    Employee editEmployee(RequestEmployeeDTO requestEmployee);
    void deleteEmployee(Long idEmployee);
    Employee findEmployee(Long idEmployee);
    List<Employee> findAllEmployees();

}
