package com.assist.control.controller;

import com.assist.control.domain.Employee;
import com.assist.control.dto.request.RequestEmployeeDTO;
import com.assist.control.dto.request.RequestWorkdayDTO;
import com.assist.control.dto.response.ResponseWorkdayDTO;
import com.assist.control.service.interfaces.EmployeeInterface;
import com.assist.control.service.interfaces.WorkdayInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/assist-control-v1")
public class EmployeeController {

    @Autowired
    private EmployeeInterface employeeInterface;

    @Autowired
    private WorkdayInterface workdayInterface;

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody @Valid RequestEmployeeDTO request){

        Employee employee = employeeInterface.saveEmployee(request);

        return ResponseEntity
                .created(URI.create("/assist-control-v1/" + employee.getId()))
                .body(employee);

    }

    @PostMapping("/workday")
    public ResponseEntity<ResponseWorkdayDTO> addWorkdayToEmployee(@RequestBody @Valid RequestWorkdayDTO request){

        ResponseWorkdayDTO response = workdayInterface.setWorkdayToEmployee(request);

        return ResponseEntity.ok().body(response);
    }

}
