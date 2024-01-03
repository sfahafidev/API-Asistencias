package com.assist.control.service;

import com.assist.control.domain.Employee;
import com.assist.control.dto.request.RequestEmployeeDTO;
import com.assist.control.repository.EmployeeRepository;
import com.assist.control.service.interfaces.EmployeeInterface;
import com.assist.control.validators.EmployeeValidators;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements EmployeeInterface {

    private final EmployeeRepository employeeRepository;
    private final EmployeeValidators employeeValidators;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeeValidators employeeValidators,
                           ModelMapper modelMapper){
        this.employeeRepository = employeeRepository;
        this.employeeValidators = employeeValidators;
        this.modelMapper = modelMapper;
    }

    @Override
    public Employee saveEmployee(RequestEmployeeDTO requestEmployee) {
        Employee employee = modelMapper.map(requestEmployee, Employee.class);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee editEmployee(RequestEmployeeDTO requestEmployee) {
        Employee employee = employeeValidators.validateEmployee(requestEmployee.getIdEmployee());

        employee.setId(requestEmployee.getIdEmployee());
        employee.setName(requestEmployee.getName());
        employee.setLastName(requestEmployee.getLastName());

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long idEmployee) {
        Employee employee = employeeValidators.validateEmployee(idEmployee);

        employeeRepository.delete(employee);
    }

    @Override
    public Employee findEmployee(Long idEmployee) {
        return employeeValidators.validateEmployee(idEmployee);
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }


}
