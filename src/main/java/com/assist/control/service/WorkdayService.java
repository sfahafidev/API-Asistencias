package com.assist.control.service;

import com.assist.control.domain.Employee;
import com.assist.control.domain.Workday;
import com.assist.control.dto.request.RequestWorkdayDTO;
import com.assist.control.dto.response.ResponseWorkdayDTO;
import com.assist.control.repository.EmployeeRepository;
import com.assist.control.repository.WorkdayRepository;
import com.assist.control.service.interfaces.WorkdayInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

@Service
public class WorkdayService implements WorkdayInterface {

    private final WorkdayRepository workdayRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public WorkdayService(
            WorkdayRepository workdayRepository, EmployeeRepository employeeRepository,
            ModelMapper modelMapper
    ) {
        this.workdayRepository = workdayRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseWorkdayDTO setWorkdayToEmployee(RequestWorkdayDTO requestWorkday) {
        Employee employee = employeeRepository.findById(requestWorkday.getIdEmployee())
                .orElseThrow(() -> new RuntimeException("El empleado indicado no existe"));

        //Workday workday = modelMapper.map(requestWorkday, Workday.class);
        Workday workday = RequestWorkdayDTO.workdayMap(requestWorkday);
        workday.setTotalTimeDay(calculateTotalHours(requestWorkday.getTimeOfEntry(), requestWorkday.getTimeOfExit()));
        workday.setApproved(false);
        workday.setEmployee(employee);

        workdayRepository.save(workday);

        ResponseWorkdayDTO response = modelMapper.map(workday, ResponseWorkdayDTO.class);
        response.setName(employee.getName());

        return response;
    }

    private Double calculateTotalHours(LocalTime timeOfEntry, LocalTime timeOfExit){
        long tota = Duration.between(timeOfEntry, timeOfExit).getSeconds();
        return (double)tota/3600;
    }

}
