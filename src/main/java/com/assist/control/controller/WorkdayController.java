package com.assist.control.controller;

import com.assist.control.domain.Workday;
import com.assist.control.dto.request.RequestWorkdayDTO;
import com.assist.control.dto.response.ResponseWorkdayDTO;
import com.assist.control.service.interfaces.WorkdayInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assist-control/v1")
public class WorkdayController {

    @Autowired
    private WorkdayInterface workdayInterface;

    @PostMapping("/workday")
    public ResponseEntity<ResponseWorkdayDTO> addWorkdayToEmployee(@RequestBody @Valid RequestWorkdayDTO request){

        ResponseWorkdayDTO response = workdayInterface.setWorkdayToEmployee(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/workdays/{idEmployee}")
    public ResponseEntity<List<Workday>> getWorkdaysByEmployee(@PathVariable Long idEmployee){

        List<Workday> workdays = workdayInterface.findWorkdaysByEmployee(idEmployee);

        return ResponseEntity.ok().body(workdays);

    }


}
