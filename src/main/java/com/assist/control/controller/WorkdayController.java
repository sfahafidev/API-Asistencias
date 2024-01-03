package com.assist.control.controller;

import com.assist.control.dto.request.RequestWorkdayDTO;
import com.assist.control.dto.response.ResponseWorkdayDTO;
import com.assist.control.service.interfaces.WorkdayInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
