package com.assist.control.service;

import com.assist.control.domain.Employee;
import com.assist.control.dto.request.RequestEmployeeDTO;
import com.assist.control.repository.EmployeeRepository;
import com.assist.control.service.interfaces.EmployeeInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements EmployeeInterface {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper){
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Employee saveEmployee(RequestEmployeeDTO requestEmployee) {
        Employee employee = modelMapper.map(requestEmployee, Employee.class);
        return employeeRepository.save(employee);
    }

    @Override
    public void editEmployee(RequestEmployeeDTO requestEmployee) {

    }

    @Override
    public Boolean deleteEmployee(Long idEmployee) {
        return null;
    }

    @Override
    public Employee findEmployee(Long idEmployee) {
        return null;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return null;
    }


}
