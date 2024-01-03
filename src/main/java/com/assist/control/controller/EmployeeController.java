package com.assist.control.controller;

import com.assist.control.domain.Employee;
import com.assist.control.dto.request.RequestEmployeeDTO;
import com.assist.control.service.interfaces.EmployeeInterface;
import com.assist.control.service.interfaces.WorkdayInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/assist-control/v1")
public class EmployeeController {

    @Autowired
    private EmployeeInterface employeeInterface;
    @Autowired
    private WorkdayInterface workdayInterface;

    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody @Valid RequestEmployeeDTO request){

        Employee employee = employeeInterface.saveEmployee(request);

        return ResponseEntity
                .created(URI.create("/assist-control/v1/" + employee.getId()))
                .body(employee);

    }

    @PutMapping("/employee")
    public ResponseEntity updateEmployee(@RequestBody @Valid RequestEmployeeDTO request){

        Employee employee = employeeInterface.editEmployee(request);

        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAllEmployees(){

        List<Employee> employees = employeeInterface.findAllEmployees();

        return ResponseEntity.ok().body(employees);
    }

    @GetMapping("/employee/{idEmployee}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable Long idEmployee){

        Employee  employee = employeeInterface.findEmployee(idEmployee);

        return ResponseEntity.ok().body(employee);
    }

    @DeleteMapping("/employee/{idEmployee}")
    public ResponseEntity deleteEmployeeById(@PathVariable Long idEmployee){

        employeeInterface.deleteEmployee(idEmployee);

        return ResponseEntity.noContent().build();
    }


}
